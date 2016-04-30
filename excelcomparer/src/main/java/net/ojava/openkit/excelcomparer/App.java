package net.ojava.openkit.excelcomparer;

import java.text.MessageFormat;
import java.util.Properties;

import javax.swing.JOptionPane;

import net.ojava.openkit.excelcomparer.gui.MainFrame;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.Profile;
import net.ojava.openkit.excelcomparer.util.UiUtil;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger log = Logger.getLogger(App.class);
	public static MainFrame mainFrame;

	public static void main(String[] args) {
		initLogger();

		log.info("begin to run excel comparator application");
		
		try {
			startApp();
		} catch (Throwable e) {
			log.debug("start app fail", e);
			JOptionPane.showMessageDialog(null, e.getMessage());
			exitApp();
			return;
		}
		
		try {
			showMainWnd();
			
		} catch (Throwable t) {
			log.debug("show main wnd", t);
			JOptionPane.showMessageDialog(null, t.getMessage());
			
			exitApp();
		}
	}

	private static void initLogger() {
		try {
			Properties p = new Properties();
			p.setProperty("log4j.logger.org.hibernate", "ERROR");
			PropertyConfigurator.configure(p);
			
			PatternLayout layout = new PatternLayout();
			layout.setConversionPattern("%d [%t] %-5p %c- %m%n");
			
			ConsoleAppender cAppender = new ConsoleAppender(layout, "System.out");
			cAppender.setEncoding("utf-8");
			Logger.getRootLogger().addAppender(cAppender);
			
			String datePattern = "'.'yyyy-MM-dd";
			String logFile = MessageFormat.format("{0}{1}{2}", Profile.getLogPath(), Profile.getFileSeparator(), "excelcomparer.log");
			DailyRollingFileAppender rfAppender = new DailyRollingFileAppender(layout, logFile, datePattern);
			rfAppender.setAppend(true);
			rfAppender.setEncoding("utf-8");
			Logger.getRootLogger().addAppender(rfAppender);

			
			Logger.getRootLogger().setLevel(Level.DEBUG);
		}
		catch(Exception e) {
		}
	}
	
	public static void startApp() throws Exception {
		UiUtil.initGUI();
		Resource.getInstance().initResources();
		log.info("initialize font successfully");
	}
	
	public static void exitApp() {
		Profile.getInstance().save();
		Resource.getInstance().freeResources();
		log.info("exit excel comparator application");
		System.exit(0);
	}
	
	public static void showMainWnd() {
		mainFrame = new MainFrame();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}
