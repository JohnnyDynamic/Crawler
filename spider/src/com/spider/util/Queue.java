package com.spider.util;

import java.util.LinkedList;

public class Queue<T> {

	private LinkedList<T> queue = new LinkedList<T>();  
	
	public void enQueue(T t) {
		queue.add(t);
	}

	public Object deQueue() {
		return queue.removeFirst();
	}
	
	public boolean contains(Object t) {
		return queue.contains(t);
	}
	
	public boolean empty() {
		return queue.isEmpty();
	}
}

