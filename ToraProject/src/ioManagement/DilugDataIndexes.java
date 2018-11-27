package ioManagement;

public class DilugDataIndexes {
	int lineNum;
	int charPOS;
	int wordPOS;

	DilugDataIndexes(int l, int c, int w) {
		lineNum = l;
		charPOS = c;
		wordPOS = w;
	}
	
	public int getLineNum() {
		return lineNum;
	}
	public int getCharPOS() {
		return charPOS;
	}
	public int getWordPOS() {
		return wordPOS; 
	}
}
