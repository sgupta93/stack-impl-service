package com.test.stack.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lifecycle listener to initialize and destroy the Stack
 */
public class WebAppLifecycleListener implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(WebAppLifecycleListener.class);

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		logger.info("***************  {} initialization ***************", contextEvent.getServletContext().getServletContextName());
		try {
			createContext(contextEvent.getServletContext().getContextPath());
		} catch (Exception t) {
			logger.info("Exception creating configuration context", t);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		logger.info("***************  {} shutdown ***************", contextEvent.getServletContext().getServletContextName());
		try {
			destroyContext();
		} catch (Exception t) {
			logger.info("Exception shutting down configuration context", t);
		}
	}

	private void createContext(String path) {
		ConfigurationContext.createInstance(path);
	}

	private void destroyContext() {
		ConfigurationContext.destroyInstance();
	}
}
