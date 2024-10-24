package DriverFactory;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utilities.ExcelFileUtils;

public class DriverScript{
	ExcelFileUtils xl;
	public boolean status=false;
	public boolean testResultStatus = false;
	ExtentReports report;
	ExtentTest logger;
	
	public void kickStart() throws Throwable {
		xl = new ExcelFileUtils(ExcelFileUtils.excelInputPath);
		String sheetName=ExcelFileUtils.wb.getSheetAt(0).getSheetName();
		System.out.println("SheetName: "+ sheetName);
		for(int i=1; i<=xl.rowCount(sheetName); i++) {
			String TCmodule=xl.getCellData(sheetName, i, 1);
			report = new ExtentReports("target/HTML Reports/"+TCmodule+".html");
			logger = report.startTest(TCmodule);
			logger.assignAuthor("Sridhar");
			String testRunStatus=xl.getCellData(sheetName, i, 2);
			if(testRunStatus.equalsIgnoreCase("y")) 
			{
				for(int j=1; j<=xl.rowCount(TCmodule); j++) {
					String objDescription=xl.getCellData(TCmodule, j, 1);
					String objType=xl.getCellData(TCmodule, j, 2);
					String locatorType=xl.getCellData(TCmodule, j, 3);
					String locatorValue=xl.getCellData(TCmodule, j, 4);
					String testData=xl.getCellData(TCmodule, j, 5);
					try 
					{
						if(objType.equalsIgnoreCase("openBrowser")) {
							FunctionLibrary.openBrowser();
							logger.log(LogStatus.INFO, objDescription);
							
						}
						if(objType.equalsIgnoreCase("launchUrl")) {
							FunctionLibrary.launchUrl();
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(locatorType, locatorValue);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("MoveToClick")) {
							FunctionLibrary.MoveToClick(locatorType, locatorValue);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("validatePage")) {
							FunctionLibrary.validatePage(testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("selectAction")) {
							FunctionLibrary.selectAction(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("SelectItemFromTable")) {
							FunctionLibrary.SelectItemFromTable(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("screenShot")) {
							FunctionLibrary.screenShot(testData);
							logger.log(LogStatus.INFO, objDescription);
						}
						if(objType.equalsIgnoreCase("DeleteItemFromTable")) {
							FunctionLibrary.DeleteItemFromTable(locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, objDescription);
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
						logger.log(LogStatus.PASS, objDescription);
					}
					else {
						xl.setCellData(TCmodule, j, 6, "Fail", ExcelFileUtils.excelOutputPath);
						logger.log(LogStatus.FAIL, objDescription);
					}
					report.endTest(logger);
					report.flush();
					
				}
				testResultStatus=true;
				if(status==true && testResultStatus==true) {
					xl.setCellData(sheetName, i, 3, "Pass", ExcelFileUtils.excelOutputPath);
					logger.log(LogStatus.PASS, TCmodule);
				}
				else {
					xl.setCellData(sheetName, i, 3, "Fail", ExcelFileUtils.excelOutputPath);
					logger.log(LogStatus.FAIL, TCmodule);
				}
				
			}
			else 
			{
				xl.setCellData(sheetName, i, 3, "Blocked", ExcelFileUtils.excelOutputPath);
				logger.log(LogStatus.SKIP, TCmodule);
			}
			
		}
		
	}
}
