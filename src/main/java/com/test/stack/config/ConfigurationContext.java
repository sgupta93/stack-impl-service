package com.test.stack.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.stack.persistence.IDatabaseClient;
import com.test.stack.persistence.impl.MySQLDatabaseClientImpl;
import com.test.stack.service.Stack;

/**
 * Singleton context for creating/destroying stack at startup and shutdown.
 */
public class ConfigurationContext implements IConfigurationContext {

	protected static final Logger logger = LoggerFactory.getLogger(ConfigurationContext.class);
	public static final int DEFAULT_DELAY_FOR_CONFIG_FAILURES = 300;
	private Stack<Object> stack;
	private IDatabaseClient databaseClient;

	protected ConfigurationContext(String webAppContextRoot) {
		logger.info("Initializing configuration context (root: {})", webAppContextRoot);
		initialize();
	}

	public static void createInstance(String webAppContextRoot) {
		if (sInstance.get() == null) {
			ConfigurationContext context = createConfigurationContext(webAppContextRoot);
			if (context != null) {
				if (sInstance.compareAndSet(null, context)) {
					return;
				}
				context.shutdown();
			}
		} else {
			throw new IllegalStateException("Configuration context instance already created");
		}
	}

	private static ConfigurationContext createConfigurationContext(String webAppContextRoot) {
		return new ConfigurationContext(webAppContextRoot);

	}

	private void initialize() {
		if (stack == null) {
			stack = new Stack<>();
		}
		if (databaseClient == null) {
			databaseClient = initializeDatabaseClient();
		}
	}
	
	public IDatabaseClient initializeDatabaseClient() {
		if (IDatabaseClient.instance().isEmpty()) {
			IDatabaseClient databaseClient = new MySQLDatabaseClientImpl();
			if (IDatabaseClient.sInstance.compareAndSet(null, databaseClient)) {
				return databaseClient;
			}
			return null;
		} else {
			throw new IllegalStateException("Database client instance already created");
		}
	}
	
	@Override
	public Stack<Object> stack() {
		return stack;
	}
	
	@Override
	public IDatabaseClient databaseClient() {
		return databaseClient;
	}

	public static void destroyInstance() {
		Optional<ConfigurationContext> context = asConfigurationContext(IConfigurationContext.instance());
		if (context.isPresent() && sInstance.compareAndSet(context.get(), null)) {
			context.get().shutdown();
		}
	}

	private static Optional<ConfigurationContext> asConfigurationContext(Optional<IConfigurationContext> context) {
		return context.filter(c -> c instanceof ConfigurationContext).map(c -> (ConfigurationContext) c);
	}

	private void shutdown() {
		stack = null;
		databaseClient = null;
	}
}
