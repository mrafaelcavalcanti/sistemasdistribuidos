package com.ufape.sistemasdistribuidos.model;

public class Response<T> {
	private Boolean status;
	private T payload;
	private String message;
	
	public Response(Boolean status, T payload, String message) {
		this.status = status;
		this.payload = payload;
		this.message = message;
	}

	public Boolean getStatus() {
		return this.status;
	}
	
	public T getPayload() {
		return this.payload;
	}

	public String getMessage() {
		return message;
	}
	
}
