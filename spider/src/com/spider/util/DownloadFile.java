package com.spider.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;


public class DownloadFile {

	private void saveToLocal(Set<String> imgSet, String filePath) {
		URLConnection con=null;
		URL theUrl=null;
		for(String url: imgSet) {
			System.out.println(url);
			try {
				theUrl=new URL(url);//建立地址
				con = theUrl.openConnection();//打开连接
				con.setConnectTimeout(30000);
				con.connect();//连接
			} catch (MalformedURLException e) {
				System.err.println("unavailable url");
			} catch (IOException e) {
				System.err.println("cannot connect to remote host");
			}
			String type = con.getContentType();
			long size = con.getContentLength();
			if(size <= 30000)
				continue;
			File file = new File("D://software/program/eclipse/workspace/spider/img3/"+UUID.randomUUID()+".jpg");
			
			if (type != null) {
				byte[] buffer = new byte[4 * 1024];
				int read;
				FileOutputStream os = null;
				InputStream in = null;
				try {
					os = new FileOutputStream(file);
					in = con.getInputStream();//重定向输入
					while ((read = in.read(buffer)) > 0) {//读取输出
						os.write(buffer, 0, read);//写入本地文件
					}
				} catch (FileNotFoundException e) {
					System.err.println("target directory dosent exist");
				} catch (IOException e) {
					System.err.println("wrong while reading remote file");
				} finally {
					try {
						in.close();
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private Set<String> getImgUrl(String html, String imgPattern) {
		Set<String> imgSet = new HashSet<String>();
		try {
			Parser myParser = new Parser(html);
	        myParser.setEncoding("GBK");
	        String filterStr = "img";
	        NodeFilter filter = new TagNameFilter(filterStr);
	        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
	        for (int i = 1; i < nodeList.size(); i++) {
	            ImageTag imgtag = (ImageTag) nodeList.elementAt(i);
	            String imageUrl = imgtag.getImageURL();
	            if(imageUrl.contains(imgPattern))
	            	imgSet.add(imageUrl);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgSet;
	}
	
	public String downLoadFile(String url, String imgPattern) {

		String html = null;
		HttpClient httpClient = new HttpClient();
		//set time out 
		httpClient.getHttpConnectionManager()
				  .getParams()
				  .setConnectionTimeout(10000);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				new DefaultHttpMethodRetryHandler()); //retry
		
		//execute HTTP GET request
		try {
			getMethod.addRequestHeader("Content-Type", "text/html; charset=UTF-8");  
			
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed:" + getMethod.getStatusLine());
				html = null;
			}
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			saveToLocal(getImgUrl(sb.toString(), imgPattern),"");
		} catch (HttpException e) {
			System.out.println("Please check you provided http address!");
		} catch (IOException e) {
		} catch (RuntimeException e) {
			System.out.println("error");
		}finally {
			getMethod.releaseConnection();
		}
		return html;
	}
}

