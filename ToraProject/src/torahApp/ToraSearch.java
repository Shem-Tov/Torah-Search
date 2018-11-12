package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import frame.Frame;
import frame.Tree;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.ManageIO;
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
		BufferedReader inputStream2 = null;
		// StringWriter outputStream = null;
		String searchSTR;
		String searchConvert;
		int[] searchRange;
		boolean bool_wholeWords;
		boolean bool_sofiot;
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 3) {
				throw new IllegalArgumentException("Missing Arguments in ToraSearch.searchWords");
			}
			searchSTR = ((String) args[0]).trim();
			bool_wholeWords = (args[1] != null) ? (Boolean) args[1] : true;
			bool_sofiot = (args[2] != null) ? (Boolean) args[2] : true;
			searchConvert = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR) : searchSTR;
			searchRange = (args[3] != null) ?(int[])(args[3]) : (new int[] {0,0});
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

		BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
		if (bReader == null) {
			return;
		}
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = bReader;
			String line="";
			String line2="";
			int searchSTRinLine2=0;
			if ((!bool_wholeWords) && (searchConvert.contains(" "))) {
				inputStream2 = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
				searchSTRinLine2 = searchConvert.length()-searchConvert.indexOf(' ');
				//inputStream2.mark(640000);
				line2 = inputStream2.readLine();
			}
			//inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
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
				Tree.getInstance().changeRootText(Output.markText(searchSTR, Frame.headerStyleHTML));
				Output.printLine(Frame.lineHeaderSize);
			}
			// System.out.println(formatter.locale());
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
			}
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if ((searchRange[1]!=0) && 
						((countLines<=searchRange[0]) ||
								(countLines>searchRange[1]))) {
					continue;
				}
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
					String combineConvertedLines="";
					if (searchSTRinLine2>0) {
						line2 = (inputStream2.readLine());
						if (line2!=null) {
							line2=line2.substring(0, searchSTRinLine2);
							combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line+" "+line2) : (line+" "+line2));
						} else {
							combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line) : line);
						}
					} else {
						combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line) : line);
					}
					if (combineConvertedLines.contains(searchConvert)) {
						boolean foundInLine2=false;
						if (searchSTRinLine2>0) {
							if ((combineConvertedLines.lastIndexOf(searchConvert)+searchConvert.length())>line.length()) {
								foundInLine2=true;
							}
						}
						int countMatch = StringUtils.countMatches(combineConvertedLines, searchConvert);
						count = count + countMatch;
						if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
							frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
						}
						countPsukim++;
						// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
						// fill results array
						results.add(Output.printPasukInfo(countLines, searchSTR, 
								((foundInLine2)?(line+" "+line2):line), frame.Frame.markupStyleHTML,
								bool_sofiot, bool_wholeWords));
					}
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					break;
				}
			}
			if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame)) {
				Tree.getInstance().flushBuffer((count<50));
			}
			String Title = ((bool_wholeWords) ? "חיפוש מילים שלמות בתורה" : "חיפוש צירוף אותיות בתורה");
			String fileName = searchSTR;
			String sheet = ((bool_wholeWords) ? "מילים" : "אותיות");
			if (count > 0) {
				ExcelFunctions.writeXLS(fileName, sheet, (bool_sofiot) ? 0 : 1, Title, results, true
						,((ToraApp.getGuiMode()==ToraApp.id_guiMode_Frame)? Frame.get_searchRangeText():"")
						);
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
		}
	}
}
