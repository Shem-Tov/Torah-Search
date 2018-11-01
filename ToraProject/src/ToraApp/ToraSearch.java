package ToraApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import StringFormatting.StringAlignUtils;

public class ToraSearch {
	private static ToraSearch instance;
	
	public static ToraSearch getInstance() {
		if (instance == null) {
			instance = new ToraSearch();
		}
		return instance;
	}
	
	public void searchWords(Object[] args) throws IOException {
		ArrayList<String[][]> results = new ArrayList<String[][]>();
		// String[][] results=null;
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		String searchSTR;
		boolean bool_wholeWords;
		// FileWriter outputStream2 = null;
		try {
			searchSTR = (String) args[0];
			bool_wholeWords = (Boolean) args[1];
		} catch (ClassCastException e) {
			Output.printText("casting exception...",1);
			return;
		}
		int countPsukim = 0;
		int countLines = 0;
		int count = 0;
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = new BufferedReader(new FileReader(ToraApp.ToraLineFile));
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
			str = "\u202B" + ((bool_wholeWords) ? "חיפוש מילים שלמות" : "חיפוש צירופי אותיות");
			Output.printText(str);
			Output.printText(StringAlignUtils.padRight("", str.length()).replace(' ', '-'));
			// System.out.println(formatter.locale());
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if (countLines % 25 == 0) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}
				if (bool_wholeWords) {
					if (searchSTR.contains(" ")) {
						frame.frame.clearText();
						Output.printText("לא ניתן לעשות חיפוש לפי מילים ליותר ממילה אחת, תעשו חיפוש לפי אותיות",1);
						if (inputStream != null) {
							inputStream.close();
						}
						if (outputStream != null) {
							outputStream.close();
						}
						return;
					}
					String[] splitStr = line.trim().split("\\s+");
					for (String s : splitStr) {
						// Do your stuff here
						if (s.equals(searchSTR)) {
							count++;
							// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to fill results array
							results.add(Output.printPasukInfo(countLines, searchSTR, line,frame.frame.markupStyleHTML));
						}
					}
				} else {
					if (line.contains(searchSTR)) {
						int countMatch = StringUtils.countMatches(line, searchSTR);
						count = count + countMatch;
						countPsukim++;
						// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to fill results array
						results.add(Output.printPasukInfo(countLines, searchSTR, line,frame.frame.markupStyleHTML));
					}
				}
			}
			String Title = ((bool_wholeWords) ? "חיפוש מילים שלמות בתורה" : "חיפוש צירוף אותיות בתורה");
			String fileName = searchSTR;
			String sheet = ((bool_wholeWords) ? "מילים" : "אותיות");
			if (count > 0) {
				ExcelFunctions.writeXLS(fileName,sheet,0, Title, results);
			}
			Output.printText("");
			Output.printText("\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים"
					+ ((bool_wholeWords) ? "." : (" ב" + "\u00A0" + String.valueOf(countPsukim) + " פסוקים.")));
		} catch (Exception e) {
			Output.printText("Found Error at Line: " + countLines,1);
		} finally {
			Output.printText("");
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
