package com.crawler.imp;

import java.util.Map;

import com.crawler.base.BaseCrawler;
import com.crawler.component.Requestor;

public class PostCrawler extends BaseCrawler{
	
	public String requestSource(String url, Map<String, String> map, String encode) {
		return new Requestor().postRequest(url, map, encode);
	}
	
	
}
