package com.crawler.main;

public class CrawlerFactory {
	
	public Crawler generateCrawler(String entrance) {
		return new Crawler(entrance);
	}
}
