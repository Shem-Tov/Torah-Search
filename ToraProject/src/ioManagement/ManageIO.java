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

import torahApp.TorahApp;

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
				if (TorahApp.isGui()) {
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
			fileName1 = TorahApp.TorahLineFile;
			fileName2 = TorahApp.subTorahLineFile;
			break;
		case NoTevot:
			fileName1 = TorahApp.TorahLetterFile;
			fileName2 = TorahApp.subTorahLetterFile;
			break;
		case Different:
			fileName2 = TorahApp.differentSearchFile;
			break;
		}

		try {
			if (fileName1.equals("")) {
				throw new Exception();
			}
			
			/*
			  public static void load(File f) throws IOException {
				    FileInputStream fis = new FileInputStream(f);
				    FileChannel fc = fis.getChannel();

				    MappedByteBuffer mmb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				   
				    byte[] buffer = new byte[(int)fc.size()];
				    mmb.get(buffer);
				   
				    fis.close();

				    BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

				    for (String line = in.readLine(); line != null; line = in.readLine()) {
				      // do your processing here...
				    }

				    in.close();
				  }
			*/
			
			
			bReader = new BufferedReader(
					new InputStreamReader(TorahApp.class.getClassLoader().getResourceAsStream(fileName1),"UTF8"));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
			} catch (NullPointerException | FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (TorahApp.isGui()) {
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
					new InputStreamReader(TorahApp.class.getClassLoader().getResourceAsStream(fileName1)));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new PushbackReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (TorahApp.isGui()) {
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
