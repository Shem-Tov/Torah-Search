package ioManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
		}
		return file;
	}

	public static BufferedReader getBufferedReader(String fileName1, String fileName2) {
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(
					new InputStreamReader(ToraApp.class.getClassLoader().getResourceAsStream(fileName1),"UTF8"));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
			return bReader;
			// safe to ignore
		}
		return bReader;
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
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
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
