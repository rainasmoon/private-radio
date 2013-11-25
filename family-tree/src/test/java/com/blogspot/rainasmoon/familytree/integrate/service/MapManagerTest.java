package com.blogspot.rainasmoon.familytree.integrate.service;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.utils.DbUnitUtils;

import com.blogspot.rainasmoon.familytree.dao.account.RoleDao;
import com.blogspot.rainasmoon.familytree.dao.account.UserDao;
import com.blogspot.rainasmoon.familytree.service.map.MapManager;
import com.blogspot.rainasmoon.familytree.ui.FamilyUI;
import com.blogspot.rainasmoon.familytree.ui.NodeUI;
import com.blogspot.rainasmoon.familytree.unit.dao.BaseUnitTestCase;


@ContextConfiguration(locations = { "/applicationContext-test.xml" })

public class MapManagerTest extends BaseUnitTestCase{

	private static DataSource dataSourceHolder = null;

	@Autowired
	private MapManager mapManager;


	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, BaseUnitTestCase.path);
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, BaseUnitTestCase.path);
	}
	
	@Test
	public void shouldCreateFamilyUI() {
		NodeUI node = mapManager.createFamilyUI("Glen");
		
		System.out.println("funny...");
		log.info("the node is:" + node);
		
		log.info("the node is:" + (node instanceof FamilyUI));
	}
}
