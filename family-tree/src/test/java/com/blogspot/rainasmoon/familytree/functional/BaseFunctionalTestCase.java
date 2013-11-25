package com.blogspot.rainasmoon.familytree.functional;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.PropertiesUtils;
import org.springside.modules.utils.spring.SpringContextHolder;

import com.blogspot.rainasmoon.familytree.tools.Start;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前重新载入默认数据, 创建WebDriver.
 * 
 * @author calvin
 */
@Ignore
@RunWith(GroupsTestRunner.class)
public class BaseFunctionalTestCase {

	protected final static String BASE_URL = Start.BASE_URL;

	//Test Groups define
	protected final static String DAILY = "DAILY";
	protected final static String NIGHTLY = "NIGHTLY";

	protected static Server server;

	protected static DataSource dataSource;

	protected static WebDriver driver;

    public static String path;

    static {
        try {
            Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties", "classpath:/application.test-local.properties");
            path = props.getProperty("test.data.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@BeforeClass
	public static void startAll() throws Exception {
		startJetty();

		fetchDataSource();
		loadDefaultData();

		createWebDriver();

		loginAsAdmin();
	}

	@AfterClass
	public static void stopAll() throws Exception {
		stopWebDriver();
		cleanDefaultData();
		stopJetty();
	}

	/**
	 * 启动Jetty服务器
	 */
	protected static void startJetty() throws Exception {
		server = JettyUtils.buildTestServer(Start.PORT, Start.CONTEXT);
		server.start();
	}

	/**
	 * 关闭Jetty服务器.
	 */
	protected static void stopJetty() throws Exception {
		server.stop();
	}

	/**
	 * 取出Jetty Server内的DataSource.
	 */
	protected static void fetchDataSource() {
		dataSource = SpringContextHolder.getBean("dataSource");
	}

	/**
	 * 载入默认数据.
	 */
	protected static void loadDefaultData() throws Exception {
        DbUnitUtils.loadData(dataSource, path);
	}

	/**
	 * 删除默认数据.
	 */
	protected static void cleanDefaultData() throws Exception {
        DbUnitUtils.removeData(dataSource, path);
	}

	/**
	 * 创建WebDriver.
	 */
	protected static void createWebDriver() throws Exception {
		Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties",
				"classpath:/application.test-local.properties");

		driver = SeleniumUtils.buildDriver(props.getProperty("selenium.driver"));
	}

	/**
	 * 关闭WebDriver
	 */
	protected static void stopWebDriver() {
		driver.close();
	}

	/**
	 * 登录管理员角色.
	 */
	protected static void loginAsAdmin() {
		driver.get(BASE_URL + "/j_spring_security_logout");

		// TODO:glen
		driver.get(BASE_URL + "/account/user.action");
		driver.findElement(By.name("j_username")).sendKeys("admin");
		driver.findElement(By.name("j_password")).sendKeys("admin");
		driver.findElement(By.xpath(Gui.BUTTON_LOGIN)).click();
	}
}