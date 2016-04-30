package net.ojava.openkit.excelcomparer.poi.excel;

import java.util.Map;

public interface ExcelReaderCallBack {
	public void startSheet(String name);
	public void errorSheet(String name, String error);
	public void endSheet(String name);
	public void processRow(int rowNum, Map<String, String> rowData) throws RuntimeException;
}
