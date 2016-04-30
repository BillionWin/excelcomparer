package net.ojava.openkit.excelcomparer.poi.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ExcelReader {
	private ExcelReaderCallBack callBack;
	private ExcelSheetContentsHandler handler;
	
	public ExcelReader(ExcelReaderCallBack callBack) {
		this.callBack = callBack;
		handler = new ExcelSheetContentsHandler(callBack);
	}

	public void read(File excelFile, Set<String> sheetNames) throws Exception {
		ReadOnlySharedStringsTable strings;
		OPCPackage pkg = null;

		try {
			pkg = OPCPackage.open(excelFile);
			strings = new ReadOnlySharedStringsTable(pkg);
			XSSFReader xssfReader = new XSSFReader(pkg);
			StylesTable styles = xssfReader.getStylesTable();
			XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
			while (sheets.hasNext()) {
				InputStream stream = sheets.next();
				String sheetName = sheets.getSheetName();
				
				if ((sheetNames == null) || (sheetNames.contains(sheetName))) {
					if (null != callBack)
						this.callBack.startSheet(sheetName);
					
					try {
						readSheet(styles, strings, stream);
						
						if (null != callBack)
							this.callBack.endSheet(sheetName);
					} catch (Exception e) {
						if (null != callBack)
							this.callBack.errorSheet(sheetName, e.getMessage());
						throw e;
					}
				}
				IOUtils.closeQuietly(stream);
			}
		} finally {
			if(pkg != null) {
				pkg.close();
			}
		}
	}
	
	private void readSheet(StylesTable styles,
			ReadOnlySharedStringsTable sharedStringsTable,
			InputStream sheetInputStream) throws IOException,
			ParserConfigurationException, SAXException {

		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		XMLReader sheetParser = saxFactory.newSAXParser().getXMLReader();

		ContentHandler handler = new XSSFSheetXMLHandler(styles,
				sharedStringsTable, this.handler, true);

		sheetParser.setContentHandler(handler);
		sheetParser.parse(new InputSource(sheetInputStream));
	}
}
