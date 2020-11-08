package com.test.stack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stack<E> {

	protected static final Logger logger = LoggerFactory.getLogger(Stack.class);
	private Node root;

	private class Node {
		E data;
		Node next;

		Node(E data) {
			this.data = data;
		}
	}

	public E push(E data) {
		Node newNode = new Node(data);
		if (root == null) {
			root = newNode;
		} else {
			Node temp = root;
			root = newNode;
			newNode.next = temp;
		}
		logger.info(data + " pushed into stack");
		return data;
	}

	public E pop() {
		E popped = null;
		if (root == null) {
			logger.info("Stack is Empty");
		} else {
			popped = root.data;
			root = root.next;
		}
		logger.info(popped + " pushed into stack");
		return popped;
	}
}
