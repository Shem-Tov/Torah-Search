package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frame.Frame;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.ManageIO;
import ioManagement.Output;
import stringFormatting.StringAlignUtils;

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

	public void searchForLetters(Object[] args) throws IOException {
		ArrayList<String[][]> results = new ArrayList<String[][]>();
		// String[][] results=null;
		BufferedReader inputStream = null;
		String searchSTR;
		String searchConvert;
		boolean bool_sofiot;
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 2) {
				throw new IllegalArgumentException("Missing Arguments in LetterSearch.searchForLetters");
			}
			searchSTR = (String) args[0];
			bool_sofiot = (args[1] != null) ? (Boolean) args[1] : true;
			searchConvert = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR) : searchSTR;
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

		BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
		if (bReader == null) {
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
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
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

				if (searchSTR.contains(" ")) {
					if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
						frame.Frame.clearText();
					}
					Output.printText("לא ניתן לחפש עם רווחים", 1);
					if (inputStream != null) {
						inputStream.close();
					}
					return;
				}
				String[] splitStr = line.trim().split("\\s+");
				for (String s : splitStr) {
					// Do your stuff here
					String sConvert;
					if (!bool_sofiot) {
						sConvert = HebrewLetters.switchSofiotStr(s);
					} else {
						sConvert = s;
					}
					if (containsLetters(sConvert, searchMap)) {
						count++;
						if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
							frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
						}
						// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
						// fill results array
						results.add(Output.printPasukInfo(countLines, s, line, frame.Frame.markupStyleHTML, bool_sofiot,
								true));
					}
				}

				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					break;
				}
			}
			String Title = ("חיפוש אותיות במילים בתורה");
			String fileName = "LT_" + searchSTR;
			String sheet = ("אותיות");
			if (count > 0) {
				ExcelFunctions.writeXLS(searchSTR, fileName, sheet, 3, Title, results, true);
			}
		} catch (

		Exception e) {
			Output.printText("Error with at line " + countLines, 1);
			e.printStackTrace();
		} finally {
			Output.printText("");
			Output.printText(Output.markText(
					"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים" + ".",
					frame.Frame.footerStyleHTML));
			Output.printText("");
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.Frame.footerStyleHTML));
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
