package com.crawler.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
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
import com.crawler.filter.ItemFilter;
import com.crawler.main.CrawlerInitializer;

public class PostCrawler {
	
	protected College config;
	
	public College getConfig() {
		return config;
	}

	public void setConfig(College config) {
		this.config = config;
	}
	
	public List<URL> obtainItemURL(String url, Map<String, String> data, String pattern, String encode) {
		Parser parser;
		List<URL> itemURLs = new ArrayList<URL>(); 
		try {
			parser = new Parser(submitForm(url, data, encode));
			ItemFilter filter = new ItemFilter(pattern);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for(int i=0; i<list.size(); i++) {
				Node tag = list.elementAt(i);
				System.out.println(tag.toPlainTextString().trim());
				if(tag instanceof LinkTag) {
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();
					System.out.println(linkUrl);
					linkUrl = url.substring(0, url.lastIndexOf("/")+1)+linkUrl;
					itemURLs.add(new URL(linkUrl));
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemURLs;
	}
	
	public String obtainJson(String url, Map<String, String> data, String encode) {
		return submitForm(url, data, encode);
	}
	
	//直接用Parser获取dom可能会导致乱码，因此可首先单独获取dom，再将其传递给Parser进行解析
	private String submitForm(String requestURL, Map<String, String> data, String encode) {
		PostMethod method = null;
        String res = "";
        HttpClient client = new HttpClient();  
        try  {  
            method = new PostMethod(requestURL);  
            method.addRequestHeader("connection","keep-alive");
            method.setRequestBody(map2Pair(data));
            int statusCode = client.executeMethod(method); 
            System.out.println(statusCode);
            BufferedReader is = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), encode));
            String line;
            while((line = is.readLine()) != null) {
            	res += line+"\n";
            }
        } catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return res;
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
	
	protected List<String> iterateItem(Node node) {
		List<String> res = new ArrayList<String>();
		if(node.getChildren() != null && node.getChildren().size()>1) {
			SimpleNodeIterator iter = node.getChildren().elements();
			while(iter.hasMoreNodes()) {
				Node n = iter.nextNode();
				res.addAll(iterateItem(n));
			}
		}else {
			String txt = node.toPlainTextString().trim();
			if(!txt.equals("")) {
				res.add(txt);
//				System.out.println("######## "+txt);
			}
		}
		return res;
	}
	
	//直接用Parser获取dom可能会导致乱码，因此可首先单独获取dom，再将其传递给Parser进行解析
	protected String obtainDoc(String file, String encode) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(new URL(file).openStream(), encode));
			String line;
            while((line = is.readLine()) != null) {
            	result.append(line);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//解决打开链接Invalid Argument
	protected Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        new Parser();
        return new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
	
	private NameValuePair[] map2Pair(Map<String, String> map) {
		NameValuePair[] pairs = new NameValuePair[map.size()];
		int i = 0;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			if(value.equals("*"))
				value = "";
			NameValuePair pair = new NameValuePair(key, value);
			pairs[i] = pair;
			i++;
		}
		return pairs;
	}
	
	public static void main(String[] args) {
		CrawlerInitializer.loadConfig();
		PostCrawler crawler = new PostCrawler();
		crawler.setConfig(CrawlerInitializer.config.get("ZJU"));
		for(int i = 1; i < 17; i++) {
			crawler.getConfig().getForm().put("pages.currentPage", i+"");
			List<URL> list = crawler.obtainItemURL(crawler.getConfig().getEntrance().toString(), crawler.getConfig().getForm()
					, crawler.getConfig().getItemPattern(), crawler.getConfig().getEncode());
			for(URL url: list) {
				System.out.println("page "+i+": "+url);
				crawler.obtainItem(url.toString(), crawler.getConfig().getEncode()
						, crawler.getConfig().getContentLabel(), crawler.getConfig().getContentLableType());
			}
		}
	}

}
