package com.crawler.impl;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.crawler.bean.College;
import com.crawler.bean.Recruitment;
import com.crawler.filter.ItemFilter;

public class GetCrawler{
	
	protected College config;
	
	public College getConfig() {
		return config;
	}

	public void setConfig(College config) {
		this.config = config;
	}

	public String obtainPage(String url, int currentPage) {
		if(url.contains("&amp;"))
			url = url.replace("&amp;", "&");
		return url+currentPage;
	}
	
	//解决打开链接Invalid Argument
	protected Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        new Parser();
        return new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
	
	public List<Recruitment> obtainItemURL(String url, String pattern, String encode) {
		Parser parser;
		List<Recruitment> recruitments = new ArrayList<Recruitment>(); 
		try {
			parser = new Parser(url);
			parser.setEncoding(encode);
			ItemFilter filter = new ItemFilter(pattern);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for(int i=0; i<list.size(); i++) {
				Node tag = list.elementAt(i);
				if(tag instanceof LinkTag) {
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();
					recruitments.add(new Recruitment(link.toPlainTextString(), linkUrl));
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recruitments;
	}
	
	//直接用Parser获取dom可能会导致乱码，因此可首先单独获取dom，再将其传递给Parser进行解析
	protected String obtainDoc(String file, String encode) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedInputStream fin = new BufferedInputStream(
					new URL(file).openStream());
			byte[] buffer = new byte[1024];
			while(fin.read(buffer) != -1) {
				result.append(new String(buffer, encode));//编码方式依据页面编码确定，可以抽象到配置文件
			}
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public List<String> obtainItem(String url, String encode, String contentLabel, String contentLabelType) {
		List<String> res = null;
		try {
			Parser parser = createParser(obtainDoc(url, encode));
			HasAttributeFilter filter = new HasAttributeFilter(contentLabelType, contentLabel);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			res = iterateItem(list.elementAt(0));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	protected String iterateItem(Node node) {
		StringBuilder res = new StringBuilder();
		if(node.getChildren() != null && node.getChildren().size()>1) {
			SimpleNodeIterator iter = node.getChildren().elements();
			while(iter.hasMoreNodes()) {
				Node n = iter.nextNode();
				res.append(iterateItem(n));
			}
		}else {
			String txt = node.toPlainTextString().trim();
			if(!txt.equals("")) {
				res.append(txt);
//				System.out.println("######## "+txt);
			}
		}
		return res.toString();
	}
	
	protected String obtainJson(String file, String encode) {
		StringBuilder result = new StringBuilder();
		BufferedInputStream fin = null;
		try {
			fin = new BufferedInputStream(
					new URL(file).openStream());
			byte[] buffer = new byte[1024];
			while(fin.read(buffer) != -1) {
				result.append(new String(buffer, encode));//编码方式依据页面编码确定，可以抽象到配置文件
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
	
	
	public static void main(String[] args) {
		new GetCrawler();
	}
}
