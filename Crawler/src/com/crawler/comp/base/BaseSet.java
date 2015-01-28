package com.crawler.comp.base;

import java.util.HashSet;
import java.util.Set;

public class BaseSet<T> {
	
	private Set<T> set = new HashSet<T>();
	
	public void insert(T t) {
		set.add(t);
	}
	
	public void delete(T t) {
		set.remove(t);
	}
	
	public boolean contains(T t) {
		return set.contains(t);
	}
	
	public boolean containsAll(Set<?> ts) {
		return set.containsAll(ts);
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public int size() {
		return set.size();
	}
}
