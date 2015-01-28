package com.crawler.spec1;

import java.net.URL;
import java.util.List;

import com.crawler.bean.College;
import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 中央财经大学
 * @author asus
 *
 */
public class CUFECrawler extends GetCrawler{
	
	public CUFECrawler() {
		this.setConfig(CrawlerInitializer.config.get("CUFE"));
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		CUFECrawler crawler = new CUFECrawler();
		for(int i = 1; i <= 37; i++) {
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString(), i);
			System.out.println(url);
			List<URL> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(),
					crawler.getConfig().getEncode());
			for(URL u: list) {
				System.out.println("page "+i+": "+u);
				crawler.obtainItem(u.toString(), crawler.getConfig().getEncode(), 
						crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
			}
		}
	}


}
