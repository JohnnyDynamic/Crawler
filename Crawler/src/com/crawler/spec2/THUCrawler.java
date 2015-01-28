package com.crawler.spec2;

import java.util.List;
import java.util.Map;

import com.crawler.impl.DateCrawler;
import com.crawler.main.CrawlerInitializer;
import com.crawler.util.JsonTool;

/**
 * 清华大学
 * @author asus
 *
 */
public class THUCrawler extends DateCrawler{
	
	
	public String obtainPage(String url, String date) {
		return url+date;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		THUCrawler crawler = new THUCrawler();
		String dt = "2014-09-01";
		for(int i = 0; i < 200; i++) {
			dt = crawler.nextDate(dt);
			System.out.println(dt);
			String url = crawler.obtainPage(crawler.getConfig().getEntrance().toString().replace("&amp;", ";"), dt);
			String json = crawler.obtainJson(url, "UTF-8");
			List<Map<String, Object>> list = JsonTool.parseJSON2List(json);
			for(Map map: list) {
				String key = (String) map.get("zphid");
				String link = "http://career.cic.tsinghua.edu.cn/xsglxt/f/jyxt/anony/gotoZpxxList?id="+key;
				System.out.println(link);
				crawler.obtainItem(link, "UTF-8", "con clearfix","class");
			}
		}
	}

}
