package com.crawler.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


public class Requestor {
	
	public String getRequest(String url, String encode) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		StringBuilder source = new StringBuilder();
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			System.out.println(statusCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), encode));
			String line = null;
			while((line = br.readLine()) != null) {
				source.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return source.toString();
	}
	
	public String postRequest(String url, Map<String, String> map, String encode) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		StringBuilder source = new StringBuilder();
		postMethod.setRequestBody(map2Pair(map));
		try {
			int status = httpClient.executeMethod(postMethod);
			System.out.println(status);
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), encode));
			String line = null;
			while((line = br.readLine()) != null) {
				source.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return source.toString();
	}
	
	private NameValuePair[] map2Pair(Map<String, String> map) {
		NameValuePair[] pairs = new NameValuePair[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int i = 0;
		while(it.hasNext()) {
			String key = it.next();
			NameValuePair pair = new NameValuePair(key, map.get(key));
			pairs[i++] = pair;
		}
		return pairs;
	}
	
	
	public static void main(String[] args) {
//		System.out.println(new Requestor().getRequest("http://gr.uestc.edu.cn/job/joblist.shtml?type=zhaopinhui", "UTF-8"));
//		System.out.println(new Requestor().json2Map("{\"rq\":\"20150113\",\"zphid\":\"1586528527\"}"));
	}

}
