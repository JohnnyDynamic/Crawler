package com.crawler.Inter;

import java.net.URL;
import java.util.List;

public interface CrawlerInter {
	
	public void start();
	
	public String nextPage(String url, int currentPage);
	
	public List<URL> obtainItemURL(String url);
	
}
