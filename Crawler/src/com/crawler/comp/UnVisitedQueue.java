package com.crawler.comp;

import java.net.URL;
import java.util.Set;

import com.crawler.comp.base.BaseQueue;

public class UnVisitedQueue<T> extends BaseQueue<URL> {
	
	public void enQueue(URL p) {
		if(p!=null&&!p.toString().trim().equals(""))
			super.enQueue(p);
		else {
			System.err.println("invalid page is denied by unvisited queue");
		}
	}
	
	public URL deQueue() {
		return (URL) super.deQueue();
	}
	
	public void batchAdd(Set<URL> ps) {
		for(URL p: ps) {
			enQueue(p);
		}
	}
}
