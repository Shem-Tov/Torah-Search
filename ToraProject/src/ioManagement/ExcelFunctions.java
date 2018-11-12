package ioManagement;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import hebrewLetters.HebrewLetters;
import torahApp.ToraApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExcelFunctions {
	public static Boolean tableLoaded = false;
	public static final int id_searchSTR = 0; // for row 0
	public static final int id_searchLetter = 0; // for the rest of the rows
	public static final int id_toraLine = 4;
	public static final int id_charPOS = 5;
	public static final int id_lineNum = 6;

	public static String[][] readBookTableXLS(String[] inputFiles, int sheetNUM, int startColumn, int startRow, int endColumn, int endRow) {
		String[][] data = null;
		Workbook workbook = null;
		DataFormatter dataFormatter = new DataFormatter();
		for (int dloop = 0; dloop < inputFiles.length; dloop++) {
			try {
				InputStream excelFile;
				File file;
				if ((dloop == 0) && (inputFiles.length > 1)) {
					// file = new File(ClassLoader.getSystemResource(inputFiles[dloop]).toURI());
					excelFile = ExcelFunctions.class.getClassLoader().getResourceAsStream(inputFiles[dloop]);
				} else {
					file = new File(inputFiles[dloop]);
					excelFile = new FileInputStream(file);
				}
				workbook = new HSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(sheetNUM);
				Iterator<Row> iterator = datatypeSheet.iterator();
				data = new String[endColumn - startColumn][endRow - startRow];
				int i = 0;
				int j = 0;
				Row currentRow;
				while (j < startRow) {
					currentRow = iterator.next();
					j++;
				}
				while (iterator.hasNext()) {
					currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();
					i = startColumn;
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						data[i++ - startColumn][(j) - startRow] = dataFormatter.formatCellValue(currentCell);
						if (i >= (endColumn)) {
							break;
						}
					}
					j++;
					if (j >= (endRow)) {
						break;
					}
				}
				tableLoaded = true;
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
					frame.Frame.setButtonEnabled(true);
				}
				Output.printText("Imported XLS", 2);
				break;
			} catch (IOException | NullPointerException e) {
				if (dloop == inputFiles.length - 1) {
					if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
						frame.Frame.clearText();
					}
					Output.printText("Error importing from EXCEL Sheet", 1);
					Output.printText("Program can not work without TorahTables Excel file", 1);
					tableLoaded = false;
					try {
						frame.Frame.setButtonEnabled(false);
					} catch (NullPointerException ex) {
						// safe to ignore
					}
					// e.printStackTrace();
				}
			} finally {
				try {
					if (workbook != null) {
						workbook.close();
					}
				} catch (IOException e) {

				}
			}
		}
		return data;
	}
	public static String[][] readParashaTableXLS(String[] inputFiles, int sheetNUM, int X, int Y, int posX, int posY) {
		String[][] data = null;
		Workbook workbook = null;
		DataFormatter dataFormatter = new DataFormatter();
		for (int dloop = 0; dloop < inputFiles.length; dloop++) {
			try {
				InputStream excelFile;
				File file;
				if ((dloop == 0) && (inputFiles.length > 1)) {
					// file = new File(ClassLoader.getSystemResource(inputFiles[dloop]).toURI());
					excelFile = ExcelFunctions.class.getClassLoader().getResourceAsStream(inputFiles[dloop]);
				} else {
					file = new File(inputFiles[dloop]);
					excelFile = new FileInputStream(file);
				}
				workbook = new HSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(sheetNUM);
				Iterator<Row> iterator = datatypeSheet.iterator();
				data = new String[posX - X][posY - Y];
				int i = 0;
				int j = 0;
				Row currentRow;
				while (j < Y) {
					currentRow = iterator.next();
					j++;
				}
				while (iterator.hasNext()) {
					currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();
					i = X;
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						data[i++ - X][(j) - Y] = dataFormatter.formatCellValue(currentCell);
						if (i >= (posX)) {
							break;
						}
					}
					j++;
					if (j >= (posY)) {
						break;
					}
				}
				tableLoaded = true;
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
					frame.Frame.setButtonEnabled(true);
				}
				Output.printText("Imported XLS", 2);
				break;
			} catch (IOException | NullPointerException e) {
				if (dloop == inputFiles.length - 1) {
					if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
						frame.Frame.clearText();
					}
					Output.printText("Error importing from EXCEL Sheet", 1);
					Output.printText("Program can not work without TorahTables Excel file", 1);
					tableLoaded = false;
					try {
						frame.Frame.setButtonEnabled(false);
					} catch (NullPointerException ex) {
						// safe to ignore
					}
					// e.printStackTrace();
				}
			} finally {
				try {
					if (workbook != null) {
						workbook.close();
					}
				} catch (IOException e) {

				}
			}
		}
		return data;
	}

	// End Excel Tables

	// private static final String FILE_NAME = "/MyFirstExcel.xlsx";
	private static final String EXCEL_FILE_LOCATION = "./Reports/";
	private static final String EXCEL_FILE_EXTENSION = ".xls";

	public static void writeXLS(String fileName, String sheetName, int mode, String Title,
			ArrayList<String[][]> results, boolean bool_Padding, String...etc ) {
		if (!frame.Frame.getCheckbox_createExcel()) {
			return;
		}
		// mode=0 regular search sofiot considered
		// mode=1 regular search sofiot not considered
		// mode=2 dilugim search
		// mode=3 letters search

		// etc - additional information
		// fileName - name of excel file, can have multiple sheets
		// sheetName - sheetName, depends on search settings
		// mode - explained above
		// Title - used as header inside sheet
		// searchSTR - the string searched for
		// results - depends on mode, will be described below, at the switch
		// boolPadding - says if the Padding was succesful for Dilugim
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		File file = null;
		String fileNameExtended = EXCEL_FILE_LOCATION + fileName + EXCEL_FILE_EXTENSION;
		try {
			new File(EXCEL_FILE_LOCATION).mkdirs();
			file = new File(fileNameExtended);
			if (file.exists()) {
				FileInputStream excelFile = new FileInputStream(file);
				workbook = new HSSFWorkbook(excelFile);
			} else {
				workbook = new HSSFWorkbook();
				// HSSFSheet sheet = workbook.createSheet("Sample sheet1");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = workbook.getSheet(sheetName);
		if (sheet != null) {
			int index = workbook.getSheetIndex(sheetName);
			workbook.removeSheetAt(index);
		}
		sheet = workbook.createSheet(sheetName);
		HSSFFont txtFont = workbook.createFont();
		HSSFFont txtFontTitle = workbook.createFont();
		HSSFFont txtFontHeader = workbook.createFont();
		txtFont.setFontName("Nachlieli CLM");
		txtFont.setFontHeightInPoints((short) 16);
		txtFontTitle.setFontName("Nachlieli CLM");
		txtFontTitle.setBold(true);
		txtFontTitle.setFontHeightInPoints((short) 20);
		txtFontTitle.setColor(IndexedColors.PLUM.getIndex());
		txtFontHeader.setFontName("Nachlieli CLM");
		txtFontHeader.setBold(true);
		txtFontHeader.setFontHeightInPoints((short) 18);
		txtFontHeader.setColor(IndexedColors.BLUE_GREY.getIndex());
		CellStyle style = workbook.createCellStyle(); // Create new style
		style.setFont(txtFont);
		style.setAlignment(HorizontalAlignment.RIGHT);

		sheet.setDefaultRowHeightInPoints(36);
		// CellStyle fontStyle = workbook.createCellStyle();
		// fontStyle.setFont(txtFont);
		sheet.setRightToLeft(true);
		CellStyle styleTitle = workbook.createCellStyle(); // Create new style
		styleTitle.setWrapText(true); // Set wordwrap
		styleTitle.setFont(txtFontTitle);
		styleTitle.setAlignment(HorizontalAlignment.RIGHT);
		CellStyle styleHeader = workbook.createCellStyle(); // Create new style
		styleHeader.setFont(txtFontHeader);
		styleHeader.setAlignment(HorizontalAlignment.RIGHT);
		final String dilugWord = "דילוג";
		String[] header = { "אינדקס", dilugWord, "נמצא", "ספר", "פרק", "פסוק", "תורה" };

		int rowNum = 0;
		System.out.println("Creating excel " + fileNameExtended + " : Sheet - " + sheetName);

		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(0);
		// row.setRowStyle(style);
		row.setHeightInPoints(80);
		cell.setCellStyle(styleTitle);// Apply style to cell
		cell.setCellValue(Title);
		//if (mode == 3) {
		int countETC=1;
		for (String e:etc) {
			if (countETC==1) {
				sheet.setColumnWidth(1, 6000);
			}
			cell = row.createCell(countETC++);
			cell.setCellStyle(styleTitle);
			cell.setCellValue(e);
		}
		//}
		rowNum++;
		int colNum = 0;
		int index;
		row = sheet.createRow(rowNum++);
		row.setRowStyle(styleHeader);
		HSSFFont fontDilug = workbook.createFont();
		fontDilug.setFontName("Nachlieli CLM");
		fontDilug.setBold(true);
		fontDilug.setFontHeightInPoints((short) 26);
		fontDilug.setColor(IndexedColors.PLUM.getIndex());
		// unique table layout
		// mode 0 - Regular search - sofiot considered
		// mode 1 - Regular search - sofiot NOT considered
		// mode 2 - Dilugim
		switch (mode) {
		// mode = 0 regular search
		// multidimensional array used as one dimensional array shows result for each
		// word of search results
		// [0] = searchSTR
		// [1] = BookName
		// [2] = Perek
		// [3] = Pasuk
		// [4] = Torah of the Pasuk
		case 0:
		case 1:
		case 3:
			Boolean bool_sofiot = (mode == 0) ? true : false;
			for (String field : header) {
				if (field == dilugWord) {
					continue;
				}
				cell = row.createCell(colNum++);
				cell.setCellStyle(styleHeader);
				cell.setCellValue(field);
			}
			index = 1;
			for (String[][] resArr : results) {
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(index++);
				for (String[] res : resArr) {
					for (int i = 0; i < res.length; i++) {
						cell = row.createCell(i + 1);
						cell.setCellStyle(style);
						if (i == id_toraLine) {
							int j = 0;
							String lineConvert;
							String searchConvert;
							if (!bool_sofiot) {
								searchConvert = HebrewLetters.switchSofiotStr(res[id_searchSTR]);
								lineConvert = HebrewLetters.switchSofiotStr(res[id_toraLine]);
							} else {
								searchConvert = res[id_searchSTR];
								lineConvert = res[id_toraLine];
							}
							ArrayList<Integer> indexes = new ArrayList<Integer>();
							indexes.add(lineConvert.indexOf(searchConvert, 0));
							int STRLength = res[id_searchSTR].length();
							int newIndex = 0;
							// find all occurences of searchSTR in Line and Color them
							while ((newIndex = lineConvert.indexOf(searchConvert, indexes.get(j) + 1)) != -1) {
								indexes.add(newIndex);
								j++;
							}
							int lastIndex = 0;
							HSSFRichTextString richString = new HSSFRichTextString(new String(res[id_toraLine]));
							try {
								for (Integer thisIndex : indexes) {
									if (thisIndex > 0) {
										richString.applyFont(lastIndex, thisIndex, txtFont);
									}
									richString.applyFont(thisIndex, STRLength + thisIndex, fontDilug);
									lastIndex = thisIndex + STRLength;
								}
								richString.applyFont(lastIndex, richString.length(), txtFont);
								cell.setCellValue(richString);
							} catch (Exception e) {
								e.printStackTrace();
							}

							// HSSFCell hssfCell = row.createCell();
							// rich text consists of two runs

							// cell.setCellValue(res[i - 1]);
						} else {
							cell.setCellValue(res[i]);
						}
					}
				}
			}
			break;
		// mode = 2 dilugim search
		// multidimensional array - shows result for each letter of the search word in
		// every search
		// [0][0] = Dilug Number
		// [0][1] = searchSTR
		// [0][2] = padding
		// [0][3] = headPadding
		// [0][4] = tailPadding
		// [0] = searchLetter(except first Row)
		// [1] = Book Name (except first Row)
		// [2] = Perek
		// [3] = Pasuk
		// [4] = Torah of the Pasuk
		// [5] = Character position in the Pasuk for the letter matching the Dilug
		// [6] = Line Number of Pasuk
		// Search
		case 2:

			for (String field : header) {
				cell = row.createCell(colNum++);
				cell.setCellStyle(styleHeader);
				cell.setCellValue(field);
			}
			index = 1;

			for (String[][] resArr : results) {
				// if ((mode==1) && (counter++==0)) { // The Dilugim Mode needs to skip first
				// array
				// continue;
				// }
				// results has all results
				// resArr holds one result
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(index++);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(resArr[0][0]);
				cell = row.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue(resArr[0][1]);
				cell = row.createCell(6);
				cell.setCellStyle(style);
				HSSFRichTextString richString;
				// Padding Check
				if (bool_Padding) {
					richString = new HSSFRichTextString(resArr[0][2]);
					try {
						richString.applyFont(0, 1 + Integer.parseInt(resArr[0][3]), txtFont);
						richString.applyFont(Integer.parseInt(resArr[0][3]), Integer.parseInt(resArr[0][4]), fontDilug);
						richString.applyFont(Integer.parseInt(resArr[0][4]), richString.length(), txtFont);
						cell.setCellValue(richString);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				int j = 0;
				int counter = 0;
				Boolean boolRepeat = false;
				String reportLine = "";
				ArrayList<Integer> charPOSList = new ArrayList<Integer>();
				for (String[] res : resArr) {
					// resArr holds one result
					// resArr is a Database with some rows of data
					// res is one row of data
					// when "res" is the first row it is:
					// first row [0][0] containing Dilug Number
					// first row [0][1] containing Search String
					//
					// The rest of the time "res" holds
					// [0] One Search Letter in order of Search String
					// [1] = Book Name (except first Row)
					// [2] = Perek
					// [3] = Pasuk
					// [4] = Torah of the Pasuk
					// [5] = Character position in the Pasuk for the letter matching the Dilug
					// [6] = Line Number of Pasuk
					j++; // index next result
					if (counter++ == 0) {
						// skip first row (row 0)
						continue;
					}
					if (!boolRepeat) {
						charPOSList = new ArrayList<Integer>();
						row = sheet.createRow(rowNum++);
						reportLine = "";
					}
					charPOSList.add(Integer.parseInt(res[id_charPOS]) - 1);
					reportLine += res[id_searchLetter];
					if ((j < (resArr[0][1].length() + 1)) && (res[id_lineNum].equals(resArr[j][id_lineNum]))) {
						boolRepeat = true;
						continue;
					}
					boolRepeat = false;
					for (int i = 0; i < res.length; i++) {
						if ((i == id_charPOS) || (i == id_lineNum)) {
							continue;
						}
						cell = row.createCell(i + 2);
						cell.setCellStyle(style);
						if (i == id_toraLine) {
							// Compares lineNum of this and next index in resArr.
							// j is the index of the letter + 1 because first index doesn't count
							// ignore the last letter of the String Search
							// HSSFCell hssfCell = row.createCell();
							// rich text consists of two runs
							int lastIndex = 0;
							richString = new HSSFRichTextString(new String(res[id_toraLine]));
							try {
								for (Integer thisIndex : charPOSList) {

									if (thisIndex > 0) {
										richString.applyFont(lastIndex, thisIndex, txtFont);
									}
									richString.applyFont(thisIndex, 1 + thisIndex, fontDilug);
									lastIndex = thisIndex + 1;
								}
								richString.applyFont(lastIndex, richString.length(), txtFont);
								cell.setCellValue(richString);

								/*
								 * richString.applyFont(0, Integer.parseInt(res[id_charPOS]) - 1, txtFont);
								 * richString.applyFont(Integer.parseInt(res[id_charPOS]) - 1,
								 * Integer.parseInt(res[id_charPOS]), fontDilug);
								 * richString.applyFont(Integer.parseInt(res[id_charPOS]), richString.length(),
								 * txtFont);
								 */

							} catch (Exception e) {
								e.printStackTrace();
							}
							// cell.setCellValue(res[i - 1]);
						} else if (i == id_searchLetter) {
							cell.setCellValue(reportLine);
						} else {
							cell.setCellValue(res[i]);
						}
					}
				}
			}
			break;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);

		try {
			FileOutputStream outputStream = new FileOutputStream(fileNameExtended);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	public static void main(String[] args) {
		// writeXLS(new String[] { "Hello", "1", "2", "3" });

		writeXLS("בדיקה", "דוח", 0, "Title", new ArrayList<String[][]>(
				Arrays.asList(new String[][] { { "1", "2" } }, new String[][] { { "3", "4" } })), true);

	}

}
