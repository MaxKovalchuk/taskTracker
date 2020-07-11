package com.busfor.api;

import com.busfor.model.Ping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

  @Override
  public ResponseEntity<Ping> v1PingGet() {
    Ping pong = new Ping().localtime(new Date().getTime());

    return new ResponseEntity<>(pong, HttpStatus.OK);
  }
}
