package com.jwtAuth.controller.dto;

public class ServerResponse {
	
	private String message;

	public ServerResponse(String message) {
		super();
		this.message = message;
	}

	public ServerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
