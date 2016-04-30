package net.ojava.openkit.excelcomparer.gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import net.ojava.openkit.excelcomparer.excel.ResultHead;
import net.ojava.openkit.excelcomparer.res.Resource;

public class ResultHeadTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 4286414737516667690L;
	private ArrayList<ResultHead> headList = new ArrayList<ResultHead>();

	public void addResultHead(ResultHead head) {
		headList.add(head);
	}
	
	public void addAllResultHead(Collection<ResultHead> heads) {
		for(ResultHead tsh : heads) {
			addResultHead(tsh);
		}
	}

	public ResultHead removeResultHead(int index) {
		return headList.remove(index);
	}
	
	public ResultHead getResultHead(int index) {
		return headList.get(index);
	}
	
	public int size() {
		return headList.size();
	}
	
	public void redraw() {
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int colIndex) {
		switch (colIndex) {
		case 0:
			return Resource.getInstance().getResourceString(
					Resource.KEY_LABEL_INDEX1);
		case 1:
			return Resource.getInstance().getResourceString(
					Resource.KEY_LABEL_INDEX2);
		case 2:
			return Resource.getInstance().getResourceString(
					Resource.KEY_LABEL_TEXT);
		}
		return "";
	}

	@Override
	public int getRowCount() {
		return headList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ResultHead head = headList.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return head.getColumn1();
		case 1:
			return head.getColumn2();
		case 2:
			return head.getText();
		}
		return "";
	}
}
