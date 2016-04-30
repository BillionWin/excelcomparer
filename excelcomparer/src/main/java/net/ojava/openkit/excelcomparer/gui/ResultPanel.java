package net.ojava.openkit.excelcomparer.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.ojava.openkit.excelcomparer.excel.LoadConfig;
import net.ojava.openkit.excelcomparer.res.Resource;

public class ResultPanel extends JPanel {
	private static final long serialVersionUID = 2943712223391297290L;

	private JTextField sourceField1 = new JTextField(100);
	private JTextField sourceField2 = new JTextField(100);
	private JTextField outputField = new JTextField(100);
	
	public ResultPanel() {
		initComponents();
		initEvents();
	}
	
	private void initComponents() {
		sourceField1.setEditable(false);
		sourceField2.setEditable(false);
		outputField.setEditable(false);
		
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		this.add(p);
		
		p.add(new JLabel(Resource.getInstance().getResourceString(Resource.KEY_TIP_COMPAREOK)), BorderLayout.NORTH);

		JPanel filePanel = new JPanel();
		filePanel.setBorder(BorderFactory.createTitledBorder(
				new EtchedBorder(),
				Resource.getInstance().getResourceString(
						Resource.KEY_LABEL_SOURCETARGETFILE)));
		filePanel.setLayout(new BorderLayout(5, 5));
		p.add(filePanel);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3, 1, 5, 5));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_SOURCEFILE1)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_SOURCEFILE2)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_OUTPUTFILE)));
		filePanel.add(labelPanel, BorderLayout.WEST);

		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(3, 1, 5, 5));
		fieldPanel.add(sourceField1);
		fieldPanel.add(sourceField2);
		fieldPanel.add(outputField);
		filePanel.add(fieldPanel);
	}
	
	private void initEvents() {
	}
	
	public void prepare(LoadConfig loadConfig) {
		sourceField1.setText(loadConfig.excelFile1.getAbsolutePath());
		sourceField2.setText(loadConfig.excelFile2.getAbsolutePath());
		outputField.setText(loadConfig.outputFile.getAbsolutePath());
	}
}
