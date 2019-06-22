package light.mvc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeUtil {
	private static SimpleDateFormat sdf;
	
	static{
		sdf = new SimpleDateFormat();
	}
	
	public static String getDateString(Date date, String pattern){
		sdf.applyPattern(pattern);
		
		return sdf.format(date);
	}
	
	public static Date getDate(String dateString, String pattern){
		try {
			sdf.applyPattern(pattern);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
