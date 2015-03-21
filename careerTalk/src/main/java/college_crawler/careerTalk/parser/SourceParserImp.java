package college_crawler.careerTalk.parser;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SourceParserImp implements SourceParser {

	public List<String> obtainItemLinksFromDom(String source, String itemPattern) {
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

}
