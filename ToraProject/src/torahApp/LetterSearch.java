package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frame.ColorClass;
import frame.Frame;
import frame.Tree;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.LineHtmlReport;
import ioManagement.LineReport;
import ioManagement.ManageIO;
import ioManagement.Output;
import stringFormat.StringAlignUtils;

public class LetterSearch {
	private static LetterSearch instance;

	public static LetterSearch getInstance() {
		if (instance == null) {
			instance = new LetterSearch();
		}
		return instance;
	}

	static public Map<Character, Integer> toMap(String searchSTR) {
		ArrayList<Character> lst = new ArrayList<Character>();
		for (char c : searchSTR.toCharArray()) {
			lst.add(c);
		}
		return lst.stream().collect(HashMap<Character, Integer>::new, (map, str) -> {
			if (!map.containsKey(str)) {
				map.put(str, 1);
			} else {
				map.put(str, map.get(str) + 1);
			}
		}, HashMap<Character, Integer>::putAll);
	}

	private Boolean containsLetters(String pLine, Map<Character, Integer> searchMap) {
		Map<Character, Integer> pLineMap = toMap(pLine);
		Boolean foundMatch = true;
		for (Character key : searchMap.keySet()) {
			if ((!pLineMap.containsKey(key)) || (searchMap.get(key) > pLineMap.get(key))) {
				foundMatch = false;
				break;
			}
		}

		return foundMatch;
	}

	private Boolean containsOrderOfLetters(String line, String searchSTR) {
		Boolean found = true;
		int oldIndex = 0;
		int index = -1;
		for (char ch : searchSTR.toCharArray()) {
			index = line.indexOf(ch, oldIndex);
			if (index == -1) {
				found = false;
				break;
			} else {
				oldIndex = index;
			}
		}
		return found;
	}

