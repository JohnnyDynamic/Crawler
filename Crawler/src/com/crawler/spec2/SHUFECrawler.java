package com.crawler.spec2;

import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.impl.DateCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 上海财经大学
 * @author asus
 *
 */
public class SHUFECrawler extends DateCrawler{
	
	public String obtainPage(String url, String date) {
		return url+date;
	}
	
	@Override
	public List<String> obtainItem(String url, String encode,
			String contentLabel, String contentLabelType) {
		List<String> res = null;
		try {
			Parser parser = createParser(obtainDoc(url, encode));
			TagNameFilter filter = new TagNameFilter("div");
			NodeList list = parser.extractAllNodesThatMatch(filter);
			res = iterateItem(list.elementAt(0));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		SHUFECrawler crawler = new SHUFECrawler();
		String dt = "2014-09-01";
		for(int i = 0; i < 200; i++) {
			dt = crawler.nextDate(dt);
			System.out.println(dt);
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString().replace("&amp;", ";"), dt);
			System.out.println(crawler.getConfig().getEncode());
			crawler.obtainItem(url, crawler.getConfig().getEncode()
					, crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
		}
	}
}
