package com.busfor.db.insert;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.busfor.db.insert.AttachmentQueryInsert;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentQueryInsertTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement pst;
	@Mock(lenient = true)
	MultipartFile file;

	@Test
	public void inserTest() throws SQLException, IOException {
		Mockito.doReturn(pst).when(connection).prepareStatement(Mockito.anyString());
		Mockito.doReturn(1).when(pst).executeUpdate();
		Mockito.doReturn("file.png").when(file).getName();
		Mockito.doReturn(new byte[100]).when(file).getBytes();
		AttachmentQueryInsert spy = new AttachmentQueryInsert(1, 1, file, connection);
		assertTrue(spy.insert());
	}
}
