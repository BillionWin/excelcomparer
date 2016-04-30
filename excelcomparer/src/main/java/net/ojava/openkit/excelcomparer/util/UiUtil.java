package net.ojava.openkit.excelcomparer.util;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class UiUtil {

	@SuppressWarnings("rawtypes")
	public static void initGUI() {
		try {
			UIDefaults defaults = UIManager.getDefaults();

			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Font f = new Font("宋体", Font.PLAIN, 12);
			
			Enumeration keys = defaults.keys();
	        while (keys.hasMoreElements())
	        {
	            Object key = keys.nextElement();
	            Object value = UIManager.get(key);
	            if(value instanceof javax.swing.plaf.FontUIResource) {
	                UIManager.put(key, f);
	            }
	        }
	        
			defaults.remove("SplitPane.border");
			defaults.remove("SplitPaneDivider.border");

		} catch (Exception e) {
		}
	}
	
}
