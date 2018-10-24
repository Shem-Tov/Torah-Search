package ToraApp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
//import jxl.write.Number;

public class ExcelTables {
	// Excel Tables
	public static String[][] readXLS(String inputFile, int sheetNUM, int X, int Y, int posX, int posY)
			throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w = null;
		String[][] data = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet

			Sheet sheet = w.getSheet(sheetNUM);
			data = new String[sheet.getColumns()][sheet.getRows()];
			// Loop over first 10 column and lines
			// System.out.println(sheet.getColumns() + " " +sheet.getRows());
			for (int j = X; j < (posX + X); j++) {
				for (int i = Y; i < (posY + Y); i++) {
					Cell cell = sheet.getCell(j, i);
					data[j][i] = cell.getContents();
					// System.out.println(cell.getContents());
				}
			}
			Output.printText("Imported XLS",1);
			/*
			 * for (int j = 0; j < data.length; j++) { for (int i = 0; i <data[j].length;
			 * i++) {
			 * 
			 * System.out.println(data[j][i]); } }
			 */

		} catch (BiffException e) {
			Output.printText("Error importing from EXCEL Sheet",1);
			e.printStackTrace();
		} finally {
			if (w != null) {
				w.close();
			}
		}
		return data;
	}
// End Excel Tables


    private static final String EXCEL_FILE_LOCATION = "./src/";
    private static final String EXCEL_FILE_EXTENSION = ".xls";
    
    public static void writeXLS(String fileName, String Title, String searchSTR, ArrayList<String[]> results) {

        //1. Create an Excel file
        WritableWorkbook myFirstWbook = null;
        try {

            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION+fileName+EXCEL_FILE_EXTENSION));

            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("דוח", 0);
            Label label = new Label(0,0,Title);
            excelSheet.addCell(label);
            label = new Label(1,0,searchSTR);
            excelSheet.addCell(label);
            
            label = new Label(0,2,"נמצא");
            excelSheet.addCell(label);
            label = new Label(1,2,"ספר");
            excelSheet.addCell(label);
            label = new Label(2,2,"פרק");
            excelSheet.addCell(label);
            label = new Label(3,2,"פסוק");
            excelSheet.addCell(label);
            label = new Label(4,2,"תורה");
            excelSheet.addCell(label);         
            
            int row=3;
            for (String[] res:results) {
            	for (int i=0; i < res.length; i++) {
            		label = new Label(i, row, res[i]);
            		excelSheet.addCell(label);
                	}
            	row++;
            }
            /*
            // add something into the Excel sheet
            Label label = new Label(0, 0, "Test Count");
            excelSheet.addCell(label);

            Number number = new Number(0, 1, 1);
            excelSheet.addCell(number);

            label = new Label(1, 0, "Result");
            excelSheet.addCell(label);

            label = new Label(1, 1, "Passed");
            excelSheet.addCell(label);

            number = new Number(0, 2, 2);
            excelSheet.addCell(number);

            label = new Label(1, 2, "Passed 2");
            excelSheet.addCell(label);
            */

            myFirstWbook.write();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }


        }

    }
    
    public static void main(String[] args) {
    	writeXLS("בדיקה","Title", "searchSTR",new ArrayList<String[]> 
    	(Arrays.asList(
    		new String[] {"1","2"},
    		new String[] {"3","4"})));
    	
    }

}

