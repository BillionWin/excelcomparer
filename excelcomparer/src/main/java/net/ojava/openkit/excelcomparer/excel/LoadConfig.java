package net.ojava.openkit.excelcomparer.excel;

import java.io.File;

public class LoadConfig {
	public int keyColumn1;
	public int keyColumn2;
	public int keySimilarity;
	public int compareColumn1;
	public int compareColumn2;
	public int compareSimilarity;
	public File excelFile1;
	public File excelFile2;
	public File outputFile;

	public DataCache cache1;
	public DataCache cache2;
}
