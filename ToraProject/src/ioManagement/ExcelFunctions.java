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

import torahApp.ToraApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelFunctions {
	public static Boolean tableLoaded = false;
	public static final int id_searchSTR = 0; // for row 0
	public static final int id_searchLetter = 0; // for the rest of the rows
	public static final int id_torahLine = 4;
	public static final int id_dilugTorahLine = 5;
	public static final int id_charPOS = 5;
	public static final int id_lineNum = 6;

	// End Excel Tables

	// private static final String FILE_NAME = "/MyFirstExcel.xlsx";
	private static final String EXCEL_FILE_LOCATION_HARDCODED = "./Reports/";
	private static String EXCEL_FILE_LOCATION = EXCEL_FILE_LOCATION_HARDCODED;
	private static final String EXCEL_FILE_EXTENSION = ".xls";

	public static String getExcel_File_Location_Hardcoded() {
		return EXCEL_FILE_LOCATION_HARDCODED;
	}

	public static String getExcel_File_Location() {
		return EXCEL_FILE_LOCATION;
	}

	public static void setExcel_File_Location(String directoryPath) {
		EXCEL_FILE_LOCATION = directoryPath;
	}

	public static void resetExcel_File_Location() {
		EXCEL_FILE_LOCATION = EXCEL_FILE_LOCATION_HARDCODED;
	}

	public static void writeXLS(String fileName, String sheetName, int mode, String Title,
			ArrayList<LineReport> results, String... etc) {
		// BUG
		// Cannot have two Identical colors with different
		// size fonts, one color can only have one size font
		class styleBase {
			CellStyle style;
			@SuppressWarnings("unused")
			HSSFFont font;

			styleBase(CellStyle s, HSSFFont f) {
				style = s;
				font = f;
			}

			CellStyle getStyle() {
				return style;
			}
		}

		if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (!frame.Frame.getCheckbox_createExcel())) {
			return;
		}
		// mode=0 regular search sofiot considered
		// mode=1 regular search sofiot not considered
		// mode=2 dilugim search
		// mode=3 letters search
		// mode=3 gimatria search
		// mode=4 ReportTorahCount

		// etc - additional information
		// fileName - name of excel file, can have multiple sheets
		// sheetName - sheetName, depends on search settings
		// mode - explained above
		// Title - used as header inside sheet
		// searchSTR - the string searched for
		// results - depends on mode, will be described below, at the switch
		// boolPadding - says if the Padding was succesful for Dilugim
		HashMap<IndexedColors, styleBase> colorMap = new HashMap<IndexedColors, styleBase>();

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
		int countETC = 1;
		int countColumn = 1;
		for (int i = 0; i < Math.max(etc.length, 8); i++) {
			int length = 0;
			if (i < etc.length) {
				String e;
				e = etc[i];
				length = e.length();
				if (length > 0) {
					cell = row.createCell(countColumn++);
					cell.setCellStyle(styleTitle);
					cell.setCellValue(e);
				}
			}
			switch (i) {
			case 1:
				sheet.setColumnWidth(i, 1500 + Math.max(5000, (int) (length / 3) * (600)));
				break;
			default:
				sheet.setColumnWidth(i, 1500 + Math.max(5, (int) (length / 3)) * 600);
				break;
			}
			countETC++;
		}
		// }
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

		// mode = 0 regular search
		// multidimensional array used as one dimensional array shows result for each
		// word of search results
		// [0] = searchSTR
		// [1] = BookName
		// [2] = Perek
		// [3] = Pasuk
		// [4] = Torah of the Pasuk
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

		// do not create headers for mode=4 ReportTorahCount
		switch (mode) {
		case 0:
		case 1:
		case 2:
		case 3:
			for (String field : header) {
				if ((field == dilugWord) && (mode != 2)) {
					continue;
				}
				cell = row.createCell(colNum++);
				cell.setCellStyle(styleHeader);
				cell.setCellValue(field);
			}
		}
		index = 1;
		int lineIndex = -1;
		ArrayList<Integer> columnExpanded = new ArrayList<Integer>();
		for (LineReport lineReport : results) {
			ArrayList<String[]> resArray = lineReport.getLines();
			int resIndex = -1;
			lineIndex++;
			for (String[] res : resArray) {
				int rowExpanded=0;
				
				resIndex++;
				row = sheet.createRow(rowNum++);
				if ((mode != 4) && (resIndex == 0)) {
					cell = row.createCell(resIndex);
					cell.setCellStyle(style);
					cell.setCellValue(index++);
				}
				for (int i = 0; i < res.length; i++) {
					cell = row.createCell(i + 1);
					if (lineReport.getColumnColor(i) != null) {
						if (colorMap.containsKey(lineReport.getColumnColor(i))) {
							cell.setCellStyle(colorMap.get(lineReport.getColumnColor(i)).getStyle());
						} else {
							CellStyle cStyle = workbook.createCellStyle();
							HSSFFont fontX = workbook.createFont();
							fontX.setBold(true);
							cStyle.setWrapText(true);
							fontX.setColor(lineReport.getColumnColor(i).getIndex());
							if (lineReport.getColumnFontSize(i) != -1) {
								fontX.setFontHeightInPoints((short) lineReport.getColumnFontSize(i));
							}
							cStyle.setFont(fontX);
							colorMap.put(lineReport.getColumnColor(i), new styleBase(cStyle, fontX));
							cell.setCellStyle(cStyle);
						}
						// style.setFont(txtFontCustom);
					} else if (lineReport.getRowColor() != null) {
						if (colorMap.containsKey(lineReport.getRowColor())) {
							cell.setCellStyle(colorMap.get(lineReport.getRowColor()).getStyle());
						} else {
							CellStyle cStyle = workbook.createCellStyle();
							HSSFFont fontX = workbook.createFont();
							fontX.setBold(true);
							cStyle.setWrapText(true);
							fontX.setColor(lineReport.getRowColor().getIndex());
							if (lineReport.getRowFontSize() != -1) {
								fontX.setFontHeightInPoints((short) lineReport.getRowFontSize());
							}
							cStyle.setFont(fontX);
							colorMap.put(lineReport.getRowColor(), new styleBase(cStyle, fontX));
							cell.setCellStyle(cStyle);
						}
						// style.setFont(txtFontCustom);
					} else {
						// style.setFont(txtFont);
						cell.setCellStyle(style);
					}

					if ((mode != 4) && ((i == id_torahLine) && (mode != 2))
							|| ((i == id_dilugTorahLine) && (mode == 2))) {
						// find all occurences of searchSTR in Line and Color them
						int lastIndex = 0;
						HSSFRichTextString richString = new HSSFRichTextString(new String(res[i]));
						try {
							if (results.get(lineIndex).getIndexes(resIndex) == null) {
								cell.setCellValue(richString);
							} else {
								for (Integer[] thisIndex : results.get(lineIndex).getIndexes(resIndex)) {
									if (thisIndex[0] > 0) {
										richString.applyFont(lastIndex, thisIndex[0], txtFont);
									}
									richString.applyFont(thisIndex[0], thisIndex[1], fontDilug);
									lastIndex = thisIndex[1];
								}
								richString.applyFont(lastIndex, richString.length(), txtFont);
								cell.setCellValue(richString);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						// HSSFCell hssfCell = row.createCell();
						// rich text consists of two runs
						// cell.setCellValue(res[i - 1]);
					} else {
						while ((i+1)>=columnExpanded.size()) {
							columnExpanded.add(0);
						}
						cell.setCellValue(res[i]);
						int len;
						if ((len = res[i].length())>5) {
							if (rowExpanded<len) {
								rowExpanded = len;
								row.setHeight((short)(500+360*(int)(len/5)));
							}
							if (columnExpanded.get(i+1)<len) {
								columnExpanded.set(i+1, len);
								sheet.setColumnWidth(i+1, 3000+500*(int)(len/7));
							}
						}
					}
				}
			}
		}
		sheet.autoSizeColumn(0);

		for (int i = countETC; i <= 6; i++) {
			sheet.autoSizeColumn(i);
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(fileNameExtended);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	public static String[][] readBookTableXLS(String[] inputFiles, int sheetNUM, int startColumn, int startRow,
			int endColumn, int endRow) {
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

	/*
	 * public static void main(String[] args) { // writeXLS(new String[] { "Hello",
	 * "1", "2", "3" }); ArrayList<LineReport> arrLineReport = new
	 * ArrayList<LineReport>(); arrLineReport.add(new LineReport(new String[] { "1",
	 * "2" } , null)); arrLineReport.add(new LineReport(new String[] { "3", "4" } ,
	 * null)); writeXLS("בדיקה", "דוח", 0, "Title", arrLineReport, true); }
	 */
}
