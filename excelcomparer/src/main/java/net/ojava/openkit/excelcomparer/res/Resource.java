package net.ojava.openkit.excelcomparer.res;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Resource {
	private static Logger log = Logger.getLogger(Resource.class);
	
	public static final String KEY_APPNAME = "APP.NAME";
	public static final String KEY_APPVERSION = "APP.VERSION";
	public static final String KEY_APPRELEASEDATE = "APP.RELEASEDATE";
	public static final String KEY_DEFAULTFILETYPES = "DATA.DEFAULTFILETYPES";
	
	public static final String KEY_TIP_EXITCONFIRM="TIP.EXITCONFIRM";
	public static final String KEY_TIP_COMPAREOK="TIP.COMPAREOK";
	
	public static final String KEY_TITLE_SELECTSOURCEFOLDER="TITLE.SELECTSOURCEFOLDER";
	public static final String KEY_TITLE_SELECTTARGETFOLDER="TITLE.SELECTTARGETFOLDER";
	public static final String KEY_TITLE_ERRORTIP="TITLE.ERRORTIP";
	public static final String KEY_TITLE_INFOTIP="TITLE.INFOTIP";
	public static final String KEY_TITLE_CONFIRMTIP="TITLE.CONFIRMTIP";
	
	public static final String KEY_LABEL_EXCELFILE="LABEL.EXCELFILE";
	public static final String KEY_LABEL_SOURCETARGETFILE="LABEL.SOURCETARGETFILE";
	public static final String KEY_LABEL_SOURCEFILE1="LABEL.SOURCEFILE1";
	public static final String KEY_LABEL_SOURCEFILE2="LABEL.SOURCEFILE2";
	public static final String KEY_LABEL_OUTPUTFILE="LABEL.OUTPUTFILE";
	public static final String KEY_LABEL_PREVIOUS="LABEL.PREVIOUS";
	public static final String KEY_LABEL_NEXT="LABEL.NEXT";
	public static final String KEY_LABEL_COMPARE="LABEL.COMPARE";
	public static final String KEY_LABEL_RESTART="LABEL.RESTART";
	public static final String KEY_LABEL_EXIT="LABEL.EXIT";
	public static final String KEY_LABEL_KEYCOLUMN="LABEL.KEYCOLUMN";
	public static final String KEY_LABEL_KEYCOLUMN1="LABEL.KEYCOLUMN1";
	public static final String KEY_LABEL_KEYCOLUMN2="LABEL.KEYCOLUMN2";
	public static final String KEY_LABEL_KEYSIMILARITY="LABEL.KEYSIMILARITY";
	public static final String KEY_LABEL_COMPARECOLUMN="LABEL.COMPARECOLUMN";
	public static final String KEY_LABEL_COMPARECOLUMN1="LABEL.COMPARECOLUMN1";
	public static final String KEY_LABEL_COMPARECOLUMN2="LABEL.COMPARECOLUMN2";
	public static final String KEY_LABEL_COMPARESIMILARITY="LABEL.COMPARESIMILARITY";
	public static final String KEY_LABEL_INDEX="LABEL.INDEX";
	public static final String KEY_LABEL_INDEX1="LABEL.INDEX1";
	public static final String KEY_LABEL_INDEX2="LABEL.INDEX2";
	public static final String KEY_LABEL_TEXT="LABEL.TEXT";
	public static final String KEY_LABEL_ADD="LABEL.ADD";
	public static final String KEY_LABEL_DELETE="LABEL.DELETE";
	public static final String KEY_LABEL_RESULTCOLUMN="LABEL.RESULTCOLUMN";
	
	public static final String KEY_INFO_OVERRIDEFILEHTML="INFO.OVERRIDEFILEHTML";
	public static final String KEY_INFO_DEFAULTOUTPUTFILENAME="INFO.DEFAULTOUTPUTFILENAME";
	
	public static final String KEY_ERROR_HEADNULL="ERROR.HEADNULL";
	public static final String KEY_ERROR_EXCELEMPTY="ERROR.EXCELEMPTY";
	public static final String KEY_ERROR_INVALIDCOLINDEX="ERROR.INVALIDCOLINDEX";
	public static final String KEY_ERROR_INVALIDSIMILARITY="ERROR.INVALIDSIMILARITY";
	public static final String KEY_ERROR_EMPTYEXCELFILE1="ERROR.EMPTYEXCELFILE1";
	public static final String KEY_ERROR_EMPTYEXCELFILE2="ERROR.EMPTYEXCELFILE2";
	public static final String KEY_ERROR_EMPTYOUTPUTFILE="ERROR.EMPTYOUTPUTFILE";
	public static final String KEY_ERROR_EMPTYOUTPUTCOLUMN="ERROR.EMPTYOUTPUTCOLUMN";
	
	public static final String KEY_MSG_CONFIRMDELETERESULT="MSG.CONFIRMDELETERESULT";
	
	public static final int TABLE_ROW_HEIGHT = 24;
	
	public static Locale cnLocale = new Locale("zh", "CN");
	public static Locale enLocale = new Locale("us", "EN");
	private ResourceBundle resources;
	
	private static  Resource instance;
	
	private Resource() {
	}
	
	public static Resource getInstance() {
		if(instance == null)
			instance = new Resource();
		return instance;
	}
	
	/**
	 * Loads the resources
	 * 
	 * @param display the display
	 */
	public void initResources() throws Exception {
		resources = ResourceBundle.getBundle("res.res", Locale.getDefault());
	}
	/**
	 * Frees the resources
	 */
	public void freeResources() {
	}

	public String getResourceString(String key) {
		try {
			 return resources.getString(key);
		} catch (Exception e) {
			log.debug("Read resource error: " + key, e);
		}

		return "";
	}
}
