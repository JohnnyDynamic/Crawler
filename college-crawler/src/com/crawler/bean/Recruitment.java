package com.crawler.bean;

import java.util.Map;

public class Recruitment {
	
	private String startDate;
	private String address;
	private String name;
	private Map<String, String> extraInfo;
	private String url;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(Map<String, String> extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return this.name+" "+this.startDate+" "+this.address;
	}
	
}
