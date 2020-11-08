package com.test.stack.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.test.stack.persistence.IDatabaseClient;

public class MySQLDatabaseClientImpl implements IDatabaseClient {

	public static final String JDBC_DRIVER_PROPERTY = "JDBC_DRIVER";
	public static final String DEFAULT_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static final String JDBC_URL_PROPERTY = "JDBC_URL";
	public static final String JDBC_URL_SCHEMA_VARIABLE = "db_stack";

	public static final String MYSQL_HOST_PROPERTY = "MYSQL_HOST_ADDR";
	public static final String DEFAULT_MYSQL_HOST = "localhost";
	public static final String MYSQL_PORT_PROPERTY = "MYSQL_HOST_PORT";
	public static final String DEFAULT_MYSQL_PORT = "3306";
	public static final String MYSQL_USER_PROPERTY = "MYSQL_USERNAME";
	public static final String MYSQL_PASS_PROPERTY = "MYSQL_PASSWORD";
	private final AtomicReference<String> defaultJdbcUrl = new AtomicReference<>();

	private static String getEnvOrProperty(String name, String defValue) {
		String value = System.getenv(name);
		return (value != null) ? value : System.getProperty(name, defValue);
	}

	private String jdbcUrl() {
		return getEnvOrProperty(JDBC_URL_PROPERTY, defaultJdbcUrl());
	}

	private String jdbcDriver() {
		return getEnvOrProperty(JDBC_DRIVER_PROPERTY, DEFAULT_JDBC_DRIVER);
	}

	private String jdbcUsername() {
		return Optional.ofNullable(getEnvOrProperty(MYSQL_USER_PROPERTY, "root")).orElseThrow(
				() -> new IllegalStateException("Required property " + MYSQL_USER_PROPERTY + " has not been provided"));
	}

	private String jdbcPassword() {
		return Optional.ofNullable(getEnvOrProperty(MYSQL_PASS_PROPERTY, "root")).orElseThrow(
				() -> new IllegalStateException("Required property " + MYSQL_PASS_PROPERTY + " has not been provided"));
	}

	private String defaultJdbcUrl() {
		return Optional.ofNullable(defaultJdbcUrl.get()).orElseGet(this::initializeDefaultJdbcUrl);
	}

	private String initializeDefaultJdbcUrl() {
		String host = getEnvOrProperty(MYSQL_HOST_PROPERTY, DEFAULT_MYSQL_HOST);
		String port = getEnvOrProperty(MYSQL_PORT_PROPERTY, DEFAULT_MYSQL_PORT);
		String url = "jdbc:mysql://" + host + ":" + port + "/" + JDBC_URL_SCHEMA_VARIABLE
				+ "?autoReconnect=true&useSSL=false";
		defaultJdbcUrl.set(url);
		return url;
	}

	@Override
	public Connection getConnection() throws Exception {
		Class.forName(jdbcDriver());
		return DriverManager.getConnection(jdbcUrl(), jdbcUsername(), jdbcPassword());
	}
}
