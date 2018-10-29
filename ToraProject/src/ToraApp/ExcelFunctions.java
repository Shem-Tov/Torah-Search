package ToraApp;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
			data = new String[posX-X][posY-Y];
			int i = 0;
			int j = 0;
			Row currentRow;
			while (j<Y) {
				  currentRow = iterator.next();
				  j++;
			} 
			while (iterator.hasNext()) {
				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				i=X;
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					data[i++-X][(j)-Y] = dataFormatter.formatCellValue(currentCell);
					if (i >= (posX)) { break;}
				}
				j++;
				if (j >= (posY)) { break;}
			}
			Output.printText("Imported XLS", false);
		} catch (FileNotFoundException e) {
			Output.printText("Error importing from EXCEL Sheet", false);
			e.printStackTrace();
		} catch (IOException e) {
			Output.printText("Error importing from EXCEL Sheet", false);
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

	public static void writeXLS(String fileName, String Title, String searchSTR, ArrayList<String[]> results) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("דוח");
		XSSFFont txtFont = workbook.createFont();
		XSSFFont txtFontTitle = workbook.createFont();
		XSSFFont txtFontHeader = workbook.createFont();
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
		org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCol cTCol = ((XSSFSheet) sheet).getCTWorksheet()
				.getColsArray(0).addNewCol();
		cTCol.setMin(1);
		cTCol.setMax(16384);
		cTCol.setWidth(12.7109375);
		cTCol.setStyle(style.getIndex());

		sheet.setDefaultRowHeightInPoints(36);

		// CellStyle fontStyle = workbook.createCellStyle();
		// fontStyle.setFont(txtFont);
		sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
		CellStyle styleTitle = workbook.createCellStyle(); // Create new style
		styleTitle.setWrapText(true); // Set wordwrap
		styleTitle.setFont(txtFontTitle);
		CellStyle styleHeader = workbook.createCellStyle(); // Create new style
		styleHeader.setFont(txtFontHeader);

		String[] header = { "אינדקס", "נמצא", "ספר", "פרק", "פסוק", "תורה" };

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
			cell = row.createCell(colNum++);
			cell.setCellValue(field);
		}
		row = sheet.createRow(rowNum++);
		int index = 1;
		for (String[] res : results) {
			cell = row.createCell(0);
			cell.setCellValue(index++);
			for (int i = 1; i <= res.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(res[i - 1]);
			}
			row = sheet.createRow(rowNum++);
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(5);

		try {
			new File(EXCEL_FILE_LOCATION).mkdirs();
			FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_LOCATION + fileName + EXCEL_FILE_EXTENSION);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	public static void main(String[] args) {
		// writeXLS(new String[] { "Hello", "1", "2", "3" });

		writeXLS("בדיקה", "Title", "searchSTR",
				new ArrayList<String[]>(Arrays.asList(new String[] { "1", "2" }, new String[] { "3", "4" })));

	}

}
