package com.david.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {

	private static Logger logger = LoggerFactory.getLogger(Log4jTest.class);

	@Test
	public void loggerTest() {
		logger.error("log error 测试");
		logger.warn("log warn 测试");
		logger.debug("log debug 测试");
	}
}
