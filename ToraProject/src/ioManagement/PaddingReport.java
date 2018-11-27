package ioManagement;

import java.util.ArrayList;

public class PaddingReport {
	String paddingLine;
	int startMark;
	int endMark;
	public PaddingReport(String pLine, int sMark, int eMark) {
		paddingLine=pLine;
		startMark=sMark;
		endMark=eMark;
	}
	public String getPaddingLine() {
		return paddingLine;
	}
	public int getStartMark() {
		return startMark;
	}
	public int getEndMark() {
		return endMark;
	}
	
	public ArrayList<Integer[]> getArrayListPadding(){
		ArrayList<Integer[]> aList = new ArrayList<Integer[]>();
		aList.add(new Integer[] {startMark,endMark});
		return aList;
	}
}

