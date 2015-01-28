package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 西南财经大学
 * @author asus
 *
 */
public class SWUFECrawler extends PostCrawler {
	
	public SWUFECrawler() {
		this.config = CrawlerInitializer.config.get("SWUFE");
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		SWUFECrawler crawler = new SWUFECrawler();
		for(int i = 1; i < 67; i++) {
			crawler.getConfig().getForm().put("anpListPager_input", i+"");
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
