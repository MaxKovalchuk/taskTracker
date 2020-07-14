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

import com.busfor.model.Comment;

@RunWith(MockitoJUnitRunner.class)
public class CommentsByTaskIdQuerySelectTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;
	@Mock
	ResultSet rs;
	String example = "example";
	
	@Test
	public void selectTest() throws SQLException {
		Comment expected = new Comment().id(1).authorId(1).createdAt(example).taskId(1).text(example).isEdited(false);
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(rs).when(pst).executeQuery();
		Mockito.doReturn(true, false).when(rs).next();
		Mockito.doReturn(1).when(rs).getInt(Mockito.anyString());
		Mockito.doReturn(example).when(rs).getString(Mockito.anyString());
		Mockito.doReturn(false).when(rs).getBoolean(Mockito.anyString());
		CommentsByTaskIdQuerySelect spy = new CommentsByTaskIdQuerySelect(1, connection);
		Comment actual = spy.select().get(0);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getAuthorId(), actual.getAuthorId());
		assertEquals(expected.getText(), actual.getText());
		assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
		assertEquals(expected.getTaskId(), actual.getTaskId());
		assertEquals(expected.isIsEdited(), actual.isIsEdited());
	}
	
}
