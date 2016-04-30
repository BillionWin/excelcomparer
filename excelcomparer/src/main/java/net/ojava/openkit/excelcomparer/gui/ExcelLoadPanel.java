package net.ojava.openkit.excelcomparer.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.ojava.openkit.excelcomparer.excel.DataCache;
import net.ojava.openkit.excelcomparer.excel.LoadConfig;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.Profile;
import net.ojava.openkit.excelcomparer.util.StrUtil;

public class ExcelLoadPanel extends JPanel {
	private static final long serialVersionUID = -1526535795070107283L;

	private static String[] COLUMN_OPTIONS;
	static {
		int optionCount = 30;
		COLUMN_OPTIONS = new String[optionCount];
		for (int i = 0; i < optionCount; i++) {
			COLUMN_OPTIONS[i] = String.valueOf((char) ('A' + i));
		}
	}

	private static String[] SIMILARITY_OPTIONS = new String[] { "100", "95",
			"90", "85", "80", "75", "70", "65", "60" };

	private JTextField sourceField1 = new JTextField(70);
	private JButton sourceBrowseBtn1 = new JButton("...");
	private JTextField sourceField2 = new JTextField(70);
	private JButton sourceBrowseBtn2 = new JButton("...");
	private JTextField outputField = new JTextField(70);
	private JButton outputBrowseBtn = new JButton("...");

	private JComboBox<String> keyColumnCombo1 = new JComboBox<String>(
			COLUMN_OPTIONS);
	private JComboBox<String> keyColumnCombo2 = new JComboBox<String>(
			COLUMN_OPTIONS);
	private JComboBox<String> keySimilarityCombo = new JComboBox<String>(
			SIMILARITY_OPTIONS);
	private JComboBox<String> compareColumnCombo1 = new JComboBox<String>(
			COLUMN_OPTIONS);
	private JComboBox<String> compareColumnCombo2 = new JComboBox<String>(
			COLUMN_OPTIONS);
	private JComboBox<String> compareSimilarityCombo = new JComboBox<String>(
			SIMILARITY_OPTIONS);

	private LoadConfig loadConfig = null;

	public ExcelLoadPanel() {
		initComponents();
		initEvents();
	}

	private void initComponents() {
		keyColumnCombo1.setEditable(true);
		keyColumnCombo2.setEditable(true);
		keySimilarityCombo.setEditable(true);
		keySimilarityCombo.setEnabled(false);
		compareColumnCombo1.setEditable(true);
		compareColumnCombo2.setEditable(true);
		compareSimilarityCombo.setEditable(true);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout());

