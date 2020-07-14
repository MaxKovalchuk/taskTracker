package com.busfor.db.select;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.busfor.model.Attachment;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentsByTaskIdQuerySelectTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;
	@Mock
	ResultSet rs;
	String example = "example";
	
	@Test
	public void selectTest() throws SQLException {
		Attachment expected = new Attachment().id(1).authorId(1).awsLink(example).taskId(1);
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(rs).when(pst).executeQuery();
		Mockito.doReturn(true, false).when(rs).next();
		Mockito.doReturn(1).when(rs).getInt(Mockito.anyString());
		Mockito.doReturn(example).when(rs).getString(Mockito.anyString());
		AttachmentsByTaskIdQuerySelect spy = new AttachmentsByTaskIdQuerySelect(1, connection);
		Attachment actual = spy.select().get(0);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getAuthorId(), actual.getAuthorId());
		assertEquals(expected.getTaskId(), actual.getTaskId());
		assertEquals(expected.getAwsLink(), actual.getAwsLink());
	}
	
}
