package com.crawler.spec2;

import java.net.URL;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 浙江工商大学
 * @author asus
 *
 */
public class ZJGSUCrawler extends PostCrawler{
	
	public ZJGSUCrawler() {
		this.config = CrawlerInitializer.config.get("ZJGSU");
	}
	
	@Override
	public List<String> obtainItem(String url, String encode,
			String contentLabel, String contentLabelType) {
		List<String> res = null;
		try {
			Parser parser = createParser(obtainDoc(url, encode));
//			HasAttributeFilter filter = new HasAttributeFilter(contentLabelType, contentLabel);
			TagNameFilter filter = new TagNameFilter("table");
			NodeList list = parser.extractAllNodesThatMatch(filter);
			res = iterateItem(list.elementAt(8));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		ZJGSUCrawler crawler = new ZJGSUCrawler();
		for(int i = 1; i < 30; i++) {
			crawler.getConfig().getForm().put("me_page", i+"");
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
