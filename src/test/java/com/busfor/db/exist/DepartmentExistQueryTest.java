package com.busfor.db.exist;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.busfor.db.exists.DepartmentExistQuery;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentExistQueryTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;
	@Mock
	ResultSet rs;
	
	@Test
	public void existTest() throws SQLException {
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(rs).when(pst).executeQuery();
		Mockito.doReturn(true).when(rs).next();
		DepartmentExistQuery spy = new DepartmentExistQuery(connection, 100);
		assertTrue(spy.exists());
	}
}
