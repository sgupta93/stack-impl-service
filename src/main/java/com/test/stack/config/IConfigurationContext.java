package com.test.stack.config;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.test.stack.persistence.IDatabaseClient;
import com.test.stack.service.Stack;

/**
 * Interface to the singleton configuration context.
 */
public interface IConfigurationContext {

	public static final AtomicReference<IConfigurationContext> sInstance = new AtomicReference<>();

	public static Optional<IConfigurationContext> instance() {
		return Optional.ofNullable(sInstance.get());
	}

	public static IConfigurationContext instanceOrThrow() {
		IConfigurationContext instance = sInstance.get();
		if (instance == null) {
			throw new IllegalStateException("ConfigurationContext has not been initialized");
		}

		return instance;
	}

	public Stack<Object> stack();
	public IDatabaseClient databaseClient();
}
