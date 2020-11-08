package com.test.stack.service;

public interface StackRestService {
	public <E> boolean push(E data);
	public boolean pop();
}
