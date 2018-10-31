package ToraApp;

import org.apache.poi.hssf.usermodel.HSSFFont;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExcelFunctions {

	public static String[][] readXLS(String inputFile, int sheetNUM, int X, int Y, int posX, int posY)
			throws IOException {
		String[][] data = null;
		Workbook workbook = null;
		DataFormatter dataFormatter = new DataFormatter();
		try {
			FileInputStream excelFile = new FileInputStream(new File(inputFile));
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
			Output.printText("Imported XLS", 1);
		} catch (FileNotFoundException e) {
			Output.printText("Error importing from EXCEL Sheet", 1);
			e.printStackTrace();
		} catch (IOException e) {
			Output.printText("Error importing from EXCEL Sheet", 1);
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return data;
	}
// End Excel Tables

	// private static final String FILE_NAME = "./src/MyFirstExcel.xlsx";
	private static final String EXCEL_FILE_LOCATION = "./Reports/";
	private static final String EXCEL_FILE_EXTENSION = ".xls";

	public static void writeXLS(String fileName, String sheetName, int mode, String Title, String searchSTR,
			ArrayList<String[][]> results) {
		// mode=0 regular search
		// mode=1 dilugim search
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		File file=null;
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
		if(sheet != null)   {
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
		System.out.println("Creating excel");

		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(0);
		// row.setRowStyle(style);
		row.setHeightInPoints(60);
		cell.setCellStyle(styleTitle);// Apply style to cell
		cell.setCellValue(Title);
		rowNum++;
		int colNum = 0;
		row = sheet.createRow(rowNum++);
		row.setRowStyle(styleHeader);
		for (String field : header) {
			if ((mode != 1) && (field == dilugWord)) {
				continue;
			}
			cell = row.createCell(colNum++);
			cell.setCellStyle(style);
			cell.setCellValue(field);
		}

		int index = 1;

		for (String[][] resArr : results) {
			// if ((mode==1) && (counter++==0)) { // The Dilugim Mode needs to skip first
			// array
			// continue;
			// }
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(index++);
			switch (mode) {
			case 1:
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(resArr[0][0]);
				cell = row.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue(resArr[0][1]);
				break;
			}

			int counter = 0;
			for (String[] res : resArr) {
				if ((counter++ == 0) && (mode == 1)) {
					continue;
				}
				if (mode == 1) {
					row = sheet.createRow(rowNum++);
				}
				for (int i = 1; i <= res.length; i++) {
					cell = row.createCell(i + ((mode == 1) ? 1 : 0));
					cell.setCellStyle(style);
					cell.setCellValue(res[i - 1]);
				}
			}
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

		writeXLS("בדיקה", "דוח", 0, "Title", "searchSTR", new ArrayList<String[][]>(
				Arrays.asList(new String[][] { { "1", "2" } }, new String[][] { { "3", "4" } })));

	}

}
