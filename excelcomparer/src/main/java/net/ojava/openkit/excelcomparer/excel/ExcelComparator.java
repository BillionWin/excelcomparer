package net.ojava.openkit.excelcomparer.excel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;

import net.ojava.openkit.excelcomparer.util.Similarity;
import net.ojava.openkit.excelcomparer.util.StrUtil;

public class ExcelComparator {
	public void compare(LoadConfig loadConfig, OutputConfig config) throws Exception {
		ResultCache rc = new ResultCache();
		HashSet<String> keySet = new HashSet<String>();
		keySet.addAll(loadConfig.cache1.getContentTable().keySet());
		for(String name : keySet) {
			ArrayList<ArrayList<String>> itemList1 = loadConfig.cache1.getContentTable().get(name);
			ArrayList<ArrayList<String>> itemList2 = loadConfig.cache2.getContentTable().get(name);
			if(itemList1 != null && itemList2 != null) {
L:				for(int i=0; i<itemList1.size(); i++) {
					String id1 = itemList1.get(i).get(loadConfig.compareColumn1);
					for(int j=0; j<itemList2.size(); j++) {
						String id2 = itemList2.get(j).get(loadConfig.compareColumn2);
//						System.out.println(id1 + " --- " + id2);
						if(Similarity.SimilarDegree(id1, id2) > loadConfig.compareSimilarity / 100.0) {
							ArrayList<String> itemData1 = itemList1.remove(i);
							if(itemList1.size() == 0)
								loadConfig.cache1.getContentTable().remove(name);
							ArrayList<String> itemData2 = itemList2.remove(j);
							if(itemList2.size() == 0)
								loadConfig.cache2.getContentTable().remove(name);
							
							ArrayList<String> itemData = new ArrayList<String>();
							for(ResultHead th : config.getResultHeadList()) {
								if(th.getColumn1() != null) {
									itemData.add(itemData1.get(StrUtil.stringToIndex(th.getColumn1())));
								} else if(th.getColumn2() != null) {
									itemData.add(itemData2.get(StrUtil.stringToIndex(th.getColumn2())));
								} else {
									itemData.add("");
								}
							}
							
							rc.addInOrder(itemData);
							break L;
						}
					}
				}
			}
		}
		
		Enumeration<ArrayList<ArrayList<String>>> ee = loadConfig.cache2.getContentTable().elements();
		while(ee.hasMoreElements()) {
			ArrayList<ArrayList<String>> list = ee.nextElement();
			if(list != null) {
				for(ArrayList<String> d : list) {
					ArrayList<String> itemData = new ArrayList<String>();
					for(ResultHead th : config.getResultHeadList()) {
						if(th.getColumn2() != null) {
							itemData.add(d.get(StrUtil.stringToIndex(th.getColumn2())));
						} else {
							itemData.add("");
						}
					}
					
					rc.addInOrder(itemData);
				}
			}
		}
		
		ee = loadConfig.cache1.getContentTable().elements();
		while(ee.hasMoreElements()) {
			ArrayList<ArrayList<String>> list = ee.nextElement();
			if(list != null) {
				for(ArrayList<String> d : list) {
					ArrayList<String> itemData = new ArrayList<String>();
					for(ResultHead th : config.getResultHeadList()) {
						if(th.getColumn1() != null) {
							itemData.add(d.get(StrUtil.stringToIndex(th.getColumn1())));
						} else {
							itemData.add("");
						}
					}
					
					rc.append(itemData);
				}
			}
		}
		
		rc.writeToExcel(loadConfig.outputFile, config);
	}
}
