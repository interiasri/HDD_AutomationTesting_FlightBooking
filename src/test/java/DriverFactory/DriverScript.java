package DriverFactory;

import CommonFunctions.FunctionLibrary;
import Utilities.ExcelFileUtils;

public class DriverScript{
	ExcelFileUtils xl;
	public boolean status=false;
	public boolean testResultStatus = false;
	
	public void kickStart() throws Throwable {
		xl = new ExcelFileUtils(ExcelFileUtils.excelInputPath);
		String sheetName=ExcelFileUtils.wb.getSheetAt(0).getSheetName();
		System.out.println("SheetName: "+ sheetName);
		for(int i=1; i<=xl.rowCount(sheetName); i++) {
			String TCmodule=xl.getCellData(sheetName, i, 1);
			String testRunStatus=xl.getCellData(sheetName, i, 2);
			if(testRunStatus.equalsIgnoreCase("y")) 
			{
				for(int j=1; j<=xl.rowCount(TCmodule); j++) {
					String objType=xl.getCellData(TCmodule, j, 2);
					String locatorType=xl.getCellData(TCmodule, j, 3);
					String locatorValue=xl.getCellData(TCmodule, j, 4);
					String testData=xl.getCellData(TCmodule, j, 5);
					try 
					{
						if(objType.equalsIgnoreCase("openBrowser")) {
							FunctionLibrary.openBrowser();
						}
						if(objType.equalsIgnoreCase("launchUrl")) {
							FunctionLibrary.launchUrl();
						}
						if(objType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(locatorType, locatorValue, testData);
						}
						if(objType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(locatorType, locatorValue, testData);
						}
						if(objType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(locatorType, locatorValue);
						}
						if(objType.equalsIgnoreCase("MoveToClick")) {
							FunctionLibrary.MoveToClick(locatorType, locatorValue);
						}
						if(objType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
						}
						if(objType.equalsIgnoreCase("validatePage")) {
							FunctionLibrary.validatePage(testData);
						}
						if(objType.equalsIgnoreCase("selectAction")) {
							FunctionLibrary.selectAction(locatorType, locatorValue, testData);
						}
						if(objType.equalsIgnoreCase("SelectItemFromTable")) {
							FunctionLibrary.SelectItemFromTable(locatorType, locatorValue, testData);
						}
						if(objType.equalsIgnoreCase("screenShot")) {
							FunctionLibrary.screenShot(testData);
						}
						if(objType.equalsIgnoreCase("refreshPage")) {
							FunctionLibrary.refreshPage();
						}
						if(objType.equalsIgnoreCase("DeleteItemFromTable")) {
							FunctionLibrary.DeleteItemFromTable(locatorType, locatorValue, testData);
						}
						status=true;
					}
					
					catch (Exception e) 
					{
						System.out.println(e.getMessage());
						status=false;
					}
					if(status==true) {
						xl.setCellData(TCmodule, j, 6, "Pass", ExcelFileUtils.excelOutputPath);
					}
					else {
						xl.setCellData(TCmodule, j, 6, "Fail", ExcelFileUtils.excelOutputPath);
					}
				}
				testResultStatus=true;
				if(status==true && testResultStatus==true) {
					xl.setCellData(sheetName, i, 3, "Pass", ExcelFileUtils.excelOutputPath);
				}
				else {
					xl.setCellData(sheetName, i, 3, "Fail", ExcelFileUtils.excelOutputPath);
				}
			}
			
			else 
			{
				xl.setCellData(sheetName, i, 3, "Blocked", ExcelFileUtils.excelOutputPath);
			}
		}
	}
}
