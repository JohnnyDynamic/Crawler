package com.crawler.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.crawler.main.CrawlerInitializer;

public class DateCrawler extends GetCrawler{
	
	public DateCrawler() {
		String clazzName = Thread.currentThread().getStackTrace()[2].getClassName();  
	    String college = clazzName.substring(clazzName.lastIndexOf(".")+1, clazzName.indexOf("Crawler"));
	    this.setConfig(CrawlerInitializer.config.get(college));
	}
	
	@SuppressWarnings("deprecation")
	protected String nextDate(String currentDate) {
		SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		d.setYear(Integer.parseInt(currentDate.split("-")[0])-1900);
		d.setMonth(Integer.parseInt(currentDate.split("-")[1])-1);
		d.setDate(Integer.parseInt(currentDate.split("-")[2]));
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(d);
		gc.add(5,+1);
		return sf.format(gc.getTime());
	}
	
	
	public static void main(String[] args) {
		new DateCrawler();
	}
}
