package ToraApp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class ToraSearch {
	public static void searchWords(Object[] args) throws IOException {
		ArrayList<String[]> results=new ArrayList<String[]>();
		//String[][] results=null;
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		String searchSTR;
		boolean bool_wholeWords;
		// FileWriter outputStream2 = null;
		try {
			searchSTR = (String) args[0];
			bool_wholeWords = (Boolean) args[1];
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			return;
		}
		int countPsukim = 0;
		int countLines = 0;
		int count = 0;
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = new BufferedReader(new FileReader("./src/Lines_2.txt"));
			outputStream = new StringWriter();
			// outputStream2 = new FileWriter("./src/myText.txt", false);
			inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			String line;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
			Output.printText(str);
			if (bool_wholeWords) {
				Output.printText("\u202B" + "חיפוש מילים שלמות");
			}	else
			{
				Output.printText("\u202B" + "חיפוש צירופי אותיות");
			}
			Output.printText(String.format("%1$-" + (str.length() - 1) + "s", "").replace(' ', '-'));
			//System.out.println(formatter.locale());
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if (bool_wholeWords) {
					String[] splitStr = line.trim().split("\\s+");
					for (String s : splitStr) {
						// Do your stuff here
						if (s.equals(searchSTR)) {
							count++;
							ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
							String tempStr1 = "\u202B" + 
									"\"" + searchSTR + "\" " + "נמצא ב" + pBookInstance.getBookName() + " "
									+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters() + "  =  ";
							Output.printText(tempStr1 + line);
							results.add(new String[] {searchSTR,pBookInstance.getBookName(),pBookInstance.getPerekLetters(),pBookInstance.getPasukLetters(),line});
						}
					}
				} else {
					if (line.contains(searchSTR)) {
						int countMatch = StringUtils.countMatches(line, searchSTR);
						count=count+countMatch;
						countPsukim++;
						ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
						String tempStr1 = "\u202B" + 
								"\"" + searchSTR + "\" " + "נמצא ב" + pBookInstance.getBookName() + " "
								+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters() + "  =  ";
						Output.printText(tempStr1 + line);
						results.add(new String[] {searchSTR,pBookInstance.getBookName(),pBookInstance.getPerekLetters(),pBookInstance.getPasukLetters(),line});
					}
				}
			}
			String Title = ((bool_wholeWords) ? "חיפוש מילים שלמות בתורה":"חיפוש צירוף אותיות בתורה");
			String fileName = searchSTR + "_" + ((bool_wholeWords) ? "מילים":"אותיות");
			ExcelFunctions.writeXLS(fileName,Title, searchSTR, results);
			Output.printText("\u202B" + " נמצא " + "\"" + searchSTR + "\" " + count + " פעמים ב" + countPsukim + " פסוקים.");
		} catch(Exception e) {
			Output.printText("Found Error at Line: " + countLines );
		} finally {
			Output.printText("\u202B" + "סיים חיפוש");
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}

			/*
			 * if (outputStream2 != null) { outputStream2.close(); }
			 */
		}
	}
}
