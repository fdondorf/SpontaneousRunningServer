package org.spontaneous.server.client.service;

public class RegisteredUserResult {

	private Long id;

	public RegisteredUserResult(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
