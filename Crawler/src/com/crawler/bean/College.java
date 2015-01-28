package com.crawler.bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class College {
	
	private URL entrance;
	private String name;
	private String type;
	private String itemPattern;
	private String contentLabel;
	private String contentLableType;
	private String encode;
	private Map<String, String> form;
	
	public Map<String, String> getForm() {
		return form;
	}

	public void setForm(Map<String, String> form) {
		this.form = form;
	}

	public String getItemPattern() {
		return itemPattern;
	}

	public void setItemPattern(String itemPattern) {
		this.itemPattern = itemPattern;
	}

	public College(String name) {
		this.name = name;
	}
	
	public College(String name, String link) {
		this(name);
		try {
			this.entrance = new URL(link);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public URL getEntrance() {
		return entrance;
	}

	public void setEntrance(URL entrance) {
		this.entrance = entrance;
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

	public String getContentLabel() {
		return contentLabel;
	}

	public void setContentLabel(String contentLabel) {
		this.contentLabel = contentLabel;
	}

	public String getContentLableType() {
		return contentLableType;
	}

	public void setContentLableType(String contentLableType) {
		this.contentLableType = contentLableType;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
}
