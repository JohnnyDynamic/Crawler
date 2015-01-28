package com.crawler.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.crawler.Inter.CrawlerInter;

public class Crawler implements CrawlerInter{

	private URL entrance;
	
	private URLManager urlManager;
	
	public Crawler() {
		
	}
	
	public Crawler(URL entrance) {
		this.entrance = entrance;
		urlManager = new URLManager();
		urlManager.inputURL(entrance);
	}
	
	public void start() {
		while(!urlManager.isEmpty()) {
			Parser parser;
			try {
				parser = new Parser(urlManager.outputURL().toString());
				parser.setEncoding("UTF-8");
				
				ItemFilter filter = new ItemFilter(null);
				NodeList list = parser.extractAllNodesThatMatch(filter);
				for(int i=0; i<list.size(); i++) {
					Node tag = list.elementAt(i);
					if( tag instanceof LinkTag) {
						LinkTag link = (LinkTag) tag;
						String linkUrl = link.getLink();
						urlManager.inputURL(new URL(linkUrl));
					}
				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String nextPage(String url, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<URL> obtainItemURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

}

class ItemFilter implements NodeFilter {

	private static final long serialVersionUID = 1L;
	
	private String item;
	
	public ItemFilter(String item) {
		this.item = item;
	}
	
	@Override
	public boolean accept(Node node) {
		//System.out.println("text: "+node.getText());
		if(node.getText().contains(item))
			return true;
		return false;
	}
	
}
