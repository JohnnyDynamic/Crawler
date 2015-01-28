package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 福建农林大学
 * @author asus
 *
 */
public class FAFUCrawler extends PostCrawler {
	
	public FAFUCrawler() {
		this.config = CrawlerInitializer.config.get("FAFU");
	}
	
	@Override
	public List<String> obtainItem(String url, String encode,
			String contentLabel, String contentLabelType) {
		List<String> res = null;
		try {
			Parser parser = createParser(obtainDoc(url, encode));
			HasAttributeFilter filter = new HasAttributeFilter(contentLabelType, contentLabel);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			res = iterateItem(list.elementAt(2));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		FAFUCrawler crawler = new FAFUCrawler();
		for(int i = 1; i < 11; i++) {
			crawler.getConfig().getForm().put("Num", i+"");
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
