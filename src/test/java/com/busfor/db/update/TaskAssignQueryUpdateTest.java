package com.busfor.db.update;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskAssignQueryUpdateTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;

	@Test
	public void updateTest() throws SQLException {
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(1).when(pst).executeUpdate();
		TaskAssignQueryUpdate spy = new TaskAssignQueryUpdate(1, 1, connection);
		assertTrue(spy.update());
	}
}
