package com.crawler.spec1;

import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.bean.Recruitment;
import com.crawler.impl.GetCrawler;
import com.crawler.main.CrawlerInitializer;

/**
 * 北京邮电大学
 * @author asus
 *
 */
public class BUPTCrawler extends GetCrawler{
	
	public BUPTCrawler() {
		this.config = CrawlerInitializer.config.get("BUPT");
	}
	
	public String[] obtainItem(String url, String encode) {
		String[] res = new String[4];
		try {
			String doc = obtainDoc(url, encode);
			Parser parser = createParser(doc);
			StringFilter addressFilter = new StringFilter("招聘会地点");
			NodeList list1 = parser.extractAllNodesThatMatch(addressFilter);
			if(list1.size() > 0) {
				String address = list1.elementAt(0).getParent().getParent().getChildren().elementAt(3).toPlainTextString();
				System.out.println(address);
				res[0] = address;
			}
			parser.setResource(doc);
			StringFilter startDateFilter = new StringFilter("开始时间");
			NodeList list2 = parser.extractAllNodesThatMatch(startDateFilter);
			if(list2.size() > 0) {
				String startDate = list2.elementAt(0).getParent().getParent().getChildren().elementAt(3).toPlainTextString();
				System.out.println(startDate);
				res[1] = startDate;
			}
			parser.setResource(doc);
			StringFilter endDateFilter = new StringFilter("结束时间");
			NodeList list3 = parser.extractAllNodesThatMatch(endDateFilter);
			if(list3.size() > 0) {
				String endDate = list3.elementAt(0).getParent().getParent().getChildren().elementAt(3).toPlainTextString();
				System.out.println(endDate);
				res[2] = endDate;
			}
			parser.setResource(doc);
			HasAttributeFilter detailDateFilter = new HasAttributeFilter("class","detail-module");
			NodeList list4 = parser.extractAllNodesThatMatch(detailDateFilter);
			if(list4.size() > 0) {
				String detail = iterateItem(list4.elementAt(0));
				System.out.println(detail);
				res[3] = detail;
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		BUPTCrawler crawler = new BUPTCrawler();
		for(int i = 1; ; i++) {
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString(), i);
			System.out.println(url);
			List<Recruitment> list = crawler.obtainItemURL(url, crawler.getConfig().getItemPattern(),
					crawler.getConfig().getEncode());
			if(list.size() > 0) {
				for(Recruitment re: list) {
					System.out.println("page "+i+": "+re.getUrl());
					String[] res = crawler.obtainItem(re.getUrl().toString(), crawler.getConfig().getEncode());
					re.setAddress(res[0]);
					re.setStartDate(res[1]);
					
				}
			}else {
				System.out.println("crawling end");
				break;
			}
		}
	}

}
