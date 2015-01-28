package com.crawler;


import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;   
 



import org.w3c.dom.Document;   
import org.w3c.dom.Element;   
import org.w3c.dom.NodeList;   

import com.crawler.main.Crawler;
 
public class Tester {   
	
    public static void main(String args[]) {   
	     try {
			new Thread(new Crawler(new URL("http://job.bupt.edu.cn/career/careerTalkDetail"))).start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   
}   
