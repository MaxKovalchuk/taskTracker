package com.busfor.db.insert;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.busfor.db.insert.UserQueryInsert;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryInsertTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;

	@Test
	public void inserTest() throws SQLException {
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(1).when(pst).executeUpdate();
		UserQueryInsert spy = new UserQueryInsert("name", 1, connection);
		assertTrue(spy.insert());
	}
}
