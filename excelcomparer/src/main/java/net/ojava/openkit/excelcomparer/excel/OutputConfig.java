package net.ojava.openkit.excelcomparer.excel;

import java.util.ArrayList;
import java.util.Hashtable;

public class OutputConfig {
	private Hashtable<String, ResultHead> keyTable1 = new Hashtable<String, ResultHead>();
	private Hashtable<String, ResultHead> keyTable2 = new Hashtable<String, ResultHead>();
	private ArrayList<ResultHead> resultHeadList = new ArrayList<ResultHead>();
	
	public void addResultHead(ResultHead head) {
		if(head.getColumn1() != null)
			keyTable1.put(head.getColumn1(), head);
		if(head.getColumn2() != null)
			keyTable2.put(head.getColumn2(), head);
		resultHeadList.add(head);
	}
	
	public void removeResultHead(int index) {
		ResultHead head = resultHeadList.remove(index);
		if(head != null) {
			if(head.getColumn1() != null)
				keyTable1.remove(head.getColumn1());
			if(head.getColumn2() != null)
				keyTable2.remove(head.getColumn2());
		}
	}
	
	public boolean containKey1(String key1) {
		return keyTable1.containsKey(key1);
	}
	
	public boolean containKey2(String key2) {
		return keyTable2.containsKey(key2);
	}
	
	public ArrayList<ResultHead> getResultHeadList() {
		return resultHeadList;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(ResultHead th : resultHeadList) {
			if(count > 0)
				sb.append(";");
			
			if(th.getColumn1() != null)
				sb.append(th.getColumn1());
			sb.append(",");
			if(th.getColumn2() != null)
				sb.append(th.getColumn2());
			sb.append(",");
			sb.append(th.getText());
			
			count++;
		}
		
		return sb.toString();
	}
	
	public static OutputConfig parse(String str) {
		OutputConfig config = new OutputConfig();
		
		if(str == null)
			return config;
		
		str = str.trim();
		String ps[] = str.split(";");
		if(ps == null || ps.length==0)
			return config;
		
		for(String tp : ps) {
			if(tp != null)
				tp = tp.trim();
			
			if(tp == null || tp.length() == 0)
				continue;
			
			String[] ts = tp.split(",");
			if(ts == null || ts.length == 0)
				continue;
			
			int index = 0;
			
			ResultHead head = new ResultHead();

			if(ts[index] != null)
				ts[index] = ts[index].trim();
			if(ts[index] == null || ts[index].length() == 0) {
				head.setColumn1(null);
				index ++;
			} else {
				head.setColumn1(ts[index]);
				index ++;
			}
			
			if(index >= ts.length)
				continue;
			
			
			if(ts[index] != null)
				ts[index] = ts[index].trim();
			if(ts[index] == null || ts[index].length() == 0) {
				head.setColumn2(null);
				index ++;
			} else {
				head.setColumn2(ts[index]);
				index ++;
			}
			
			if(index >= ts.length)
				continue;
			
			
			if(ts[index] != null)
				ts[index] = ts[index].trim();
			if(ts[index] == null || ts[index].length() == 0) {
				head.setText("");
			} else {
				head.setText(ts[index]);
			}
			
			config.addResultHead(head);
		}
		
		return config;
	}
}
