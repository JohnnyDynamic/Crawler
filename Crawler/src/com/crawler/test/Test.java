package com.crawler.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class Test {

	public static void main(String[] args) {
		//适用于河南大学
//		new Test().ajax("http://job.henu.edu.cn/ajax/AjaxDataUpdate,App_Code.vs4l3xcm.ashx?_method=getNewsListHtml&_session=r"
//				,"curPage=9\r\ntotalPage=44\r\ntype=2\r\nGoPage=9\r\nstrWhere=7\r\nflag=ID\r\nDisType=News");
//		new Test().ajax("http://www.career.zju.edu.cn/ejob/zczphgl.do", "zphlx=0&dwmc=&hylb=&zphrq=&pages.pageSize=30&pages.currentPage=5&pages.maxPage=17&pageno=");
		
		//适用于浙江大学
		NameValuePair[] paras = new NameValuePair[13];
		for(int i = 0; i < 13; i++)
			paras[i] = new NameValuePair();
		paras[0].setName("__VIEWSTATE");
		paras[0].setValue("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");
		paras[1].setName("__VIEWSTATEGENERATOR");
		paras[1].setValue("43F74F39");
		paras[2].setName("__EVENTTARGET");
		paras[2].setValue("anpListPager");
		paras[3].setName("__EVENTARGUMENT");
		paras[3].setValue("262");
		paras[4].setName("__VIEWSTATEENCRYPTED");
		paras[4].setValue("");
		paras[5].setName("__EVENTVALIDATION");
		paras[5].setValue("mFuk3OKfCWctXDGlLDXOBh4MB0fcqkoJHBINrGi3lAVt3qvXBojs2icMh/oUQCsdZzgvqlGz966/+EDrpcm3RcnpRifalo74HgBWBZ9tgqZcNOMhGoCvIyB/nvRhYCks9eNrBV8t3/nDftd7PTj4xml2PTtaypvzLbDayWqrGcxw2PapnkcKDGt7Wx+KAOZhbXL6+7wCIPqCpkKlCHLK4GA9mklmDluUHEDMdzR9mh3Hc3/+QdOccswKLJo08yDIj4cU4ffIQzt3K4+cNVJa9uwNb7NWQU82KxN+tCS0NpjiLARiADW2yRN6t9D9c+dU8P95sWvUrOeqsAkKFibwm7pub3KSExM5c2FuvbrJlZFJRW8qCt3qkxO5STEZmDOPNsRPvbM8yVLBdCIToCl5IcvnRwGizAN6UB8/qof08Qn7MQaZuClW/0l1hHumfj62jQ48CNxsAceJ9awvzxyKr/srJWYY+E7TLL40s7ygQjDWcqouIATe2vAM2/gi6E6svckVhUN3TJAebuGwr5l8w2sWKo6jnyBVxvj91Gyzu6bkowW7Vrq5LWU07ZxSk3FyxMv8b6J0B8gc6v5rIxJm80F852EeVTsEP6nhXK/Q9m9QPTXbffVFOgLYM8TUVGeU49xX0j4N6zbEQ8Cex2LdNwmXpQv0QuDxgWptkeYcKe3p1SgNLtVGkBfQBtNCw1BQ7SwmjC0KJRz71chPGfkTq4GQtmVG3rsThmIC1S6UmmLttv0FzTT/O5OgIJaIJL71qPoa1HpGGVTKSRFOdp6Xefj2S8YOXjx6C/lpniqs9rNzkB2ac26oymlH4Rz1k32rhAoJ8OpFOekmLIKENZJ9eAT62AZw228qdAMcWp/R9j6fLzEqEA+pou6mNlQFcBjLN8dao5Xn+Y6ZTBJehZPMi3OadPHFtGE03DOXm1uJXBZILynWNIKkdfZiEayrT3pKXq+05cL68JB5e6fGsi+N2sbPnVEdwUpfoJLgAG+mKFY=");
		paras[6].setName("hfStartTime");
		paras[6].setValue("");
		paras[7].setName("txtTitle");
		paras[7].setValue("");
		paras[8].setName("listNature");
		paras[8].setValue("0");
		paras[9].setName("listIndustry");
		paras[9].setValue("0");
		paras[10].setName("ddlEducRequired");
		paras[10].setValue("");
		paras[11].setName("ddlSource");
		paras[11].setValue("");
		paras[12].setName("anpListPager_input");
		paras[12].setValue("4");
		System.out.println(new Test().getPostResponse("http://job.swufe.edu.cn/Client/UnRecruitList.aspx", paras));
//		new Test().getGetResponse("http://job.cqupt.edu.cn/main/getClnd/2014-12-28");
	}
	
	
	
	public void ajax(String url, String data) {
		 PostMethod method = null;  
	        try  {  
	            HttpClient client = new HttpClient();  
	            method = new PostMethod(url);  
	            method.addRequestHeader("connection","keep-alive");
	            method.setRequestBody(data);
	            int statusCode = client.executeMethod(method); 
	            System.out.println(statusCode);
	            BufferedReader is = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	            String res = "";
	            String line;
	            while((line = is.readLine()) != null) {
	            	res += line+"\n";
	            }
	            System.out.println(res);  
	        } catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	
	        }
	}
	
	public String getPostResponse(String url, NameValuePair[] param) {
	    String response = null;
	    PostMethod post = new PostMethod(url);
	    HttpClient client = new HttpClient();
	    post.setRequestBody(param);
	    try {
	    	int statusCode = client.executeMethod(post);
	    	System.out.println(statusCode);
	    	if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
	    		Header locationHeader = post.getResponseHeader("location");
	    		Header[] headers = post.getResponseHeaders();
	    		for(Header header: headers)
	    			System.out.println(header);
	    		String location = null;
	    		if (locationHeader != null) {
	    			location = locationHeader.getValue();
	    			System.out.println("location: "+location);
	    			response = this.getPostResponse(location, param);//用跳转后的页面重新请求。
	    		}
	    	}
	    	else if(statusCode == HttpStatus.SC_OK) {
	    		response= post.getResponseBodyAsString();
	    	}
	    }
	    catch (IOException ex) {
	    }
	    finally {
	    	post.releaseConnection();
	    }
	    return response;
    }

	public String getGetResponse(String url) {
		String response = null;
	    GetMethod post = new GetMethod(url);
	    HttpClient client = new HttpClient();
	    try {
	    	int statusCode = client.executeMethod(post);
	    	System.out.println(statusCode);
	    	response= post.getResponseBodyAsString();
	    	System.out.println(response);
	    } catch(Exception e) {
	    	
	    }
	    return response;
	}
	
	
}



