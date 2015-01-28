package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * ÷ÿ«Ï¥Û—ß
 * @author asus
 *
 */
public class CQUCrawler extends PostCrawler{
	
	public CQUCrawler() {
		this.config = CrawlerInitializer.config.get("CQU");
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		CQUCrawler crawler = new CQUCrawler();
		for(int i = 1; i < 67; i++) {
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
