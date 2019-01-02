package ioManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.UnsupportedEncodingException;

import torahApp.ToraApp;

public class ManageIO {

	public static File checkFile(String fileName1, String fileName2) {
		File file = null;
		try {
			file = new File(ClassLoader.getSystemResource(fileName1).toURI());
		} catch (Exception e) {
			// safe to ignore
		}
		if ((file == null) || (!file.exists())) {
			try {
				file = new File(fileName2);
			} catch (Exception e) {
				// safe to ignore
			}
			if ((file == null) || (!file.exists())) {
				// throw new IOException("Could not find file for TorahLetters");
				if (ToraApp.isGui()) {
					frame.Frame.clearTextPane();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
		}
		return file;
	}

	public enum fileMode {Line,NoTevot,LastSearch,Different};
	
	public static BufferedReader getBufferedReader(fileMode mode, Boolean allowLastSearch) {
		BufferedReader bReader = null;
		String fileName1="", fileName2="";
		switch (mode) {
		case LastSearch:
			if (allowLastSearch) {
				return LastSearchClass.getStoredBufferedReader();
			}
			//no break;
		case Line:
			fileName1 = ToraApp.ToraLineFile;
			fileName2 = ToraApp.subTorahLineFile;
			break;
		case NoTevot:
			fileName1 = ToraApp.ToraLetterFile;
			fileName2 = ToraApp.subTorahLetterFile;
			break;
		case Different:
			fileName2 = ToraApp.differentSearchFile;
			break;
		}

		try {
			if (fileName1.equals("")) {
				throw new Exception();
			}
			bReader = new BufferedReader(
					new InputStreamReader(ToraApp.class.getClassLoader().getResourceAsStream(fileName1),"UTF8"));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
			} catch (NullPointerException | FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (ToraApp.isGui()) {
					frame.Frame.clearTextPane();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
			return bReader;
			// safe to ignore
		}
		return bReader;
	}

	public static int countLinesOfFile(fileMode mode) {
		BufferedReader inputStream = ManageIO.getBufferedReader(mode,false);
		int countLines=0; 
		try {
				while ((inputStream.readLine()) != null) {
					countLines++;
				}
		} catch (IOException e) {
			e.printStackTrace();
	    } finally {
	    	try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return countLines;
	}
	
	public static PushbackReader getPushbackReader(String fileName1, String fileName2) {
		PushbackReader bReader = null;
		try {
			bReader = new PushbackReader(
					new InputStreamReader(ToraApp.class.getClassLoader().getResourceAsStream(fileName1)));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new PushbackReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (ToraApp.isGui()) {
					frame.Frame.clearTextPane();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
			return bReader;
			// safe to ignore
		}
		return bReader;
	}
}
