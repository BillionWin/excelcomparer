package net.ojava.openkit.excelcomparer.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.ojava.openkit.excelcomparer.excel.DataCache;
import net.ojava.openkit.excelcomparer.excel.ExcelComparator;
import net.ojava.openkit.excelcomparer.excel.LoadConfig;
import net.ojava.openkit.excelcomparer.excel.OutputConfig;
import net.ojava.openkit.excelcomparer.excel.ResultHead;
import net.ojava.openkit.excelcomparer.excel.SourceHead;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.Profile;
import net.ojava.openkit.excelcomparer.util.StrUtil;

import org.apache.log4j.Logger;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 2943712223391297290L;
	private static Logger log = Logger.getLogger(OutputPanel.class);
	
	private SourceHeadTableModel sourceHeadTableModel1 = new SourceHeadTableModel();
	private HeadTable sourceHeadTable1 = new HeadTable(sourceHeadTableModel1, new int[]{50, 100});
	private SourceHeadTableModel sourceHeadTableModel2 = new SourceHeadTableModel();
	private HeadTable sourceHeadTable2 = new HeadTable(sourceHeadTableModel2, new int[]{50, 100});
	private ResultHeadTableModel resultHeadTableModel = new ResultHeadTableModel();
	private HeadTable resultHeadTable = new HeadTable(resultHeadTableModel, new int[]{60, 60, 160});
	private JButton addBtn = new JButton(">>");
	private JButton delBtn = new JButton("<<");

	private OutputConfig outputConfig = null;
	private LoadConfig loadConfig = null;
	
	public OutputPanel() {
		initComponents();
		initEvents();
	}
	
	private void initComponents() {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		g.weighty = 1;
		g.weightx = 1;
		g.insets = new Insets(3, 3, 3, 3);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout(5, 5));
		p1.add(new JLabel(Resource.getInstance().getResourceString(Resource.KEY_LABEL_KEYCOLUMN1)), BorderLayout.NORTH);
		p1.add(new JScrollPane(sourceHeadTable1));
		this.add(p1, g);
		
		g.gridx = 1;
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout(5, 5));
		p2.add(new JLabel(Resource.getInstance().getResourceString(Resource.KEY_LABEL_KEYCOLUMN2)), BorderLayout.NORTH);
		p2.add(new JScrollPane(sourceHeadTable2));
		this.add(p2, g);
		
		g.gridx = 2;
		g.fill = GridBagConstraints.NONE;
		g.weightx = 0;
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(addBtn);
		box.add(Box.createVerticalStrut(10));
		box.add(delBtn);
		box.add(Box.createVerticalGlue());
		this.add(box, g);
		
		g.gridx = 3;
		g.fill = GridBagConstraints.BOTH;
		g.weightx = 2;
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout(5, 5));
		p3.add(new JLabel(Resource.getInstance().getResourceString(Resource.KEY_LABEL_RESULTCOLUMN)), BorderLayout.NORTH);
		p3.add(new JScrollPane(resultHeadTable));
		this.add(p3, g);
		
		updateAddBtnStatus();
		updateDelBtnStatus();
	}
	
	private void initEvents() {
		sourceHeadTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateAddBtnStatus();
			}
		});
		sourceHeadTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateAddBtnStatus();
			}
		});
		resultHeadTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateDelBtnStatus();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAddBtn();
			}
		});
		
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDelBtn();
			}
		});
	}
	
	private void updateAddBtnStatus() {
		int c1 = sourceHeadTable1.getSelectedRowCount();
		int c2 = sourceHeadTable2.getSelectedRowCount();
		if(c1 > 0 || c2 > 0) {
			addBtn.setEnabled(true);
		} else {
			addBtn.setEnabled(false);
		}
	}
	
	private void updateDelBtnStatus() {
		if(resultHeadTable.getSelectedRowCount() > 0) {
			delBtn.setEnabled(true);
		} else {
			delBtn.setEnabled(false);
		}
	}
	
	private void onAddBtn() {
		ResultHead head = new ResultHead();

		if(sourceHeadTable1.getSelectedRowCount() > 0) {
			int index = sourceHeadTable1.getSelectedRow();
			SourceHead sh = sourceHeadTableModel1.getSourceHead(index);
			head.setColumn1(sh.getColumn());
			head.setText(sh.getText());
			
			sourceHeadTableModel1.removeSourceHead(index);
		}
		
		if(sourceHeadTable2.getSelectedRowCount() > 0) {
			int index = sourceHeadTable2.getSelectedRow();
			SourceHead sh = sourceHeadTableModel2.getSourceHead(index);
			head.setColumn2(sh.getColumn());
			if(head.getText() == null)
				head.setText(sh.getText());
			
			sourceHeadTableModel2.removeSourceHead(index);
		}

		resultHeadTableModel.addResultHead(head);
		
		sourceHeadTableModel1.redraw();
		sourceHeadTableModel2.redraw();
		resultHeadTableModel.redraw();
	}
	
	private void onDelBtn() {
		if(resultHeadTable.getSelectedRowCount() <= 0)
			return;
		
		int index = resultHeadTable.getSelectedRow();
		ResultHead head = resultHeadTableModel.removeResultHead(index);
		
		if(head.getColumn1() != null) {
			int ti = StrUtil.stringToIndex(head.getColumn1());
			sourceHeadTableModel1.addSourceHead(new SourceHead(head.getColumn1(), loadConfig.cache1.getHeaderList().get(ti)));
		}
		
		if(head.getColumn2() != null) {
			int ti = StrUtil.stringToIndex(head.getColumn2());
			sourceHeadTableModel2.addSourceHead(new SourceHead(head.getColumn2(), loadConfig.cache2.getHeaderList().get(ti)));
		}
		
		sourceHeadTableModel1.redraw();
		sourceHeadTableModel2.redraw();
		resultHeadTableModel.redraw();
	}
	
	public void prepare(LoadConfig config) {
		loadConfig = config;
		
		if(outputConfig == null) {
			outputConfig = OutputConfig.parse(Profile.getInstance().getOutputHeads());
		}
		
		int index = 0;
		for(String ts : config.cache1.getHeaderList()) {
			String c1 = StrUtil.indexToString(index);
			if(!outputConfig.containKey1(c1)) {
				sourceHeadTableModel1.addSourceHead(new SourceHead(c1, ts));
			}
			index++;
		}
		sourceHeadTableModel1.redraw();
		
		index = 0;
		for(String ts : config.cache2.getHeaderList()) {
			String c2 = StrUtil.indexToString(index);
			if(!outputConfig.containKey2(c2)) {
				sourceHeadTableModel2.addSourceHead(new SourceHead(c2, ts));
			}
			index++;
		}
		sourceHeadTableModel2.redraw();
		
		for(ResultHead th : outputConfig.getResultHeadList()) {
			resultHeadTableModel.addResultHead(th);
		}
	}
	
	public boolean isDone() {
		if(resultHeadTableModel.size() <= 0) {
			GuiUtil.showErrorDlg(Resource.getInstance().getResourceString(Resource.KEY_ERROR_EMPTYOUTPUTCOLUMN));
			return false;
		}
		
		this.outputConfig = new OutputConfig();
		for(int i=0; i<resultHeadTableModel.size(); i++) {
			outputConfig.addResultHead(resultHeadTableModel.getResultHead(i));
		}
		
		try {
			ExcelComparator ec = new ExcelComparator();
			ec.compare(loadConfig, outputConfig);
		} catch (Exception e) {
			log.debug("compare files failed", e);
			try {
				loadConfig.cache1 = DataCache.load(loadConfig.excelFile1, loadConfig.keyColumn1);
				loadConfig.cache2 = DataCache.load(loadConfig.excelFile2, loadConfig.keyColumn2);
			} catch (Exception e2) {
				GuiUtil.showErrorDlg(e2.getMessage());
			}
			GuiUtil.showErrorDlg(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public void saveConfig() {
		Profile.getInstance().setOutputHeads(outputConfig.toString());
	}
}
