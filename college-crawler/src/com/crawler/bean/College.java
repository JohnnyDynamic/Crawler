package com.crawler.bean;

import java.util.List;
import java.util.Map;

public class College {
	private String pageUrl;
	private String name;
	private String type;
	private String itemPattern;
	private String contentPattern;
	private String contentLabel;
	private String encode;
	private Map<String, String> form;
	private List<String> json;
	private List<String> ParameterPattern;
	private List<String> extraParameter;
	
	public College(String name, String pageUrl) {
		this.name = name;
		this.pageUrl = pageUrl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemPattern() {
		return itemPattern;
	}
	public void setItemPattern(String itemPattern) {
		this.itemPattern = itemPattern;
	}
	public String getContentPattern() {
		return contentPattern;
	}
	public void setContentPattern(String contentPattern) {
		this.contentPattern = contentPattern;
	}
	public String getContentLabel() {
		return contentLabel;
	}
	public void setContentLabel(String contentLabel) {
		this.contentLabel = contentLabel;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public Map<String, String> getForm() {
		return form;
	}
	public void setForm(Map<String, String> form) {
		this.form = form;
	}
	public List<String> getJson() {
		return json;
	}
	public void setJson(List<String> json) {
		this.json = json;
	}
	public List<String> getParameterPattern() {
		return ParameterPattern;
	}
	public void setParameterPattern(List<String> parameterPattern) {
		ParameterPattern = parameterPattern;
	}
	public List<String> getExtraParameter() {
		return extraParameter;
	}
	public void setExtraParameter(List<String> extraParameter) {
		this.extraParameter = extraParameter;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
}
