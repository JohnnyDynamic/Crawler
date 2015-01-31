package com.crawler.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.crawler.component.Requestor;

public class BaseCrawler {
	
	public String requestSource(String url, Map<String, String> map, String encode) {
		return new Requestor().postRequest(url, map, encode);
	}
	
	public String requestSource(String url, String encode) {
		return new Requestor().getRequest(url, encode);
	}
	
	public List<String> getItemURLFromDom(String source, String itemPattern) {
		Parser parser = new Parser();
		List<String> res = new ArrayList<String>();
		try {
			parser.setResource(source);
			LinkStringFilter filter = new LinkStringFilter(itemPattern);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for(int i=0; i<list.size(); i++) {
				Node tag = list.elementAt(i);
				if( tag instanceof LinkTag) {
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();
					res.add(linkUrl);
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Map> parseJson(String json) {
		Map<String, Object> map = json2Map(json);
		List<Map> res = new ArrayList<Map>();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			Object obj = map.get(it.next());
			if(obj instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>)obj;
				for(Object ob: list) {
					if(ob instanceof Map) {
						res.add((Map) ob);
					}
				}
			}
		}
		return res;
	}
	
	public String getItemURL(Map map, String itemPattern, String itemUrl) {
		String id = (String) map.get(itemPattern);
		return itemUrl.replace("#*#", id);
	}
	
	private Map<String, Object> json2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                @SuppressWarnings("unchecked")
				Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(json2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
	}
	
	public List<String> parseTextFromDom(String dom) {
		List<String> res = null;
		try {
			Parser parser = new Parser();
			parser.setResource(dom);
			HasAttributeFilter filter = new HasAttributeFilter("body"); 
			NodeList list = parser.extractAllNodesThatMatch(filter);
			res = iterateDom(list.elementAt(0), "0");
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	private List<String> iterateDom(Node node, String path) {
		List<String> res = new ArrayList<String>();
		NodeList list;
		if((list = node.getChildren()) != null) {
			SimpleNodeIterator it = list.elements();
			int i = 0;
			while(it.hasMoreNodes()) {
				res.addAll(iterateDom(it.nextNode(), path+"-"+i++));
			}
		}else {
			String text;
			if(!(text = node.toPlainTextString().trim()).equalsIgnoreCase("")) {
				res.add(text);
//				System.out.println("##"+path+"## "+text);
			}
		}
		return res;
	}
	
	public void grabProperties(List<String> res) {
		
	}
	
	public static void main(String[] args) {
		BaseCrawler c = new BaseCrawler();
//		String json = c.requestSource("http://career.cic.tsinghua.edu.cn/xsglxt/b/jyxt/anony/queryTodayHdList?rq=2014-12-10&callback=jsonp1422448015533"
//				, "UTF-8");
//		//清华，json作特殊处理
//		json = "{\"rows\":"+json.substring(json.indexOf("["), json.lastIndexOf(")"))+"}";
//		List<String> list = c.requestItemURLFromJson(json, "zphid", "http://career.cic.tsinghua.edu.cn/xsglxt/f/jyxt/anony/gotoZpxxList?id=#*#");
//		for(String url: list) {
//			String dom = c.requestSource(url, "UTF-8");
//			String link = c.requestItemURLFromDom(dom, "showZwxx").get(0);
//			String dom2 = c.requestSource("http://career.cic.tsinghua.edu.cn"+link, "UTF-8");
//			List<String> res = c.parseTextFromDom(dom2);
//		}
		String dom2 = c.requestSource("http://job.bupt.edu.cn/career/fairdetail?fairId=f27fa84e49a845dc014a61bcd5bb46ac&fairsType=2"
				, "UTF-8");
		List<String> res = c.parseTextFromDom(dom2);
	}
}
