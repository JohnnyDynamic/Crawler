package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 西南交通大学
 * @author asus
 *
 */
public class SWJTUCrawler extends PostCrawler {
	
	public SWJTUCrawler() {
		this.config = CrawlerInitializer.config.get("SWJTU");
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		SWJTUCrawler crawler = new SWJTUCrawler();
		for(int i = 1; i < 135; i++) {
			crawler.getConfig().getForm().put("ddlBulletin", i+"");
			List<URL> list = crawler.obtainItemURL(crawler.getConfig().getEntrance().toString(), crawler.getConfig().getForm()
					, crawler.getConfig().getItemPattern(), crawler.getConfig().getEncode());
			for(URL url: list) {
				System.out.println("page "+i+": "+url);
				crawler.obtainItem(url.toString(), crawler.getConfig().getEncode()
						, crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
			}
		}
	}

}
