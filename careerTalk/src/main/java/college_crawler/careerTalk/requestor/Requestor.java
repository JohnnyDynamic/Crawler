package college_crawler.careerTalk.requestor;

import java.util.Map;

public interface Requestor {
	
	public String requestByGet(String link, String encode);
	
	public String requestByPost(String link, Map<String, String> parameters, String encode);
}
