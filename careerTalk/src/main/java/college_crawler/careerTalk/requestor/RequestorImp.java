package college_crawler.careerTalk.requestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import college_crawler.careerTalk.tool.CollectionTool;

public class RequestorImp implements Requestor {

	public String requestByGet(String link, String encode) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(link);
		StringBuilder source = new StringBuilder();
		int status = 0;
		try {
			status = httpClient.executeMethod(getMethod);
			BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), encode));
			String line = null;
			while((line = br.readLine()) != null) {
				source.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkLegal(source.toString());
	}

	public String requestByPost(String link, Map<String, String> parameters,
			String encode) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(link);
		StringBuilder source = new StringBuilder();
		postMethod.setRequestBody(CollectionTool.map2Pair(parameters));
		int status = 0;
		try {
			status = httpClient.executeMethod(postMethod);
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), encode));
			String line = null;
			while((line = br.readLine()) != null) {
				source.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkLegal(source.toString());
	}
	
	/**
	 * Check whether dom is legal or not by judge head of HTML.   
	 * @param source
	 * @return
	 */
	private String checkLegal(String source) {
		if(!source.startsWith("<") && source.indexOf("<") >= 0)
			source = source.substring(source.indexOf("<"));
		return source;
	}

}
