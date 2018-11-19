package ioManagement;

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
}

