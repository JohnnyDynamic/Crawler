package college_crawler.careerTalk.parser;

import java.util.List;

public interface SourceParser {
	
	public List<String> obtainItemLinksFromDom(String source, String itemPattern);
	
	
	
}
