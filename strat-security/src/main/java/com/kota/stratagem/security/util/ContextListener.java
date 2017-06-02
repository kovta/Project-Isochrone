package com.kota.stratagem.security.util;

import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	public ContextListener() {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if(System.getProperty("java.security.auth.login.config") == null) {
			String jaasConfigFile = null;
			URL jaasConfigURL = this.getClass().getClassLoader().getResource("login.conf");
			if(jaasConfigURL != null) {
				jaasConfigFile = jaasConfigURL.getFile();
			}
			System.setProperty("java.security.auth.login.config", jaasConfigFile);
		}
	}
}
