package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * ’„Ω≠¥Û—ß
 * @author asus
 *
 */
public class ZJUCrawler extends PostCrawler{
	
	public ZJUCrawler() {
		this.config = CrawlerInitializer.config.get("ZJU");
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		ZJUCrawler crawler = new ZJUCrawler();
		crawler.setConfig(CrawlerInitializer.config.get("ZJU"));
		for(int i = 1; i < 17; i++) {
			crawler.getConfig().getForm().put("pages.currentPage", i+"");
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
