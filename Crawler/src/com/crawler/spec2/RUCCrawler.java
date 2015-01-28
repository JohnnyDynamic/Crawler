package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.impl.DateCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 中国人民大学
 * @author asus
 *
 */
public class RUCCrawler extends DateCrawler {
	
	public String obtainPage(String url, String date) {
		return url+date;
	}
	
	@Override
	public List<String> obtainItem(String url, String encode,
			String contentLabel, String contentLabelType) {
		List<String> res = null;
		try {
			Parser parser = createParser(obtainDoc(url, encode));
//			HasAttributeFilter filter1 = new HasAttributeFilter("cellSpacing", "0");
//			HasAttributeFilter filter2 = new HasAttributeFilter("cellspacing", "0");
//			OrFilter filter = new OrFilter(filter1, filter2);
			TagNameFilter filter = new TagNameFilter("body");
			NodeList list = parser.extractAllNodesThatMatch(filter);
			System.out.println(list.size());
			res = iterateItem(list.elementAt(0));
//			res.addAll(iterateItem(list.elementAt(4)));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		RUCCrawler crawler = new RUCCrawler();
		String dt = "2014/09/01";
		for(int i = 0; i < 200; i++) {
			dt = dt.replace('/', '-');
			dt = crawler.nextDate(dt).replace('-', '/');
			System.out.println(dt);
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString().replace("&amp;", ";"), dt);
			List<URL> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(), crawler.getConfig().getEncode());
			for(URL u: list) {
				String link = u.toString();
				crawler.obtainItem(link, crawler.getConfig().getEncode(), 
						crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
			}
		}
	}
	
}
