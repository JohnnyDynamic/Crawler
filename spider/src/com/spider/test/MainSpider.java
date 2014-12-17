package com.spider.test;


import java.util.Set;

import com.spider.util.DownloadFile;
import com.spider.util.HtmlParserTool;
import com.spider.util.LinkQueue;

public class MainSpider {

	private void initCrawlerWithSeeds(String[] seeds) {
		for(int i=0; i<seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}
	
	public void crawling(String[] seeds, String imgPattern) {
		
		initCrawlerWithSeeds(seeds);
		
		while(!LinkQueue.unVisitedUrlsEmpty() 
				&& LinkQueue.getVisitedUrlNum()<=1000) {
			String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
			if(visitUrl == null) {
				continue;
			}
			DownloadFile downLoader = new DownloadFile();
			downLoader.downLoadFile(visitUrl, imgPattern);
//			System.out.println(visitUrl);
			LinkQueue.addVisitedUrl(visitUrl);
			
			Set<String> links = 
				HtmlParserTool.extracLinks(visitUrl);
			for(String link:links) {
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}
	
	public static void main(String[] args) {
		MainSpider crawler = new MainSpider();
		crawler.crawling(new String[]{"http://tieba.baidu.com/f?kw=%BE%A9%CF%E3julia&tp=0"}, 
				"imgsrc");
	}
}

