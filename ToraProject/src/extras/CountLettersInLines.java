package extras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import torahApp.ToraApp;

public class CountLettersInLines {

	public static ArrayList<Integer[]> countLetters() {
		ArrayList<Integer[]> letterCount = new ArrayList<Integer[]>();
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(ToraApp.ToraLineFile));
			int c;
			int counter = 0;
			int lineCounter = 0;
			while ((c = inputStream.read()) != -1) {
				if ((c == 32) || (c == 10)) {
					if (c == 10) {
						lineCounter++;
						letterCount.add(new Integer[] { lineCounter, counter });
						counter = 0;
					}
					continue;
				}
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return letterCount;
	}

	private static final String EXCEL_FILE_LOCATION = "./Tables/";
	private static final String EXCEL_FILE_EXTENSION = ".xls";

	public static void writeXLS(String fileName, ArrayList<Integer[]> results) {

		String sheetName = "countLettersInLines";
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
		int rowNum = 0;
		System.out.println("Creating excel "+fileNameExtended+" : Sheet - "+ sheetName);

		Row row = null;
		Cell cell = null;

		for (Integer[] c : results) {
			row = sheet.createRow(rowNum++);
			for (int i = 0; i < c.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(c[i]);
			}
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

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
		writeXLS("TableCountLetter", countLetters());
	}
}
