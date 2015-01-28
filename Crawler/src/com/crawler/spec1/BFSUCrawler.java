package com.crawler.spec1;

import java.net.URL;
import java.util.List;

import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 北京语言大学
 * @author asus
 *
 */
public class BFSUCrawler extends GetCrawler{
	
	public BFSUCrawler() {
		this.config = CrawlerInitializer.config.get("BFSU");
	}

	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		BFSUCrawler crawler = new BFSUCrawler();
		for(int i = 1; ; i++) {
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString(), i);
			System.out.println(url);
			List<URL> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(),
					crawler.getConfig().getEncode());
			if(list.size() > 0) {
				for(URL u: list) {
					System.out.println("page "+i+": "+u);
					crawler.obtainItem(u.toString(), crawler.getConfig().getEncode(), 
							crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
				}
			}else {
				System.out.println("crawling end");
				break;
			}
		}
	}
}
