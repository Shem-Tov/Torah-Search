package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import frame.Frame;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.Output;
import stringFormatting.StringAlignUtils;

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
		String searchConvert;
		boolean bool_wholeWords;
		boolean bool_sofiot;
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 3) {
				throw new IllegalArgumentException("Missing Arguments in ToraSearch.searchWords");
			}
			searchSTR = (String) args[0];
			bool_wholeWords = (args[1] != null) ? (Boolean) args[1] : true;
			bool_sofiot = (args[2] != null) ? (Boolean) args[2] : true;
			searchConvert = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR) : searchSTR;
		} catch (ClassCastException e) {
			Output.printText("casting exception...", 1);
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}
		int countPsukim = 0;
		int countLines = 0;
		int count = 0;

		BufferedReader bReader = ToraApp.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
		if (bReader == null) {
			return;
		}
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = bReader;
			outputStream = new StringWriter();
			// outputStream2 = new FileWriter("/myText.txt", false);
			inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			String line;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			str = "\u202B" + ((bool_wholeWords) ? "חיפוש מילים שלמות" : "חיפוש צירופי אותיות");
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				Output.printText(Frame.HtmlHRLine);
			}
			// System.out.println(formatter.locale());
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
			}
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}
				if (bool_wholeWords) {
					if (searchSTR.contains(" ")) {
						if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
							frame.Frame.clearText();
						}
						Output.printText("לא ניתן לעשות חיפוש לפי מילים ליותר ממילה אחת, תעשו חיפוש לפי אותיות", 1);
						if (inputStream != null) {
							inputStream.close();
						}
						if (outputStream != null) {
							outputStream.close();
						}
						return;
					}
					String[] splitStr;
					if (!bool_sofiot) {
						splitStr = HebrewLetters.switchSofiotStr(line).trim().split("\\s+");
					} else {
						splitStr = line.trim().split("\\s+");
					}
					for (String s : splitStr) {
						// Do your stuff here
						if (s.equals(searchConvert)) {
							count++;
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
							}
							// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
							// fill results array
							results.add(Output.printPasukInfo(countLines, searchSTR, line, frame.Frame.markupStyleHTML,
									bool_sofiot, bool_wholeWords));
						}
					}
				} else {
					if (((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line) : line).contains(searchConvert)) {
						int countMatch = StringUtils.countMatches(line, searchSTR);
						count = count + countMatch;
						if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
							frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
						}
						countPsukim++;
						// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
						// fill results array
						results.add(Output.printPasukInfo(countLines, searchSTR, line, frame.Frame.markupStyleHTML,
								bool_sofiot, bool_wholeWords));
					}
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					break;
				}
			}
			String Title = ((bool_wholeWords) ? "חיפוש מילים שלמות בתורה" : "חיפוש צירוף אותיות בתורה");
			String fileName = searchSTR;
			String sheet = ((bool_wholeWords) ? "מילים" : "אותיות");
			if (count > 0) {
				ExcelFunctions.writeXLS("", fileName, sheet, (bool_sofiot) ? 0 : 1, Title, results, true);
			}
		} catch (Exception e) {
			Output.printText("Error with loading Lines.txt", 1);
			e.printStackTrace();
		} finally {
			Output.printText("");
			Output.printText(
					Output.markText(
							"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים"
									+ ((bool_wholeWords) ? "."
											: (" ב" + "\u00A0" + String.valueOf(countPsukim) + " פסוקים.")),
							frame.Frame.footerStyleHTML));
			Output.printText("");
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.Frame.footerStyleHTML));
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
