package com.blogspot.rainasmoon.familytree.unit.dao;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.utils.PropertiesUtils;

public abstract class BaseUnitTestCase extends SpringTxTestCase {
	
	protected Logger log = Logger.getLogger("wh:");

    public static String path;

    static {
        try {
            Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties", "classpath:/application.test-local.properties");
            path = props.getProperty("test.data.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
