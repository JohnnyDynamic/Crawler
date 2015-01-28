package com.crawler.spec1;

import java.net.URL;
import java.util.List;

import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 北京航空航天大学
 * @author asus
 *
 */
public class BUAACrawler extends GetCrawler{
	
	public BUAACrawler() {
		this.setConfig(CrawlerInitializer.config.get("BUAA"));
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		BUAACrawler crawler = new BUAACrawler();
		for(int i = 1; i <= 14; i++) {
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString(), i);
			System.out.println(url);
			List<URL> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(),
					crawler.getConfig().getEncode());
			for(URL u: list) {
				if(!u.toExternalForm().contains("careerTalks[i]")
						&&!u.toExternalForm().contains("jobfairInfos[i]")) {
					String link = u.toString();
					String lin = link.substring(0, link.indexOf(";"))+link.substring(link.indexOf("?"));
					lin = lin.replace("&amp;", "&");
					System.out.println("page "+i+": "+lin);
					crawler.obtainItem(lin, crawler.getConfig().getEncode(), 
							crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
				}
			}
		}
	}

}
