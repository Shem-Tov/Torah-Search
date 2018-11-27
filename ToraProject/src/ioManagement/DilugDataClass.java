package ioManagement;

import java.util.ArrayList;

enum DilugMode {Regular,HPasuk,TPasuk,HWord,TWord}

public class DilugDataClass {
	
	ArrayList<DilugDataIndexes> dilugIndexes = new ArrayList<DilugDataIndexes>();
	String str;
	int dilug;
	DilugMode mode;
	
	DilugDataClass (String s, int d, DilugMode m,int lineN, int charP, int wordP) {
		str = s;
		dilug = d;
		mode = m;
		add(lineN,charP, wordP);
	}
	
	public void add (int lineN, int charP, int wordP){
		dilugIndexes.add(new DilugDataIndexes(lineN,charP,wordP));
	}
	
	public DilugDataIndexes getIndexes(int index) {
		return dilugIndexes.get(index);
	}
	
	public String getSearchWord() {
		return str;
	}
	
	public int getSearchDilug() {
		return dilug;
	}
	
	public DilugMode getSearchMode() {
		return mode;
	}
}
