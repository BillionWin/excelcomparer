package net.ojava.openkit.excelcomparer.util;

import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	private static RuleBasedCollator collator = (RuleBasedCollator)Collator.getInstance(java.util.Locale.CHINA);
	
	public static int compare(String str1, String str2) {
		CollationKey c1 = collator.getCollationKey(str1);
		CollationKey c2 = collator.getCollationKey(str2);

		return collator.compare(((CollationKey) c2).getSourceString(), ((CollationKey) c1).getSourceString());
	}
	
	public static int stringToIndex(String str) {
		int result = 0;
		
		str = str.toUpperCase();
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			
			result = result * 10 + ((int)c - (int)'A');
		}
		
		return result;
	}
	
	public static String indexToString(int index) {
		if(index < 0)
			return "";
		
		int system = 'Z' - 'A' + 1;
		StringBuffer buf = new StringBuffer();
		while(true) {
			if(index >= system) {
				int mod = index % system;
				buf.insert(0, (char)('A' + mod));
				
				index = index / system;
			} else {
				buf.insert(0, (char)('A' + index));
				break;
			}
		}
		
		return buf.toString();
	}
	
	public static String doubleValue(double num) {
		return new BigDecimal(num).toString();
	}
	
	public static String doubleValue(double num, int scale) {
//		DecimalFormat df=new DecimalFormat("#.##");
		BigDecimal bd = new BigDecimal(num);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}
	
	/**
	 * retrieve the first number from 'src' after the keyword given by 'afterKeyword' parameter
	 * @param src  source string
	 * @param afterKeyword
	 * @return a Double object if found afterKeyword and number, otherwise null
	 */
	public static Double parseFirstNumber(String src, String afterKeyword) {
		int startPos = src.indexOf(afterKeyword);
		if(startPos == -1) {
			startPos = startPos + afterKeyword.length();
			return parseFirstNumber(src, startPos);
		} else
			return null;
	}
	
	/**
	 * retrieve the first number from 'src' from the position indicated by 'startIndex'
	 * @param src
	 * @param startIndex
	 * @return a Double object if found number, otherwise null
	 */
	public static Double parseFirstNumber(String src, int startIndex) {
		if(src == null)
			return null;
		
		boolean found = false;
		int pointCount = 0;
		StringBuffer buf = new StringBuffer();
		for(int i=startIndex; i<src.length(); i++) {
			char ch = src.charAt(i);
			if(ch>='0' && ch<='9') {
				buf.append(ch);
				found = true;
			} else if(ch=='.' && found) {
				if(pointCount == 0) {
					pointCount ++;
					buf.append(ch);
				} else {
					break;
				}
			} else if(ch==',' || !found) {
				continue;
			} else {
				break;
			}
		}
		
		if(buf.length()==0)
			return null;
		else {
			return Double.parseDouble(buf.toString());
		}
	}
	
	public static double[] parseNumbers(String src) {
		if(src == null) 
			return null;

		String nums[] = src.split("\\D+");
		if(nums == null)
			return null;

		double [] values = new double[nums.length];
		int index = 0;
		for(String ts : nums) {
			try {
				values[index++] = Double.parseDouble(ts);
			} catch (Exception e){}
		}
		
		return values;
	}
	
	public static Double summariseNumbers(String src) {
		double[] values = parseNumbers(src);
		if(values == null)
			return null;
		
		double sum = 0;
		for(double tv : values) {
			sum += tv;
		}
		
		return sum;
	}
	
	public static void fillSet(String str, String regSeperator, Set<String> set) {
		if(str != null) {
			String [] ss = str.split(regSeperator);
			if(ss != null) {
				for(String ts : ss) {
					if(ts.length() > 0)
						set.add(ts);
				}
			}
		}
	}
	
	public static int parseMaxIntNumWithSuffix(String src, String suffix) {
		Pattern p = Pattern.compile("\\d+" + suffix);
		int num = -1;
		Matcher m = p.matcher(src);
		while(m.find()) {
			String ts = m.group();
			ts = ts.substring(0, ts.length() - 1);
			int tn = Integer.parseInt(ts);
			if(num < tn)
				num = tn;
		}
		
		return num;
	}
}
