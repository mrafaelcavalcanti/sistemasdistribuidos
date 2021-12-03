package com.ufape.sistemasdistribuidos.services;

import io.github.cdimascio.dotenv.Dotenv;

public class RequestService {
	
	private Dotenv dotenv;
	
	private final int timeout;
	
	private final String base;
	
	public RequestService() {
		this.dotenv = Dotenv.load();
		
		this.timeout = Integer.parseInt(dotenv.get("TIMEOUT"));
		this.base = dotenv.get("BASE_PATH");
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public String getBase() {
		return base;
	}
}
