package com.busfor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBConnection {

	private final Logger log = LoggerFactory.getLogger(DBConnection.class);
	private static final String DRIVER_CLASS = "org.postgresql.Driver";

	@Value("${db.host}")
	private String host;
	@Value("${db.name}")
	private String dbName;
	@Value("${db.user}")
	private String user;
	@Value("${db.password}")
	private String password;
	private Connection connection;

	public DBConnection() {
	}

	public Connection connection() {
		if (connection == null) {
			connect();
		}
		return connection;
	}

	@PreDestroy
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			log.error("Error when closing connection to PostgreSQL", e);
		}
	}

	private void connect() {
		if (loadDriver()) {
			try {
				connection = DriverManager.getConnection(dbLink(), user, password);
			} catch (SQLException e) {
				log.error("Failed to establish connection to PostgreSQL", e);
				return;
			}
			checkConnection();
		}
	}

	private boolean loadDriver() {
		boolean loaded = true;
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			log.error("Driver for PostgreSQL not found", e);
			loaded = false;
		}
		return loaded;
	}

	private String dbLink() {
		return "jdbc:postgresql://" + host + "/" + dbName;
	}
	
	private void checkConnection() {
		if (connection != null) {
			log.info("Connection to PostgreSQL established");
		} else {
			log.info("Failed to establish connection to PostgreSQL");
		}
	}

}
