package com.crawler.spec1;

import java.net.URL;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.bean.College;
import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 中央民族大学
 * @author asus
 *
 */
public class MUCCrawler extends GetCrawler {
	
	
	public MUCCrawler() {
		this.setConfig(CrawlerInitializer.config.get("MUC"));
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
			res = iterateItem(list.elementAt(3));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		MUCCrawler crawler = new MUCCrawler();
		for(int i = 1; i <= 32; i++) {
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
