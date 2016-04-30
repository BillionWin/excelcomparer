package net.ojava.openkit.excelcomparer.gui;

import java.io.File;
import java.text.MessageFormat;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.ojava.openkit.excelcomparer.App;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.Profile;
import net.ojava.openkit.excelcomparer.util.StrUtil;

public class GuiUtil {
	public static void showErrorDlg(String error) {
		JOptionPane.showMessageDialog(App.mainFrame, error, Resource.getInstance().getResourceString(Resource.KEY_TITLE_ERRORTIP), JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarningDlg(String warning) {
		JOptionPane.showMessageDialog(App.mainFrame, warning, Resource.getInstance().getResourceString(Resource.KEY_TITLE_INFOTIP), JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showTipDlg(String tip) {
		JOptionPane.showMessageDialog(App.mainFrame, tip, Resource.getInstance().getResourceString(Resource.KEY_TITLE_INFOTIP), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean showConfirmDlg(String message) {
		return JOptionPane.showConfirmDialog(App.mainFrame, message, Resource.getInstance().getResourceString(Resource.KEY_TITLE_CONFIRMTIP), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}

	public static void updateTableColumnWidth(JTable table, int[] widths) {
		TableColumnModel tcm = table.getColumnModel();
		for(int i=0; i<tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setPreferredWidth(widths[i]);
		}
	}
	
	public static JButton createImageButton(ImageIcon icon, String text, String tooltip) {
		JButton btn = new JButton(icon);
		btn.setText(text);
		btn.setToolTipText(tooltip);
		btn.setHorizontalTextPosition(JButton.RIGHT);
		btn.setVerticalTextPosition(JButton.CENTER);

		return btn;
	}
	
	public static void addChildTreeNode(DefaultMutableTreeNode parentNode, DefaultMutableTreeNode childNode) {
		Object childObj = childNode.getUserObject();
		
		int index = 0;
		for(; index<parentNode.getChildCount(); index++) {
			DefaultMutableTreeNode tn = (DefaultMutableTreeNode)parentNode.getChildAt(index);
			Object tmpObj = tn.getUserObject();
			
			if(StrUtil.compare(childObj.toString(), tmpObj.toString()) > 0)
				break;
		}
		
		parentNode.insert(childNode, index);
	}

	@SuppressWarnings("unchecked")
	public static void expandAllTree(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		if (node.getChildCount() > 0) {
			for (Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
				TreeNode n = e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAllTree(tree, path, expand);

			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
	
	public static File selectInputFile(String fileType, File currentDir, String ... extensions) {
		JFileChooser chooser = new JFileChooser(currentDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, extensions);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(App.mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
	}
	
	public static File selectOutputFile(String fileType, File currentDir, String defaultFileName) {
		JFileChooser chooser = new JFileChooser(currentDir) {
			private static final long serialVersionUID = 3348615193412527885L;

			@Override
			public void approveSelection() {
				File savedFile = this.getSelectedFile();
				if (!savedFile.getName().endsWith(Profile.OUTPUT_FILE_EXTENSION)) {
					savedFile = new File(savedFile.getParentFile(), savedFile.getName()
							+ Profile.OUTPUT_FILE_EXTENSION);
				}
				if (savedFile.exists()) {
					String tip = MessageFormat.format(Resource.getInstance().getResourceString(Resource.KEY_INFO_OVERRIDEFILEHTML), savedFile.getName());
					int overwriteSelect = JOptionPane.showConfirmDialog(this,
							tip, 
							Resource.getInstance().getResourceString(Resource.KEY_TITLE_CONFIRMTIP),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (overwriteSelect != JOptionPane.YES_OPTION) {
						return;
					}
				}
				
				super.approveSelection();
			}
		};
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				MessageFormat.format("{0}(*{1})", fileType, Profile.OUTPUT_FILE_EXTENSION), 
				Profile.OUTPUT_FILE_EXTENSION.substring(1));
		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(chooser.getCurrentDirectory(), MessageFormat.format("{0}{1}", defaultFileName, Profile.OUTPUT_FILE_EXTENSION)));
		int returnVal = chooser.showSaveDialog(App.mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith(Profile.OUTPUT_FILE_EXTENSION)) {
				file = new File(file.getParentFile(), file.getName()
						+ Profile.OUTPUT_FILE_EXTENSION);
			}
			
			return file;
		}
		
		return null;
	}
}
