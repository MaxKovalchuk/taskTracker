package com.busfor.api;

import com.busfor.model.Ping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class V1ApiDelegateImplTest {

  @Spy
  private V1ApiDelegateImpl spy;

  @Test
  public void testV1PingGet() throws Exception {
    ResponseEntity<Ping> result = spy.v1PingGet();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    Mockito.verify(spy, Mockito.times(1)).v1PingGet();
  }
}
