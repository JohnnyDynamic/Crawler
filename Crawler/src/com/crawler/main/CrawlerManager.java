package com.crawler.main;

import java.util.Set;

import com.crawler.bean.College;

public class CrawlerManager {
	
	private static CrawlerManager crawlerManager = new CrawlerManager();
	
	private Set<College> collegeConfig;
	
	private CrawlerManager() {
		
	}
	
	public static CrawlerManager newInstance() {
		if(crawlerManager == null)
			crawlerManager = new CrawlerManager();
		return crawlerManager;
	}
	
	public Set<College> initialize() {
		return CrawlerInitializer.loadConfig();
	}
	
	
	
	
}
