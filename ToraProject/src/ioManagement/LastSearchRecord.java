package ioManagement;

import java.io.Serializable;
import java.util.ArrayList;

public class LastSearchRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lineNum;
	private String lineText;
	private ArrayList<Integer[]> markIndexes;
	
	public LastSearchRecord(int num, String text, ArrayList<Integer[]> indexes) {
		// TODO Auto-generated constructor stub
		lineNum = num;
		lineText = text;
		markIndexes = indexes;
	}

	public int getLineNum() {
		return lineNum;
	}

	public String getLineText() {
		return lineText;
	}

	public ArrayList<Integer[]> getMarkIndexes() {
		return markIndexes;
	}
}
