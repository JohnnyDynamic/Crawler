package com.crawler.spec;

import java.util.List;
import java.util.Map;

import com.crawler.Initailizer;
import com.crawler.base.BaseCrawler;
import com.crawler.bean.College;
import com.crawler.bean.Recruitment;
import com.crawler.component.Pager;

public class THUCrawler extends BaseCrawler{
	
	private College config;
	
	public College getConfig() {
		return config;
	}

	public void setConfig(College config) {
		this.config = config;
	}

	public THUCrawler() {
		this.config = Initailizer.config.get("THU");
	}
	
	public static void main(String[] args) {
		Initailizer.loadConfig();
		THUCrawler crawler = new THUCrawler();
		College config = crawler.getConfig();
		String startDate = "2014-09-11";
		String endDate = "2014-09-30";
		Pager pager = new Pager(config.getPageUrl());
		while(startDate.compareTo(endDate) <= 0) {
			System.out.println(startDate);
			String pageUrl = pager.createPageURL(startDate);
			String json = crawler.requestSource(pageUrl, config.getEncode());
			json = "{\"rows\":"+json.substring(json.indexOf("["), json.lastIndexOf(")"))+"}";
			List<Map> recruitmentMaps = crawler.parseJson(json);
			for(Map map: recruitmentMaps) {
				Recruitment rec = new Recruitment();
				String url = crawler.getItemURL(map, config.getJson().get(0), config.getJson().get(1));
				rec.setUrl(url);
				rec.setName((String) map.get(config.getParameterPattern().get(0)));
				rec.setStartDate((String) map.get(config.getParameterPattern().get(1)));
				rec.setAddress((String) map.get(config.getParameterPattern().get(2)));
				System.out.println(rec);
				String dom = crawler.requestSource(url, "UTF-8");
				List<String> links = crawler.getItemURLFromDom(dom, config.getItemPattern());
				if(links.size() > 0) {
					String link = links.get(0);
					System.out.println("link: "+link);
					String dom2 = crawler.requestSource("http://career.cic.tsinghua.edu.cn"+link, config.getEncode());
					List<String> res = crawler.parseTextFromDom(dom2);
					List<String> extraParas = config.getExtraParameter();
					for(int k = 0; k < res.size(); k++) {
						for(int i = 0; i < extraParas.size(); i++) {
							if(res.get(k).contains(extraParas.get(i)) && res.get(k+1).contains(extraParas.get(i+1))) {
								System.out.println(extraParas.get(i)+": "+res.get(k+1));
							}
						}
					}
				}
			}
			startDate = pager.nextDate(startDate);
		}
	}

}
