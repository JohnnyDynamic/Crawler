package college_crawler.careerTalk.tool;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public class CollectionTool {
	
	/**
	 * parse Map to NameValuePair array
	 * @param map
	 * @return
	 */
	public static NameValuePair[] map2Pair(Map<String, String> map) {
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
	
}
