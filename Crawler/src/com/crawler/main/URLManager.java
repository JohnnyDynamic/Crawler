package com.crawler.main;

import java.net.URL;
import java.util.Set;

import com.crawler.comp.UnVisitedQueue;
import com.crawler.comp.VisitedSet;

public class URLManager {
	
	private VisitedSet<URL> visitedURLs = new VisitedSet<URL>();
	
	private UnVisitedQueue<URL> unVisitedURLs = new UnVisitedQueue<URL>();
	
	public void inputURL(URL url) {
		if(!visitedURLs.contains(url))
			unVisitedURLs.enQueue(url);
		else
			System.err.println("url repeats");
	}
	
	public void inputURL(Set<URL> urls) {
		if(!visitedURLs.containsAll(urls))
			unVisitedURLs.batchAdd(urls);
	}
	
	public URL outputURL() {
		URL url = unVisitedURLs.deQueue();
		visitedURLs.insert(url);
		return url;
	}
	
	public boolean isEmpty() {
		return unVisitedURLs.isEmpty();
	}
}
