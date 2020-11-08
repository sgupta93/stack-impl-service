package com.test.stack.persistence;

import java.sql.Connection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface IDatabaseClient {

	public static final AtomicReference<IDatabaseClient> sInstance = new AtomicReference<>();

	public static Optional<IDatabaseClient> instance() {
		return Optional.ofNullable(sInstance.get());
	}

	public static IDatabaseClient instanceOrThrow() {
		IDatabaseClient instance = sInstance.get();
		if (instance == null) {
			throw new IllegalStateException("Database client has not been initialized");
		}
		return instance;
	}

	public Connection getConnection() throws Exception;
}
