package com.crawler.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

public class ItemFilter implements NodeFilter {

	/**
	 * 
	 */
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
