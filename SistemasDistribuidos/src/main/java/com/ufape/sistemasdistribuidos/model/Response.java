package com.ufape.sistemasdistribuidos.model;

public class Response<T> {
	private Boolean status;
	private T payload;
	
	public Response(Boolean status, T payload) {
		this.status = status;
		this.payload = payload;
	}

	public Boolean getStatus() {
		return this.status;
	}
	
	public T getPayload() {
		return this.payload;
	}
	
}
