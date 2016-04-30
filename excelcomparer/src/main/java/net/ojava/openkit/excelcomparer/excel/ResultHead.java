package net.ojava.openkit.excelcomparer.excel;

public class ResultHead {
	private String column1;
	private String column2;
	private String text;
	
	public ResultHead() {
		
	}
	
	public ResultHead(String column1, String column2, String text) {
		this.setColumn1(column1);
		this.setColumn2(column2);
		this.setText(text);
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
