package com.spider.util;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {

	@SuppressWarnings("serial")
	public static Set<String> extracLinks(String url) {
		System.out.println(url);
		Set<String> links = new HashSet<String> ();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("UTF-8");
			NodeFilter filter = new NodeFilter() {
				@Override
				public boolean accept(Node node) {/*
					if(node.getText().contains("href=\"/html/article")
							||node.getText().contains("href=\"/html/part"))*/ {//tieba: href=\"/p
					if(node.getText().contains("href=\"/p"))
						return true;
					}
					
					return false;
				}
			};
			
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for(int i=0; i<list.size(); i++) {
				Node tag = list.elementAt(i);
				if( tag instanceof LinkTag) {
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();
					links.add(linkUrl);
				}
			}
			
		} catch (ParserException e) {
			e.printStackTrace();
		}  
		return links;
	}
}

