package college_crawler.careerTalk.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTool {
	
	@SuppressWarnings("deprecation")
	public static String nextDate(String currentDate) {
		SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		d.setYear(Integer.parseInt(currentDate.split("-")[0])-1900);
		d.setMonth(Integer.parseInt(currentDate.split("-")[1])-1);
		d.setDate(Integer.parseInt(currentDate.split("-")[2]));
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(d);
		gc.add(5,+1);
		return sf.format(gc.getTime());
	}
	
}
