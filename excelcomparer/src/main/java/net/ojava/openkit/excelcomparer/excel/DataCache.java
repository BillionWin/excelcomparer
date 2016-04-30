package net.ojava.openkit.excelcomparer.excel;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import net.ojava.openkit.excelcomparer.poi.excel.ExcelReader;
import net.ojava.openkit.excelcomparer.poi.excel.ExcelReaderCallBack;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.StrUtil;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;

public class DataCache {
	private ArrayList<String> headerList = new ArrayList<String>();
	private Hashtable<String, ArrayList<ArrayList<String>>> contentTable = new Hashtable<String, ArrayList<ArrayList<String>>>();
	
	private File excelFile;
	private int keyColumnIndex;

	private class MyExcelReaderCallBack implements ExcelReaderCallBack {
		@Override
		public void startSheet(String name) {
		}
		
		@Override
		public void errorSheet(String sheetName, String error) {
//			PSheet sheet = sheetTable.get(sheetName);
//			sheetList.remove(sheet);
		}

		@Override
		public void endSheet(String name) {
		}

		@Override
		public void processRow(int rowNum, Map<String, String> rowData) {
			if(rowNum == 0) {
				for(int i=0; i<rowData.values().size(); i++) {
					String colName = StrUtil.indexToString(i);
					String rowName = rowData.get(colName);
					if(rowName == null) {
						throw new RuntimeException(MessageFormat.format(Resource.getInstance().getResourceString(Resource.KEY_ERROR_HEADNULL), colName));
					}
					headerList.add(rowName);
				}
			} else {
				ArrayList<String> itemData = new ArrayList<String>();
				for(int i=0; i<headerList.size(); i++) {
					String cellValue = rowData.get(StrUtil.indexToString(i));
					if(cellValue == null)
						itemData.add("");
					else
						itemData.add(cellValue.trim());
				}
				
				ArrayList<ArrayList<String>> itemList = contentTable.get(itemData.get(keyColumnIndex));
				if(itemList == null) {
					itemList = new ArrayList<ArrayList<String>>();
					contentTable.put(itemData.get(keyColumnIndex), itemList);
				}
				itemList.add(itemData);
			}
		}
		
	}
	
	private DataCache() {
		
	}
	
	public static DataCache load(File file, int keyColumnIndex) throws Exception {
		DataCache cache = new DataCache();
		cache.excelFile = file;
		cache.keyColumnIndex = keyColumnIndex;
		
		String sheetName = null;
		OPCPackage pkg = null;
		try {
			pkg = OPCPackage.open(file);
			XSSFReader r = new XSSFReader(pkg);
			XSSFReader.SheetIterator it = (XSSFReader.SheetIterator)r.getSheetsData();
			if(it.hasNext()) {
				it.next();
				sheetName = it.getSheetName();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(pkg != null)
				pkg.close();
		}
		
		if(sheetName == null)
			throw new Exception(Resource.getInstance().getResourceString(Resource.KEY_ERROR_EXCELEMPTY));
		
		Set<String> names = new HashSet<String>();
		names.add(sheetName);
		ExcelReader reader = new ExcelReader(cache.new MyExcelReaderCallBack());
		reader.read(file, names);

		return cache;
	}

	public ArrayList<String> getHeaderList() {
		return headerList;
	}

	public Hashtable<String, ArrayList<ArrayList<String>>> getContentTable() {
		return contentTable;
	}

	public File getExcelFile() {
		return excelFile;
	}
}
