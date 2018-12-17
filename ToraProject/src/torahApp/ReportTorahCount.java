package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.IndexedColors;

import frame.ColorClass;
import frame.Frame;
import ioManagement.ExcelFunctions;
import ioManagement.FontStyle;
import ioManagement.LineReport;
import ioManagement.ManageIO;
import ioManagement.Output;
import stringFormat.HtmlGenerator;
import stringFormat.StringAlignUtils;

public class ReportTorahCount {
	private static ReportTorahCount instance;

	public static ReportTorahCount getInstance() {
		if (instance == null) {
			instance = new ReportTorahCount();
		}
		return instance;
	}

	private static char[] Letters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע',
			'פ', 'צ', 'ק', 'ר', 'ש', 'ת', 'ך', 'ם', 'ן', 'ף', 'ץ' };

	private static int getCharIndex(char ch) {
		int index = 0;
		for (char c : Letters) {
			if (ch == c) {
				break;
			}
			index++;
		}
		return index;
	}

	private static int getSofit(char ch) {
		switch (ch) {
		case 'כ':
			return 22;
		case 'מ':
			return 23;
		case 'נ':
			return 24;
		case 'פ':
			return 25;
		case 'צ':
			return 26;
		}
		return -1;
	}

	public void createReport(Object[] args) throws IOException {
		// String[][] results=null;
		BufferedReader inputStream = null;
		// StringWriter outputStream = null;
		int[] searchRange;
		String[] stringRange;
		// FileWriter outputStream2 = null;
		try {
			try {
				searchRange = (args[0] != null) ? (int[]) args[0] : new int[] { 0, 0 };
				stringRange = (args[1] != null) ? (String[]) args[1] : new String[] { "התחלה", "סוף" };
			} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
				searchRange = new int[] {0,0};
				stringRange = new String[] { "התחלה", "סוף" };
			}
		} catch (ClassCastException e) {
			Output.printText("casting exception...", 1);
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}
		int countLines = 0;
		int countLinesInRange = 0;
		int countLetters = 0;
		int countWords = 0;
		int[] countSpecLetters = new int[27];
		int[] countHWordSpecLetters = new int[27];
		int[] countTWordSpecLetters = new int[27];
		int[] countHPasukSpecLetters = new int[27];
		int[] countTPasukSpecLetters = new int[27];

		BufferedReader bReader = ManageIO.getBufferedReader(
				(Frame.getCheckBox_DifferentSearch())? ManageIO.fileMode.Different : ManageIO.fileMode.Line);
		if (bReader == null) {
			Output.printText("לא הצליח לפתוח קובץ תורה", 1);
			return;
		}
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = bReader;
			String line = "";
			// inputStream.mark(640000);
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "דוח ספירה"; 
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			str = "\u202B" + "סופר";
			str += "...";
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				Output.printLine(Frame.lineHeaderSize);
			}
			// System.out.println(formatter.locale());
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
			}
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if ((searchRange[1] != 0) && ((countLines <= searchRange[0]) || (countLines > searchRange[1]))) {
					continue;
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}
				String[] splitStr;
				countLinesInRange++;
				countHPasukSpecLetters[getCharIndex(line.charAt(0))]++;
				countTPasukSpecLetters[getCharIndex(line.charAt(line.length() - 1))]++;
				splitStr = line.split("\\s+");
				for (String s : splitStr) {
					countWords++;
					countHWordSpecLetters[getCharIndex(s.charAt(0))]++;
					countTWordSpecLetters[getCharIndex(s.charAt(s.length() - 1))]++;
					for (char c : s.toCharArray()) {
						countLetters++;
						countSpecLetters[getCharIndex(c)]++;
					}
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					break;
				}
			}
		} catch (

		Exception e) {
			Output.printText("Error with loading Lines.txt", 1);
			e.printStackTrace();
		} finally {
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				String style = " style = \"padding: 8px;  border: 2px solid #ccc; text-align: right; "
						+ " font-family: Arial, Verdana, sans-serif;" + " color:		#" + ColorClass.getRGBmainStyleHTML()
						+ ";" + " font-size:	" + Frame.getFontSize() + "px;" + " font-weight:bold" + "\"";
				// String tdstyle2 =" style = \"padding-top: 8px; padding-bottom: 8px;\"";
				String tdstyle = " style = \"padding: 8px; padding-left: 8px; padding-right: 8px;\"";
				Frame.clearTextPane();
				Output.printText("");
				Output.printText(
						Output.markText("טווח:", ColorClass.headerStyleHTML) +
						Output.markText(stringRange[0], ColorClass.markupStyleHTML) +
						Output.markText(" עד ", ColorClass.headerStyleHTML) +
						Output.markText(stringRange[1], ColorClass.markupStyleHTML) +
						Output.markText(stringRange[2], ColorClass.headerStyleHTML));
				Output.printLine(4,"#FFD700",0);
				// FirstTable
				int fontSizeBig = Frame.getFontSizeBig() + 1;
				int fontSizeBigger = fontSizeBig + 1;
				StringBuilder str = new StringBuilder();
				str.append("<Table border=\"1\"" + style + ">" + "<TR" + style + "><TH></TH><TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBigger, HtmlGenerator.mode_header)
						+ ">דוח כללי</TH></TR>" + "<TR" + style + "><TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">מספר</TH></TR>");
				str.append("<TR" + style + "><td" + tdstyle + ">" + countLinesInRange + "</td><Th"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_markup) + ">"
						+ "מספר פסוקים" + "</Th></TR>");
				str.append("<TR" + style + "><td" + tdstyle + ">" + countWords + "</td><Th"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_markup) + ">"
						+ "מספר מילים" + "</Th></TR>");
				str.append("<TR" + style + "><td" + tdstyle + ">" + countLetters + "</td><Th"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_markup) + ">"
						+ "מספר אותיות" + "</Th></TR>");
				// str.append("<TR"+style+"><td"+tdstyle+">" + "" + "</td><Th>" + "" +
				// "</Th></TR>");
				str.append("</TABLE>");
				Output.printText(str.toString());
				// SecondTable
				str = new StringBuilder();
				str.append("<Table border=\"1\"" + style + ">" + "<TR" + style
						+ "><TH></TH><TH></TH><TH></TH><TH></TH><TH></TH>" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBigger, HtmlGenerator.mode_header)
						+ ">דוח אותיות</TH></TR>" + "<TR" + style + ">" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">בסוף פסוק</TH>" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">בראש פסוק</TH>" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">בסוף מילה</TH>" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">בראש מילה</TH>" + "<TH"
						+ HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header) + ">מספר</TH>"
						+ "<TH" + HtmlGenerator.createFontSizeColorStyle(fontSizeBig, HtmlGenerator.mode_header)
						+ ">אות</TH></TR>");
				int j = 0;
				for (int i = 0; i < Letters.length; i++) {
					// str.append("<TR><td"+style+">"+ i+ "</td><Td"+style+">" + Letters[i] +
					// "</Td></TR>");
					str.append("<TR" + style + "><td" + tdstyle + ">" + countTPasukSpecLetters[i]
							+ (((j = getSofit(Letters[i])) != -1)
									? " (" + (countTPasukSpecLetters[j] + countTPasukSpecLetters[i]) + ")"
									: "")
							+ "</td><td" + tdstyle + ">" + countHPasukSpecLetters[i]
							+ (((j = getSofit(Letters[i])) != -1)
									? " (" + (countHPasukSpecLetters[j] + countHPasukSpecLetters[i]) + ")"
									: "")
							+ "</td><td" + tdstyle + ">" + countTWordSpecLetters[i]
							+ (((j = getSofit(Letters[i])) != -1)
									? " (" + (countTWordSpecLetters[j] + countTWordSpecLetters[i]) + ")"
									: "")
							+ "</td><td" + tdstyle + ">" + countHWordSpecLetters[i]
							+ (((j = getSofit(Letters[i])) != -1)
									? " (" + (countHWordSpecLetters[j] + countHWordSpecLetters[i]) + ")"
									: "")
							+ "</td><td" + tdstyle + ">" + countSpecLetters[i]
							+ (((j = getSofit(Letters[i])) != -1)
									? " (" + (countSpecLetters[j] + countSpecLetters[i]) + ")"
									: "")
							+ "</td><Th"
							+ HtmlGenerator.createFontSizeColorStyle(fontSizeBigger, HtmlGenerator.mode_markup) + ">"
							+ Letters[i] + "</Th></TR>");
				}
				str.append("</TABLE>");
				Output.printText(str.toString());
				Output.printText("");
				Output.printText(Output.markText("\u202B" + "סיים דוח", frame.ColorClass.footerStyleHTML));
			} else {
				Output.printText("Works only in GUI Mode");
			}
			ArrayList<LineReport> results = new ArrayList<LineReport>();
			FontStyle titleStyle = new FontStyle(IndexedColors.PLUM,20);
			FontStyle headerStyle = new FontStyle(IndexedColors.GREEN,18);
			FontStyle goldStyle = new FontStyle(IndexedColors.GOLD,18);
			results.add(new LineReport(new String[] {"טווח:"+" "+stringRange[0]+" עד " + stringRange[1] + " " +stringRange[2]},null,titleStyle));
			results.add(new LineReport(new String[] {},null));
			results.add(new LineReport(new String[] {"דוח כללי"},null,titleStyle));
			results.add(new LineReport(new String[] {"","מספר"},null,headerStyle));
			results.add(new LineReport(new String[] {"מספר פסוקים",String.valueOf(countLinesInRange)},null,null,headerStyle));
			results.add(new LineReport(new String[] {"מספר מילים",String.valueOf(countWords)},null,null,headerStyle));
			results.add(new LineReport(new String[] {"מספר אותיות",String.valueOf(countLetters)},null,null,headerStyle));
			results.add(new LineReport(new String[] {},null));
			results.add(new LineReport(new String[] {"דוח אותיות"},null,titleStyle));
			results.add(new LineReport(new String[] {"אות","מספר","בראש מילה","בסוף מילה","בראש פסוק","בסוף פסוק"},null,headerStyle));
			int j = 0;
			for (int i = 0; i < Letters.length; i++) {
				// str.append("<TR><td"+style+">"+ i+ "</td><Td"+style+">" + Letters[i] +
				// "</Td></TR>");
				results.add(new LineReport(new String[] {
						String.valueOf(Letters[i])
						,countSpecLetters[i]+(((j = getSofit(Letters[i])) != -1) ? " (" + (countSpecLetters[j] + countSpecLetters[i]) + ")" : "")
						,countHWordSpecLetters[i]+(((j = getSofit(Letters[i])) != -1) ? " (" + (countHWordSpecLetters[j] + countHWordSpecLetters[i]) + ")" : "")
						,countTWordSpecLetters[i]+(((j = getSofit(Letters[i])) != -1) ? " (" + (countTWordSpecLetters[j] + countTWordSpecLetters[i]) + ")" : "")
						,countHPasukSpecLetters[i]+(((j = getSofit(Letters[i])) != -1) ? " (" + (countHPasukSpecLetters[j] + countHPasukSpecLetters[i]) + ")" : "")
						,countTPasukSpecLetters[i]+(((j = getSofit(Letters[i])) != -1) ? " (" + (countTPasukSpecLetters[j] + countTPasukSpecLetters[i]) + ")"	: "")
						},null,null,goldStyle));
			}
			String fileName = "דוח" + "_" + searchRange[0] + "_" + searchRange[1];
			String sheet = "דוח";
			String Title = "דוח";
			ExcelFunctions.writeXLS(fileName, sheet, 4, Title, results);

			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

}
