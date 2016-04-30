package net.ojava.openkit.excelcomparer.poi.excel;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

public class ExcelSheetContentsHandler implements SheetContentsHandler {
	private ExcelReaderCallBack callBack;
	private LinkedHashMap<String, String> currentRowMap;
	private int curRowNum;
	
	public ExcelSheetContentsHandler(ExcelReaderCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	public void startRow(int rowNum) {
		curRowNum = rowNum;
		currentRowMap = new LinkedHashMap<String, String>();
	}

	@Override
	public void cell(String cellReference, String formattedValue) {
		currentRowMap.put(getColumnReference(cellReference), formattedValue);
	}

	@Override
	public void endRow() {
		callBack.processRow(curRowNum, currentRowMap);
	}

	@Override
	public void headerFooter(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	private String getColumnReference(String cellReference) {

		if (StringUtils.isBlank(cellReference)) {
			return "";
		}

		return cellReference.split("[0-9]*$")[0];
	}
}
