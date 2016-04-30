package net.ojava.openkit.excelcomparer.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.ojava.openkit.excelcomparer.util.TimeUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ResultCache {
	private ArrayList<String> headerList = new ArrayList<String>();
	private ArrayList<ArrayList<String>> contentList = new ArrayList<ArrayList<String>>();
	
	public ResultCache() {
		
	}
	
	public void addInOrder(ArrayList<String> itemData) {
		int id = Integer.parseInt(itemData.get(0));
		for(int i=0; i<contentList.size(); i++) {
			int tid = Integer.parseInt(contentList.get(i).get(0));
			if(id < tid) {
				contentList.add(i, itemData);
				return;
			}
		}
		
		contentList.add(itemData);
	}
	
	public void append(ArrayList<String> itemData) {
		contentList.add(itemData);
	}

	public ArrayList<String> getHeaderList() {
		return headerList;
	}
	
	public void writeToExcel(File outputFile, OutputConfig config) throws Exception {
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		FileOutputStream out = null;
		
		try {
				Sheet sheet = wb.createSheet(TimeUtil.getCurDateString());
				Row row = sheet.createRow(0);
				int id = 0;
				for(ResultHead th : config.getResultHeadList()) {
					row.createCell(id++).setCellValue(th.getText());
				}
				
				int rowIndex = 1;
				for(int i=0; i<contentList.size(); i++) {
					ArrayList<String> itemData = contentList.get(i);
					row = sheet.createRow(rowIndex++);
					for(int j=0; j<itemData.size(); j++) {
						row.createCell(j).setCellValue(itemData.get(j));
					}
				}
			
			out = new FileOutputStream(outputFile);
			wb.write(out);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception eee){}
			}
			
			wb.dispose();
			wb = null;

			System.gc();
		}
	}
}
