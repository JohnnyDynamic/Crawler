package com.spider.util;


import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
	
	private static Set<String> visitedUrl = new HashSet<String>();
	
	private static Queue<String> unVisitedUrl = new Queue<String>();
	
	public static Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}
	
	public static void addVisitedUrl(String url) {
		visitedUrl.remove(url);
	}
	
	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}
	
	public static Object unVisitedUrlDeQueue() {
		return unVisitedUrl.deQueue();
	}
	
	public static void addUnvisitedUrl(String url) {
		if(url != null 
				&& !url.trim().equals("")
				&& !visitedUrl.contains(url)
				&& !unVisitedUrl.contains(url)) {
			unVisitedUrl.enQueue(url);
		}
	}
	
	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}
	
	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.empty();
	}
}

