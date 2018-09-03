package com.jwtAuth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtAuth.controller.dto.ServerResponse;

@RestController
@RequestMapping("/api/auth/")
public class GreetingController {


	@RequestMapping(value = "greeting", method = RequestMethod.GET)
    public ResponseEntity<ServerResponse> greeting() throws  Exception {

    	String msg = "Hello Authenticated user";
    	return ResponseEntity.ok(new ServerResponse(msg));
    }
}