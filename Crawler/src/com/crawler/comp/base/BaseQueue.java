package com.crawler.comp.base;

import java.util.LinkedList;

public class BaseQueue<T> {
	
	private LinkedList<T> queue = new LinkedList<T>();
	
	public void enQueue(T t) {
		queue.add(t);
	}

	public Object deQueue() {
		return queue.removeFirst();
	}
	
	public boolean contains(T t) {
		return queue.contains(t);
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public void clear() {
		queue.clear();
	}
	
	public int size() {
		return queue.size();
	}
}
