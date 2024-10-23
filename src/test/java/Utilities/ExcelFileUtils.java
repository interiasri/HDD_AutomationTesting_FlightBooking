package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
	
	public static String excelInputPath = "FileInput/DataEngine.xlsx";
	public static String excelOutputPath= "FileOutput/DataEngine_Results.xlsx";
	public static FileInputStream fi;
	public static Workbook wb;
	
	public ExcelFileUtils(String excelPath) throws Throwable {
		fi = new FileInputStream(excelPath);
		wb = WorkbookFactory.create(fi);
	}
	
//	Count number of rows in sheet
	public int rowCount(String sheetName) {
		return wb.getSheet(sheetName).getLastRowNum();
	}

//	Read Data From Cell
	public String getCellData(String sheetName, int row, int col) {
		String data;
		if(wb.getSheet(sheetName).getRow(row).getCell(col).getCellType()==CellType.NUMERIC) {
			int val=(int)wb.getSheet(sheetName).getRow(row).getCell(col).getNumericCellValue();
			data=String.valueOf(val);
		}
		else {
			data=wb.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
		}
		return data;
	}
	
//	Write Data into Cell
	public void setCellData(String sheetName, int row, int col, String status, String ExcelResultsPath) throws Throwable {
		wb.getSheet(sheetName).getRow(row).createCell(col).setCellValue(status);
		if(status.equalsIgnoreCase("Pass")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(sheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Fail")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(sheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		else {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(sheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		
		FileOutputStream fo = new FileOutputStream(ExcelResultsPath);
		wb.write(fo);
	}
	
//	public static void main(String[] args) throws Throwable {
//		ExcelFileUtils xl = new ExcelFileUtils(excelInputPath);
//		int rowCount=xl.rowCount(wb.getSheetAt(0).getSheetName());
//		System.out.println("Number of rows is sheet: "+rowCount);
//		for(int i=1; i<=rowCount; i++) {
//			String testName=xl.getCellData(wb.getSheetAt(0).getSheetName(), i, 1);
//			String testExecutionStatus = xl.getCellData(wb.getSheetAt(0).getSheetName(), i, 2);
//			System.out.println(testName+": "+testExecutionStatus);
//			xl.setCellData(wb.getSheetAt(0).getSheetName(), i, 3, "Blocked", excelOutputPath);
//		}
//	}

}
