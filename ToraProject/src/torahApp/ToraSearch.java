package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import extras.extraFunctions;
import frame.ColorClass;
import frame.Frame;
import frame.Tree;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.LineReport;
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
		ArrayList<LineReport> results = new ArrayList<LineReport>();
		// String[][] results=null;
		BufferedReader inputStream = null;
		BufferedReader inputStream2 = null;
		// StringWriter outputStream = null;
		String searchSTR;
		String searchConvert;
		int[] searchRange;
		boolean bool_wholeWords;
		boolean bool_sofiot;
		boolean bool_multiSearch;
		boolean bool_multiMustFindBoth = true;
		String searchSTR2 = "", searchConvert2 = "";
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 3) {
				throw new IllegalArgumentException("Missing Arguments in ToraSearch.searchWords");
			}
			searchSTR = ((String) args[0]).trim();
			bool_wholeWords = (args[1] != null) ? (Boolean) args[1] : true;
			bool_sofiot = (args[2] != null) ? (Boolean) args[2] : true;
			searchConvert = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR) : searchSTR;
			searchRange = (args[3] != null) ? (int[]) (args[3]) : (new int[] { 0, 0 });
			bool_multiSearch = (args[4] != null) ? (Boolean) args[4] : false;
			if (bool_multiSearch) {
				searchSTR2 = ((String) args[5]).trim();
				searchConvert2 = (!bool_sofiot) ? HebrewLetters.switchSofiotStr(searchSTR2) : searchSTR2;
				bool_multiMustFindBoth = (args[6] != null) ? (Boolean) args[6] : true;
			}
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
			String line2 = "";
			int searchSTRinLine2 = 0;
			if ((!bool_wholeWords) && (searchConvert.contains(" "))) {
				inputStream2 = ManageIO.getBufferedReader(
						(Frame.getCheckBox_DifferentSearch())? ManageIO.fileMode.Different : ManageIO.fileMode.Line);
				searchSTRinLine2 = searchConvert.length() - searchConvert.indexOf(' ');
				// inputStream2.mark(640000);
				line2 = inputStream2.readLine();
			}
			// inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"";
			if (bool_multiSearch) {
				str += " | \"" + searchSTR2 + "\"";
			}
			str += "...";
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			str = "\u202B" + ((bool_wholeWords) ? "חיפוש מילים שלמות" : "חיפוש צירופי אותיות");
			Output.printText(Output.markText(str, frame.ColorClass.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				String tempStr = searchSTR;
				if (bool_multiSearch) {
					tempStr += " | " + searchSTR2;
				}
				Tree.getInstance().changeRootText(Output.markText(tempStr, ColorClass.headerStyleHTML));
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
					Boolean found1 = false, found2 = false;
					for (String s : splitStr) {
						if (bool_multiSearch) {
							if (s.equals(searchConvert) && (!found1)) {
								found1 = true;
							} else if (s.equals(searchConvert2)) {
								found2 = true;
							}
						}
						if (((bool_multiSearch) && (found1) && (found2))
								|| ((bool_multiSearch) && (!bool_multiMustFindBoth) && ((found1) || (found2)))
								|| ((!bool_multiSearch) && (s.equals(searchConvert)))) {
							count++;
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
							}
							// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
							// fill results array

							if (bool_multiSearch) {
								if ((found1) && (found2)) {
									results.add(Output.printPasukInfo(countLines, searchSTR, line,
											frame.ColorClass.markupStyleHTML, bool_sofiot, bool_wholeWords, searchSTR2));
								} else if (found1) {
									results.add(Output.printPasukInfo(countLines, searchSTR, line,
											frame.ColorClass.markupStyleHTML, bool_sofiot, bool_wholeWords));
								} else { // then (found2)
									results.add(Output.printPasukInfo(countLines, searchSTR2, line,
											frame.ColorClass.markupStyleHTML, bool_sofiot, bool_wholeWords));
								}
								break;
							} else {
								results.add(Output.printPasukInfo(countLines, searchSTR, line,
										frame.ColorClass.markupStyleHTML, bool_sofiot, bool_wholeWords));
							}
						}
					}
				} else {
					String combineConvertedLines = "";
					if (searchSTRinLine2 > 0) {
						line2 = (inputStream2.readLine());
						if (line2 != null) {
							line2 = line2.substring(0, searchSTRinLine2);
							combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line + " " + line2)
									: (line + " " + line2));
						} else {
							combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line) : line);
						}
					} else {
						combineConvertedLines = ((!bool_sofiot) ? HebrewLetters.switchSofiotStr(line) : line);
					}
					boolean found1 = (combineConvertedLines.contains(searchConvert));
					boolean found2 = ((bool_multiSearch) && (combineConvertedLines.contains(searchConvert2)));
					if ((found1) || ((found2) && (!bool_multiMustFindBoth))) {

						if ((!bool_multiSearch)
								|| ((extraFunctions.logicalXOR(found1, found2)) && (!bool_multiMustFindBoth))) {
							boolean foundInLine2 = false;
							if (searchSTRinLine2 > 0) {
								if (found1) {
									if ((combineConvertedLines.lastIndexOf(searchConvert)
											+ searchConvert.length()) > line.length()) {
										foundInLine2 = true;
									}
								} else {
									if ((combineConvertedLines.lastIndexOf(searchConvert2)
											+ searchConvert2.length()) > line.length()) {
										foundInLine2 = true;
									}	
								}
							}
							int countMatch;
							countMatch = StringUtils.countMatches(combineConvertedLines, (found1)?searchConvert:searchConvert2);
							count = count + countMatch;
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
							}
							countPsukim++;
							// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
							// fill results array
							results.add(Output.printPasukInfo(countLines, (found1) ? searchSTR:searchSTR2,
									((foundInLine2) ? (line + " " + line2) : line), frame.ColorClass.markupStyleHTML,
									bool_sofiot, bool_wholeWords));
						} else if ((combineConvertedLines.contains(searchConvert2))) {
							if ((searchConvert2.contains(searchConvert)) || (searchConvert.contains(searchConvert2))) {
								if ((combineConvertedLines.indexOf(searchConvert,
										combineConvertedLines.indexOf(searchConvert2) + 1) != -1)
										|| (combineConvertedLines.indexOf(searchConvert2,
												combineConvertedLines.indexOf(searchConvert) + 1) != -1)) {
									boolean foundInLine2 = false;
									if (searchSTRinLine2 > 0) {
										if ((combineConvertedLines.lastIndexOf(searchConvert)
												+ searchConvert.length()) > line.length()) {
											foundInLine2 = true;
										}
									}
									count++;
									if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
										frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
									}
									countPsukim++;
									// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
									// fill results array
									results.add(Output.printPasukInfo(countLines, searchSTR,
											((foundInLine2) ? (line + " " + line2) : line), frame.ColorClass.markupStyleHTML,
											bool_sofiot, bool_wholeWords, searchSTR2));

								}
							} else {
								boolean foundInLine2 = false;
								if (searchSTRinLine2 > 0) {
									if ((combineConvertedLines.lastIndexOf(searchConvert)
											+ searchConvert.length()) > line.length()) {
										foundInLine2 = true;
									}
								}
								count++;
								if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
									frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
								}
								countPsukim++;
								// printPasukInfo gets the Pasuk Info, prints to screen and sends back array to
								// fill results array
								results.add(Output.printPasukInfo(countLines, searchSTR,
										((foundInLine2) ? (line + " " + line2) : line), frame.ColorClass.markupStyleHTML,
										bool_sofiot, bool_wholeWords, searchSTR2));

							}
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
			String Title = ((bool_wholeWords) ? "חיפוש מילים שלמות בתורה" : "חיפוש צירוף אותיות בתורה");
			String Title2 ="";
			String Title3 ="";
			String fileName = searchSTR.replace(' ', '_');
			if (bool_multiSearch) {
				fileName += "_" + searchSTR2.replace(' ', '_');
			}
			String sheet = ((bool_wholeWords) ? "מילים" : "אותיות");
			if (bool_multiSearch) {
				sheet += "_מולטי";
				Title2+="חיפוש כמה מילים";
				if (bool_multiMustFindBoth) {
					sheet += "_כל";
					Title3+="פסוקים הכוללים את שני המילים";
				} else {
					sheet += "_אחד";
					Title3+="פסוקים שיש בו לפחות מילה אחת";
					
				}
			}
			
			if (count > 0) {
				ExcelFunctions.writeXLS(fileName, sheet, (bool_sofiot) ? 0 : 1, Title, results,
						Title2,Title3,searchSTR,searchSTR2,
						((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) ? Frame.get_searchRangeText() : ""));
			}
		} catch (

		Exception e) {
			Output.printText("Error with loading Lines.txt", 1);
			e.printStackTrace();
		} finally {
			Output.printText("");
			Output.printText(
					Output.markText(
							"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים"
									+ ((bool_wholeWords) ? "."
											: (" ב" + "\u00A0" + String.valueOf(countPsukim) + " פסוקים.")),
							frame.ColorClass.footerStyleHTML));
			Output.printText("");
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.ColorClass.footerStyleHTML));
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
