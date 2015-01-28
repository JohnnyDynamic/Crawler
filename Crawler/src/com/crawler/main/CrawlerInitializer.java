package com.crawler.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.crawler.bean.College;

public class CrawlerInitializer {
	
	public static Map<String, College> config = new HashMap<String, College>();
	
	public static void loadConfig() {
	    File f = new File("src/com/crawler/config.xml");
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(f);
		    NodeList beans = doc.getElementsByTagName("college");
		    for(int i = 0; i < beans.getLength(); i++) {
		    	Node bean = beans.item(i);
//		    	String name = bean.getAttributes().getNamedItem("name").getNodeValue();
		    	String name = bean.getChildNodes().item(1).getFirstChild().getNodeValue();
		    	String url = bean.getChildNodes().item(3).getFirstChild().getNodeValue();
		    	String type = bean.getChildNodes().item(5).getFirstChild().getNodeValue();
		    	String itemPattern = bean.getChildNodes().item(7).getFirstChild().getNodeValue();
		    	String contentLabel = bean.getChildNodes().item(9).getFirstChild().getNodeValue();
		    	String contentLableType = bean.getChildNodes().item(11).getFirstChild().getNodeValue();
		    	String encode = bean.getChildNodes().item(13).getFirstChild().getNodeValue();
		    	College college = new College(name, url);
		    	Node formNode = bean.getChildNodes().item(15);
		    	if(!formNode.getFirstChild().getNodeValue().equals("*")) {
		    		Map<String, String> data = new HashMap<String, String>();
		    		int length = formNode.getChildNodes().getLength();
		    		for(int j = 1; j < length; j=j+2) {
		    			String key = formNode.getChildNodes().item(j).getChildNodes().item(1).getFirstChild().getNodeValue();
		    			String value = formNode.getChildNodes().item(j).getChildNodes().item(3).getFirstChild().getNodeValue();
		    			data.put(key, value);
		    		}
			    	college.setForm(data);
		    	}
		    	college.setType(type);
		    	college.setItemPattern(itemPattern);
		    	college.setContentLabel(contentLabel);
		    	college.setContentLableType(contentLableType);
		    	college.setEncode(encode);
		    	config.put(name, college);
		    }
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new CrawlerInitializer().loadConfig();
		
	}
}
