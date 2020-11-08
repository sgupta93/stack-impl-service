package com.test.stack.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.stack.config.IConfigurationContext;
import com.test.stack.persistence.IDatabaseClient;

public class StackRestServiceImpl implements StackRestService {

	protected static final Logger logger = LoggerFactory.getLogger(StackRestServiceImpl.class);

	@Override
	public <E> boolean push(E data) {
		Stack<Object> stack = IConfigurationContext.instanceOrThrow().stack();
		if (save(data)) {
			stack.push(data);
			return true;
		} else {
			logger.info("Not able to push the data into stack.");
		}
		return false;
	}

	@Override
	public boolean pop() {
		Stack<Object> stack = IConfigurationContext.instanceOrThrow().stack();
		if (delete()) {
			stack.pop();
			return true;
		} else {
			logger.info("Not able to pop the data into stack.");
		}
		return false;
	}

	private <E> boolean save(E data) {
		IDatabaseClient databaseClient = IConfigurationContext.instanceOrThrow().databaseClient();

		try (Connection connection = databaseClient.getConnection()) {
			Statement statement = connection.createStatement();
			String sql = "insert into stack (`data`) values(" + data + ")";
			if (statement.executeUpdate(sql) == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private <E> boolean delete() {
		IDatabaseClient databaseClient = IConfigurationContext.instanceOrThrow().databaseClient();
		try (Connection connection = databaseClient.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select id, data from stack order by id desc limit 1");
			if (result.next()) {
				int id = result.getInt("id");
				if (statement.executeUpdate("delete from stack where id = " + id) == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
