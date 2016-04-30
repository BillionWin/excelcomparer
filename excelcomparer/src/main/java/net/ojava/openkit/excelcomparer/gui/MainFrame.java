package net.ojava.openkit.excelcomparer.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.ojava.openkit.excelcomparer.App;
import net.ojava.openkit.excelcomparer.res.Resource;
import net.ojava.openkit.excelcomparer.util.Profile;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String CARD_LOAD = "cardload";
	private static final String CARD_OUTPUT = "cardoutput";
	private static final String CARD_RESULT = "cardresult";
	
	private JPanel wizardPanel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	private ExcelLoadPanel loadPanel = new ExcelLoadPanel();
	private OutputPanel outputPanel = new OutputPanel();
	private ResultPanel resultPanel = new ResultPanel();
	
	private JButton prevBtn = new JButton();
	private JButton nextBtn = new JButton();
	private JButton exitBtn = new JButton();
	
	enum WIZARD_STEPS{
		STEP_LOAD, STEP_COMPARE, STEP_DONE;
	};
	
	private WIZARD_STEPS curStep = WIZARD_STEPS.STEP_LOAD;
	
	private class WindowProcessor extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			doExit();
		}
	}
	
	public MainFrame() {
		super(Resource.getInstance().getResourceString(Resource.KEY_APPNAME));
		
		initData();
		initComponents();
		initEvents();
	}
	
	private void initData() {
		
	}
	
	private void initComponents() {
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout(5, 5));
		this.setContentPane(cp);
		
		cp.add(wizardPanel);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		prevBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_PREVIOUS));
		nextBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_NEXT));
		exitBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_EXIT));
		btnPanel.add(prevBtn);
		btnPanel.add(nextBtn);
		btnPanel.add(exitBtn);
		
		
		wizardPanel.setLayout(cardLayout);
		wizardPanel.add(loadPanel, CARD_LOAD);
		wizardPanel.add(outputPanel, CARD_OUTPUT);
		wizardPanel.add(resultPanel, CARD_RESULT);
		
		goStepLoad();
		
//		this.pack();
		this.setSize(800, 500);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void initEvents() {
		this.addWindowListener(new WindowProcessor());
		
		prevBtn.addActionListener(this);
		nextBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}
	
	private void updateButtonStatus() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch(curStep) {
				case STEP_LOAD:
					prevBtn.setEnabled(false);
					nextBtn.setEnabled(true);
					nextBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_NEXT));
					break;
				case STEP_COMPARE:
					prevBtn.setEnabled(true);
					nextBtn.setEnabled(true);
					nextBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_COMPARE));
					break;
				case STEP_DONE:
					prevBtn.setEnabled(false);
					nextBtn.setEnabled(true);
					nextBtn.setText(Resource.getInstance().getResourceString(Resource.KEY_LABEL_RESTART));
					break;
				}
			}
		});
	}
	
	private void disableButtons() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				prevBtn.setEnabled(false);
				nextBtn.setEnabled(false);
			}
		});
	}
	
	private void nextStep() {
		switch(curStep) {
		case STEP_LOAD:
			goStepCompare();
			break;
		case STEP_COMPARE:
			goStepDone();
			break;
		case STEP_DONE:
			goStepLoad();
			break;
		}
	}
	
	private void prevStep() {
		switch(curStep) {
		case STEP_LOAD:
			break;
		case STEP_COMPARE:
			goStepLoad();
			break;
		case STEP_DONE:
			goStepCompare();
			break;
		}
	}
	
	private void goStepLoad() {
		curStep = WIZARD_STEPS.STEP_LOAD;
		updateButtonStatus();
		loadPanel.prepare();
		cardLayout.show(wizardPanel, CARD_LOAD);
	}
	
	private void goStepCompare() {
		disableButtons();
		new Thread() {
			public void run() {
				if(loadPanel.isDone()) {
					curStep = WIZARD_STEPS.STEP_COMPARE;
					outputPanel.prepare(loadPanel.getLoadConfig());
					cardLayout.show(wizardPanel, CARD_OUTPUT);
				}
				updateButtonStatus();
			}
		}.start();
	}
	
	private void goStepDone() {
		disableButtons();
		new Thread() {
			public void run() {
				if(outputPanel.isDone()) {
					curStep = WIZARD_STEPS.STEP_DONE;
					resultPanel.prepare(loadPanel.getLoadConfig());
					cardLayout.show(wizardPanel, CARD_RESULT);
					
					loadPanel.saveConfig();
					outputPanel.saveConfig();
					Profile.getInstance().save();
				}
				updateButtonStatus();
			}
		}.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == prevBtn) {
			prevStep();
		} else if(e.getSource() == nextBtn) {
			nextStep();
		} else if(e.getSource() == exitBtn) {
			doExit();
		}
	}
	
	
	private void doExit() {
		if(GuiUtil.showConfirmDlg(Resource.getInstance().getResourceString(Resource.KEY_TIP_EXITCONFIRM)))
			App.exitApp();
	}

}
