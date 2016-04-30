package net.ojava.openkit.excelcomparer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Profile {
	private static Logger log = Logger.getLogger(Profile.class);
	private static Profile instance;
	
	public static final String VERSION = "1.007";
	public static final String OUTPUT_FILE_EXTENSION = ".xlsx";

	
	private static final String PROFILE_FILENAME = "excelcomparer.properties";
	private File profileFile;
	
	private static String KEY_LASTSOURCEFILEPATH1 = "lastsourcefilepath1";
	private File lastSourceFilePath1;
	
	private static String KEY_LASTSOURCEFILEPATH2 = "lastsourcefilepath2";
	private File lastSourceFilePath2;
	
	private static String KEY_LASTOUTPUTFILEPATH = "lastoutputfilepath";
	private File lastOutputFilePath;
	
	private static String KEY_MAXCOLUMNCOUNT = "maxcolumncount";
	private int maxColumnCount;
	
	private static String KEY_KEYCOLUMN1 = "keycolumn1";
	private int keyColumn1;
	
	private static String KEY_KEYCOLUMN2 = "keycolumn2";
	private int keyColumn2;
	
	private static String KEY_KEYSIMILARITY = "keysimilarity";
	private int keySimilarity;
	
	private static String KEY_COMPARECOLUMN1 = "comparecolumn1";
	private int compareColumn1;
	
	private static String KEY_COMPARECOLUMN2 = "comparecolumn2";
	private int compareColumn2;
	
	private static String KEY_COMPARESIMILARITY = "comparesimilarity";
	private int compareSimilarity;
	
	private static String KEY_OUTPUTHEADS = "outputheads";
	private String outputHeads;
	
	
	private static String homePath;
	private static String logPath;

	private Profile() {
		profileFile = new File(getHomePath(), PROFILE_FILENAME);
		
		load();
	}
	
	public static Profile getInstance() {
		if(instance == null) {
			instance = new Profile();
		}
		
		return instance;
	}
	
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	public static String getHomePath() {
		if(homePath == null) {
			String homeDir = System.getProperty("user.home");
			try {
				File dir = new File(homeDir, "ojava");
				if(!dir.exists())
					dir.mkdir();
				
				dir = new File(dir, "excelcomparer");
				if(!dir.exists())
					dir.mkdir();
				
				homePath = dir.getAbsolutePath();
			} catch (Exception e) {
				homePath = homeDir;
			}
		}
		
		return homePath;
	}
	
	public static String getLogPath() {
		if(logPath == null) {
			File f = new File(getHomePath(), "logs");
			try {
				if(!f.exists()) {
					f.mkdir();
				}
				logPath = f.getAbsolutePath();
			} catch (Exception e){
				logPath = getHomePath();
			}
		}
		
		return logPath;
	}
	
	public void load() {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(profileFile));
		} catch (Exception e) {
//			log.debug("load profile fail", e);
		}
			
		String str = getPropertyString(pro, KEY_LASTSOURCEFILEPATH1, null);
		if(str != null) {
			setLastSourceFilePath1(new File(str));
		}
		
		str = getPropertyString(pro, KEY_LASTSOURCEFILEPATH2, null);
		if(str != null) {
			setLastSourceFilePath2(new File(str));
		}
		
		str = getPropertyString(pro, KEY_LASTOUTPUTFILEPATH, null);
		if(str != null) {
			setLastOutputFilePath(new File(str));
		}
		
		maxColumnCount = getPropertyInt(pro, KEY_KEYCOLUMN1, 100);
		if(maxColumnCount < 10) {
			maxColumnCount = 10;
		} else if(maxColumnCount > 100) {
			maxColumnCount = 100;
		}
		
		keyColumn1 = getPropertyInt(pro, KEY_KEYCOLUMN1, 0);
		if(keyColumn1 < 0 || keyColumn1 > 100) {
			keyColumn1 = 0;
		}
		
		keyColumn2 = getPropertyInt(pro, KEY_KEYCOLUMN2, 0);
		if(keyColumn2 < 0 || keyColumn2 > 100) {
			keyColumn2 = 0;
		}
		
		keySimilarity = getPropertyInt(pro, KEY_KEYSIMILARITY, 85);
		if(keySimilarity < 0) {
			keySimilarity = 0;
		} else if(keySimilarity > 100) {
			keySimilarity = 100;
		}
		
		compareColumn1 = getPropertyInt(pro, KEY_COMPARECOLUMN1, 0);
		if(compareColumn1 < 0 || compareColumn1 > 100) {
			compareColumn1 = 0;
		}
		
		compareColumn2 = getPropertyInt(pro, KEY_COMPARECOLUMN2, 0);
		if(compareColumn2 < 0 || compareColumn2 > 100) {
			compareColumn2 = 0;
		}
		
		compareSimilarity = getPropertyInt(pro, KEY_COMPARESIMILARITY, 85);
		if(compareSimilarity < 0) {
			compareSimilarity = 0;
		} else if(compareSimilarity > 100) {
			compareSimilarity = 100;
		}
		
		outputHeads = getPropertyString(pro, KEY_OUTPUTHEADS, "");
	}
	
	public void save() {
		Properties pro = new Properties();
		if(lastSourceFilePath1 != null)
			pro.setProperty(KEY_LASTSOURCEFILEPATH1, lastSourceFilePath1.getAbsolutePath());
		
		if(lastSourceFilePath2 != null)
			pro.setProperty(KEY_LASTSOURCEFILEPATH2, lastSourceFilePath2.getAbsolutePath());
		
		if(lastOutputFilePath != null)
			pro.setProperty(KEY_LASTOUTPUTFILEPATH, lastOutputFilePath.getAbsolutePath());
		
		pro.setProperty(KEY_MAXCOLUMNCOUNT, String.valueOf(maxColumnCount));
		
		pro.setProperty(KEY_KEYCOLUMN1, String.valueOf(keyColumn1));
		
		pro.setProperty(KEY_KEYCOLUMN2, String.valueOf(keyColumn2));
		
		pro.setProperty(KEY_KEYSIMILARITY, String.valueOf(keySimilarity));
		
		pro.setProperty(KEY_COMPARECOLUMN1, String.valueOf(compareColumn1));
		
		pro.setProperty(KEY_COMPARECOLUMN2, String.valueOf(compareColumn2));
		
		pro.setProperty(KEY_COMPARESIMILARITY, String.valueOf(compareSimilarity));
		
		pro.setProperty(KEY_OUTPUTHEADS, outputHeads);
		
		try {
			pro.store(new FileOutputStream(profileFile), "excel comparator profile file");
		} catch (Exception e) {
			log.debug("save profile fail", e);
		}
	}
	
	private int getPropertyInt(Properties pro, String key, int defaultValue) {
		String str = pro.getProperty(key);
		if(str != null) {
			str = str.trim();
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {}
		}

		return defaultValue;
	}
	
	private String getPropertyString(Properties pro, String key, String defaultValue) {
		String str = pro.getProperty(key);
		if(str != null) {
			str = str.trim();
			if(str.length() > 0)
				return str.trim();
		}
		
		return defaultValue;
	}
	
	public File getLastSourceFilePath1() {
		return lastSourceFilePath1;
	}
	
	public void setLastSourceFilePath1(File path) {
		this.lastSourceFilePath1 = path;
	}
	
	public File getLastSourceFilePath2() {
		return lastSourceFilePath2;
	}
	
	public void setLastSourceFilePath2(File path) {
		this.lastSourceFilePath2 = path;
	}
	
	public File getLastOutputFilePath() {
		return lastOutputFilePath;
	}
	
	public void setLastOutputFilePath(File path) {
		this.lastOutputFilePath = path;
	}

	public int getKeyColumn1() {
		return keyColumn1;
	}

	public void setKeyColumn1(int keyColumn1) {
		this.keyColumn1 = keyColumn1;
	}

	public int getKeyColumn2() {
		return keyColumn2;
	}

	public void setKeyColumn2(int keyColumn2) {
		this.keyColumn2 = keyColumn2;
	}

	public int getKeySimilarity() {
		return keySimilarity;
	}

	public void setKeySimilarity(int keySimilarity) {
		this.keySimilarity = keySimilarity;
	}

	public int getCompareColumn1() {
		return compareColumn1;
	}

	public void setCompareColumn1(int compareColumn1) {
		this.compareColumn1 = compareColumn1;
	}

	public int getCompareColumn2() {
		return compareColumn2;
	}

	public void setCompareColumn2(int compareColumn2) {
		this.compareColumn2 = compareColumn2;
	}

	public int getCompareSimilarity() {
		return compareSimilarity;
	}

	public void setCompareSimilarity(int compareSimilarity) {
		this.compareSimilarity = compareSimilarity;
	}

	public int getMaxColumnCount() {
		return maxColumnCount;
	}

	public void setMaxColumnCount(int maxColumnCount) {
		this.maxColumnCount = maxColumnCount;
	}

	public String getOutputHeads() {
		return outputHeads;
	}

	public void setOutputHeads(String outputHeads) {
		this.outputHeads = outputHeads;
	}
}
