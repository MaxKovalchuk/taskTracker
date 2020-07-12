package com.busfor.db.exists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExistQueryImpl implements ExistQuery{
	
	private final Logger log = LoggerFactory.getLogger(ExistQueryImpl.class);
	private final String functionName;
	private final Connection connection;
	private final Integer entityId;
	
	public ExistQueryImpl(Connection connection, Integer entityId, String functionName) {
		this.connection = connection;
		this.entityId = entityId;
		this.functionName = functionName;
	}

	@Override
	public boolean exists() {
		boolean exist = false;
		try(PreparedStatement pst = connection.prepareStatement(query())){
			pst.setInt(1, entityId);
			ResultSet rs = pst.executeQuery();
			exist = rs.next();
		}catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return exist;
	}
	
	private String query() {
		return "SELECT * FROM " + functionName + "(?)";
	}

}
