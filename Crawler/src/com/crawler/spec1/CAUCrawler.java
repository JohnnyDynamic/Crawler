package com.crawler.spec1;

import java.net.URL;
import java.util.List;

import com.crawler.bean.College;
import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 中国农业大学
 * @author asus
 *
 */
public class CAUCrawler extends GetCrawler {

	public CAUCrawler() {
		this.config = CrawlerInitializer.config.get("CAU");
	}

	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		CAUCrawler crawler = new CAUCrawler();
		for(int i = 1; ; i++) {
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString(), i);
			System.out.println(url);
			List<URL> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(),
					crawler.getConfig().getEncode());
			if(list.size() > 0) {
				for(URL u: list) {
					String lin = u.toString();
					lin = lin.replace("&amp;", "&");
					System.out.println("page "+i+": "+u);
					crawler.obtainItem(lin, crawler.getConfig().getEncode(), 
							crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
				}
			}else {
				System.out.println("crawling end");
				break;
			}
		}
	}

}
