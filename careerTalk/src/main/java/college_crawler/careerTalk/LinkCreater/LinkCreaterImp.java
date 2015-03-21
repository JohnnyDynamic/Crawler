package college_crawler.careerTalk.LinkCreater;

public class LinkCreaterImp implements LinkCreater{

	public String create(int num, String linkPattern) {
		return linkPattern.replace("*#*", num+"");
	}

	public String create(String date, String linkPattern) {
		return linkPattern.replace("*#*", linkPattern);
	}
	
}
