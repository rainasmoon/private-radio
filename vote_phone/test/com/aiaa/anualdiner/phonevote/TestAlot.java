package com.aiaa.anualdiner.phonevote;

import java.util.logging.Logger;

import org.junit.Test;

public class TestAlot {
	Logger log = Logger.getLogger("wh:");

	@Test
	public void testAlot() {
		log.info("lot:" + "aia 526".replaceFirst("aia", ""));
	}
	
	public static void main(String [] args) {
		new TestAlot().testAlot();
	}
}
