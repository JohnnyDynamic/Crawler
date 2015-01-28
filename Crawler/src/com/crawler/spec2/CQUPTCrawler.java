package com.crawler.spec2;

import java.util.List;
import java.util.Map;

import com.crawler.impl.PostCrawler;
import com.crawler.main.CrawlerInitializer;
import com.crawler.util.JsonTool;

public class CQUPTCrawler extends PostCrawler {
	
	public CQUPTCrawler() {
		this.config = CrawlerInitializer.config.get("CQUPT");
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		CQUPTCrawler crawler = new CQUPTCrawler();
		for(int i = 1; i < 16; i++) {
			crawler.getConfig().getForm().put("page", i+"");
			String json = crawler.obtainJson(crawler.getConfig().getEntrance().toString(), 
					crawler.getConfig().getForm(), crawler.getConfig().getEncode());
			List<Map<String, Object>> list = JsonTool.parseJSON2List(json);
			for(Map map: list) {
				String link = "http://job.cqupt.edu.cn/main/rec/"+((String)map.get("id")).substring(4);
				System.out.println(map.get("title"));
				crawler.obtainItem(link, crawler.getConfig().getEncode()
						, crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
			}
		}
	}

}