		Box box = Box.createVerticalBox();
		this.add(box);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 5, 5));
		p.add(createKeyPanel());
		p.add(createComparePanel());
		box.add(p);

		box.add(createFilePanel());

		box.add(Box.createVerticalGlue());
	}

	private JPanel createFilePanel() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		JPanel filePanel = new JPanel();
		filePanel.setBorder(BorderFactory.createTitledBorder(
				new EtchedBorder(),
				Resource.getInstance().getResourceString(
						Resource.KEY_LABEL_SOURCETARGETFILE)));
		filePanel.setLayout(new BorderLayout(5, 5));
		p.add(filePanel, BorderLayout.NORTH);

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

		JPanel browsePanel = new JPanel();
		browsePanel.setLayout(new GridLayout(3, 1, 5, 5));
		browsePanel.add(sourceBrowseBtn1);
		browsePanel.add(sourceBrowseBtn2);
		browsePanel.add(outputBrowseBtn);
		filePanel.add(browsePanel, BorderLayout.EAST);

		return p;
	}

	private JPanel createKeyPanel() {
		JPanel keyPanel = new JPanel();
		keyPanel.setBorder(BorderFactory.createTitledBorder(
				new EtchedBorder(),
				Resource.getInstance().getResourceString(
						Resource.KEY_LABEL_KEYCOLUMN)));
		keyPanel.setLayout(new BorderLayout(5, 5));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3, 1, 5, 5));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_KEYCOLUMN1)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_KEYCOLUMN2)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_KEYSIMILARITY)));
		keyPanel.add(labelPanel, BorderLayout.WEST);

		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(3, 1, 5, 5));
		comboPanel.add(keyColumnCombo1);
		comboPanel.add(keyColumnCombo2);
		comboPanel.add(keySimilarityCombo);
		keyPanel.add(comboPanel);

		return keyPanel;
	}

	private JPanel createComparePanel() {
		JPanel comparePanel = new JPanel();
		comparePanel.setBorder(BorderFactory.createTitledBorder(
				new EtchedBorder(),
				Resource.getInstance().getResourceString(
						Resource.KEY_LABEL_COMPARECOLUMN)));
		comparePanel.setLayout(new BorderLayout(5, 5));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3, 1, 5, 5));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_COMPARECOLUMN1)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_COMPARECOLUMN2)));
		labelPanel.add(new JLabel(Resource.getInstance().getResourceString(
				Resource.KEY_LABEL_COMPARESIMILARITY)));
		comparePanel.add(labelPanel, BorderLayout.WEST);

		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(3, 1, 5, 5));
		comboPanel.add(compareColumnCombo1);
		comboPanel.add(compareColumnCombo2);
		comboPanel.add(compareSimilarityCombo);
		comparePanel.add(comboPanel);

		return comparePanel;
	}

	private void initEvents() {
		sourceBrowseBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File parent = Profile.getInstance().getLastSourceFilePath1();
				if (parent != null)
					parent = parent.getParentFile();
				File file = GuiUtil.selectInputFile(Resource.getInstance()
						.getResourceString(Resource.KEY_LABEL_EXCELFILE),
						parent, "xls", "xlsx");
				if (file != null) {
					sourceField1.setText(file.getAbsolutePath());
					Profile.getInstance().setLastSourceFilePath1(file);
				}
			}
		});
		sourceBrowseBtn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File parent = Profile.getInstance().getLastSourceFilePath2();
				if (parent != null)
					parent = parent.getParentFile();
				File file = GuiUtil.selectInputFile(Resource.getInstance()
						.getResourceString(Resource.KEY_LABEL_EXCELFILE),
						parent, "xls", "xlsx");
				if (file != null) {
					sourceField2.setText(file.getAbsolutePath());
					Profile.getInstance().setLastSourceFilePath2(file);
				}
			}
		});
		outputBrowseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File outputFile = GuiUtil.selectOutputFile(
						Resource.getInstance().getResourceString(
								Resource.KEY_LABEL_EXCELFILE),
						Profile.getInstance().getLastOutputFilePath(),
						Resource.getInstance().getResourceString(
								Resource.KEY_INFO_DEFAULTOUTPUTFILENAME));

				if (outputFile != null) {
					outputField.setText(outputFile.getAbsolutePath());
					Profile.getInstance().setLastOutputFilePath(outputFile);
				}
			}
		});
	}

	public void prepare() {
		if (loadConfig == null) {
			loadConfig = new LoadConfig();
			loadConfig.keyColumn1 = Profile.getInstance().getKeyColumn1();
			loadConfig.keyColumn2 = Profile.getInstance().getKeyColumn2();
			loadConfig.keySimilarity = 100;
			// loadConfig.keySimilarity =
			// Profile.getInstance().getKeySimilarity();
			loadConfig.compareColumn1 = Profile.getInstance()
					.getCompareColumn1();
			loadConfig.compareColumn2 = Profile.getInstance()
					.getCompareColumn2();
			loadConfig.compareSimilarity = Profile.getInstance()
					.getCompareSimilarity();
			loadConfig.excelFile1 = Profile.getInstance()
					.getLastSourceFilePath1();
			loadConfig.excelFile2 = Profile.getInstance()
					.getLastSourceFilePath2();
			loadConfig.outputFile = Profile.getInstance()
					.getLastOutputFilePath();

			keyColumnCombo1.setSelectedItem(StrUtil
					.indexToString(loadConfig.keyColumn1));
			keyColumnCombo2.setSelectedItem(StrUtil
					.indexToString(loadConfig.keyColumn2));
			keySimilarityCombo.setSelectedItem(String
					.valueOf(loadConfig.keySimilarity));

			compareColumnCombo1.setSelectedItem(StrUtil
					.indexToString(loadConfig.compareColumn1));
			compareColumnCombo2.setSelectedItem(StrUtil
					.indexToString(loadConfig.compareColumn2));
			compareSimilarityCombo.setSelectedItem(String
					.valueOf(loadConfig.compareSimilarity));

			sourceField1.setText(loadConfig.excelFile1 == null ? ""
					: loadConfig.excelFile1.getAbsolutePath());
			sourceField2.setText(loadConfig.excelFile2 == null ? ""
					: loadConfig.excelFile2.getAbsolutePath());
			outputField.setText(loadConfig.outputFile == null ? ""
					: loadConfig.outputFile.getAbsolutePath());
		}
	}

	public boolean isDone() {
		if (loadConfig == null) {
			loadConfig = new LoadConfig();
		}

		try {
			loadConfig.keyColumn1 = checkColumnCombo(keyColumnCombo1, 0,
					Profile.getInstance().getMaxColumnCount() - 1,
					Resource.KEY_LABEL_KEYCOLUMN1);
			loadConfig.keyColumn2 = checkColumnCombo(keyColumnCombo2, 0,
					Profile.getInstance().getMaxColumnCount() - 1,
					Resource.KEY_LABEL_KEYCOLUMN2);
			loadConfig.keySimilarity = checkSimilarityCombo(keySimilarityCombo,
					Resource.KEY_LABEL_KEYSIMILARITY);
			loadConfig.compareColumn1 = checkColumnCombo(compareColumnCombo1,
					0, Profile.getInstance().getMaxColumnCount() - 1,
					Resource.KEY_LABEL_COMPARECOLUMN1);
			loadConfig.compareColumn2 = checkColumnCombo(compareColumnCombo2,
					0, Profile.getInstance().getMaxColumnCount() - 1,
					Resource.KEY_LABEL_COMPARECOLUMN2);
			loadConfig.compareSimilarity = checkSimilarityCombo(
					compareSimilarityCombo,
					Resource.KEY_LABEL_COMPARESIMILARITY);

			File sf1 = new File(sourceField1.getText());
			File sf2 = new File(sourceField2.getText());
			File of = new File(outputField.getText());
			if (!sf1.exists()) {
				throw new Exception(Resource.getInstance().getResourceString(
						Resource.KEY_ERROR_EMPTYEXCELFILE1));
			}

			if (!sf2.exists()) {
				throw new Exception(Resource.getInstance().getResourceString(
						Resource.KEY_ERROR_EMPTYEXCELFILE2));
			}

			if (of.getParentFile() == null || !of.getParentFile().exists()) {
				throw new Exception(Resource.getInstance().getResourceString(
						Resource.KEY_ERROR_EMPTYOUTPUTFILE));
			}

			loadConfig.cache1 = DataCache.load(sf1, loadConfig.keyColumn1);
			loadConfig.cache2 = DataCache.load(sf2, loadConfig.keyColumn2);

			return true;
		} catch (Exception e) {
			GuiUtil.showErrorDlg(e.getMessage());
		}

		return false;
	}

	private int checkColumnCombo(JComboBox<String> comb, int start, int end,
			String resId) throws Exception {
		String errorTip = MessageFormat.format(Resource.getInstance()
				.getResourceString(Resource.KEY_ERROR_INVALIDCOLINDEX),
				Resource.getInstance().getResourceString(resId));

		String str = (String) comb.getSelectedItem();
		if (str == null)
			throw new Exception(errorTip);

		int index = StrUtil.stringToIndex(str);
		if (index < start || index > end)
			throw new Exception(errorTip);

		return index;
	}

	private int checkSimilarityCombo(JComboBox<String> comb, String resId)
			throws Exception {
		String errorTip = MessageFormat.format(Resource.getInstance()
				.getResourceString(Resource.KEY_ERROR_INVALIDSIMILARITY),
				Resource.getInstance().getResourceString(resId));

		String str = (String) comb.getSelectedItem();
		if (str == null)
			throw new Exception(errorTip);

		try {
			int percent = Integer.parseInt(str);
			if (percent < 10 || percent > 100)
				throw new Exception(errorTip);

			return percent;
		} catch (Exception e) {
		}

		throw new Exception(errorTip);
	}

	public LoadConfig getLoadConfig() {
		return loadConfig;
	}
	
	public void saveConfig() {
		Profile.getInstance().setKeyColumn1(loadConfig.keyColumn1);
		Profile.getInstance().setKeyColumn2(loadConfig.keyColumn2);
		Profile.getInstance().setKeySimilarity(loadConfig.keySimilarity);
		Profile.getInstance().setCompareColumn1(loadConfig.compareColumn1);
		Profile.getInstance().setCompareColumn2(loadConfig.compareColumn2);
		Profile.getInstance().setCompareSimilarity(loadConfig.compareSimilarity);
		Profile.getInstance().setLastSourceFilePath1(loadConfig.excelFile1);
		Profile.getInstance().setLastSourceFilePath2(loadConfig.excelFile2);
		Profile.getInstance().setLastOutputFilePath(loadConfig.outputFile);
	}
}