	public void searchForLetters(Object[] args) {
		ArrayList<LineReport> results = new ArrayList<LineReport>();
		// String[][] results=null;
		BufferedReader inputStream = null;
		String searchSTR;
		String searchConvert;
		// modePsukim - false = checks letters in words
		// true = checks letters in psukim
		boolean modePsukim = false;
		int[] searchRange;
		boolean bool_sofiot;
		boolean bool_keepOrder = false;
		boolean bool_firstLastLetters = false;
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 2) {
				throw new IllegalArgumentException("Missing Arguments in LetterSearch.searchForLetters");
			}
			searchSTR = (String) args[0];
			bool_sofiot = (args[1] != null) ? (Boolean) args[1] : true;
			searchConvert = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR) : searchSTR;
			searchRange = (args[2] != null) ? (int[]) (args[2]) : (new int[] { 0, 0 });
			modePsukim = (args[3] != null) ? (Boolean) args[3] : false;
			bool_keepOrder = (args[4] != null) ? (Boolean) args[4] : false;
			bool_firstLastLetters = (args[5] != null) ? (Boolean) args[5] : false;
		} catch (ClassCastException e) {
			Output.printText("casting exception...", 1);
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}

		Map<Character, Integer> searchMap = toMap(searchConvert);
		int countLines = 0;
		int count = 0;

		BufferedReader bReader = ManageIO.getBufferedReader(
				(Frame.getCheckBox_DifferentSearch()) ? ManageIO.fileMode.Different : ManageIO.fileMode.Line);
		if (bReader == null) {
			Output.printText("לא הצליח לפתוח קובץ תורה", 1);
			return;
		}
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = bReader;
			// outputStream2 = new FileWriter("/myText.txt", false);
			inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			String line;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "חיפוש אותיות"; 
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				Tree.getInstance().changeRootText(Output.markText(searchSTR, ColorClass.headerStyleHTML));
				Output.printLine(Frame.lineHeaderSize);
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
			}
			// System.out.println(formatter.locale());
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if ((searchRange[1] != 0) && ((countLines <= searchRange[0]) || (countLines > searchRange[1]))) {
					continue;
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}

				if (searchSTR.contains(" ")) {
					if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
						frame.Frame.clearTextPane();
					}
					Output.printText("לא ניתן לחפש עם רווחים", 1);
					if (inputStream != null) {
						inputStream.close();
					}
					return;
				}
				String[] splitStr;
				if (modePsukim) {
					splitStr = new String[1];
					splitStr[0] = line.trim();
				} else {
					splitStr = line.trim().split("\\s+");
				}
				for (String s : splitStr) {
					// Do your stuff here
					String sConvert;
					if (!bool_sofiot) {
						sConvert = HebrewLetters.switchSofiotStr(s);
					} else {
						sConvert = s;
					}
					if ((bool_firstLastLetters) && ((searchConvert.charAt(0) != sConvert.charAt(0))
							|| (!searchConvert.substring(searchConvert.length() - 1)
									.equals(sConvert.substring(sConvert.length() - 1))))) {
						continue;
					}
					if (((!bool_keepOrder) && (containsLetters(sConvert, searchMap)))
							|| ((bool_keepOrder) && containsOrderOfLetters(sConvert, searchConvert))) {
						count++;
						if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
							frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
						}
						// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
						// fill results array
						if (modePsukim) {
							ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
							String tempStr1 = "\u202B" + "\""
									+ Output.markText(searchSTR, frame.ColorClass.markupStyleHTML) + "\" " + "נמצא ב"
									+ Output.markText(StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " "
											+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters(),
											ColorClass.highlightStyleHTML[pBookInstance.getBookNumber()
													% ColorClass.highlightStyleHTML.length]);
							// Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " = " + line);
							String lineHtml;
							LineHtmlReport lineHtmlReport = new LineHtmlReport(null, null);
							if (bool_keepOrder) {
								lineHtmlReport = Output.markTextOrderedLetters(searchSTR, line, bool_sofiot,
										bool_firstLastLetters, frame.ColorClass.markupStyleHTML);
								lineHtml = lineHtmlReport.getLineHtml();
							} else {
								lineHtml = line;
							}
							String tempStr2 = StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineHtml;
							Output.printText(tempStr2);
							results.add(new LineReport(
									new String[] { searchSTR, pBookInstance.getBookName(),
											pBookInstance.getPerekLetters(), pBookInstance.getPasukLetters(), line },
									lineHtmlReport.getIndexes()));
							String tooltip = Output.printSomePsukimHtml(countLines, Output.padLines);
							Output.printText("טקסט נוסף", 3, tooltip);
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								Output.printTree(countLines, tempStr2, false);
							}
						} else {
							results.add(Output.printPasukInfo(countLines, s, line, frame.ColorClass.markupStyleHTML,
									bool_sofiot, true));
						}
					}
				}

				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					break;
				}
			}
			if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame)) {
				Tree.getInstance().flushBuffer((count < 50));
			}
			String Title = ("חיפוש אותיות במילים בתורה");
			String Title2 = "";
			String Title3 = "";
			String Title4 = "";
			String fileName = "LT_" + searchSTR.replace(' ', '_');
			String sheet = "";
			if (modePsukim) {
				sheet += "פסוק";
			} else {
				sheet += "מילה";
			}
			if (bool_sofiot) {
				sheet += "_" + "ף";
				Title2 += "התחשבות בסופיות";
			}
			if (bool_firstLastLetters) {
				sheet += "_" + "רת";
				Title3 += "התחשבות ראשי וסופי תיבות";

			}
			if (bool_keepOrder) {
				sheet += "_" + "סדר";
				Title4 += "שמירת סדר אותיות";
			}

			if (count > 0) {
				ExcelFunctions.writeXLS(fileName, sheet, 3, Title, results, searchSTR, Title2, Title3, Title4,
						((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) ? Frame.get_searchRangeText() : ""));
			}
		} catch (

		Exception e) {
			Output.printText("Error with at line " + countLines, 1);
			e.printStackTrace();
		} finally {
			Output.printText("");
			Output.printText(Output.markText(
					"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים" + ".",
					frame.ColorClass.footerStyleHTML));
			Output.printText("");
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.ColorClass.footerStyleHTML));
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
