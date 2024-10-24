package CommonFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class FunctionLibrary{
	public static WebDriver driver;
	public static Properties p;
//	Open Browser
	public static WebDriver openBrowser() throws Throwable {
		p = new Properties();
		p.load(new FileInputStream("ObjectRepository/OR.properties"));
		if(p.getProperty("Browser").equalsIgnoreCase("chrome")) {
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		else if(p.getProperty("Browser").equalsIgnoreCase("edge")) {
			
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		else if(p.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		else {
			Reporter.log("Invalid Browser", true);
		}
		
		return driver;
	}
	
//	Enter url
	public static void launchUrl() {
		driver.get(p.getProperty("url"));
	}
	
	
//	Wait for Web Element to display on page
	public static void waitForElement(String locatorType, String locatorValue, String testData) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(testData)));
		if(locatorType.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
		}
		else {
			Reporter.log("Invalid LocatorType",true);
		}
	}
	
//	type Text into TextBox
	public static void typeAction(String locatorType, String locatorValue, String testData) {
		if(locatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(testData);
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).clear();
			driver.findElement(By.name(locatorValue)).sendKeys(testData);
		}
		else if(locatorType.equalsIgnoreCase("className")) {
			driver.findElement(By.className(locatorValue)).clear();
			driver.findElement(By.className(locatorValue)).sendKeys(testData);
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).clear();
			driver.findElement(By.xpath(locatorValue)).sendKeys(testData);
		}
		else {
			Reporter.log("Invalid Locator Type", true);
		}
	}
	
//	Click Web Element
	public static void clickAction(String locatorType, String locatorValue) {
		if(locatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorValue)).click();
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).click();
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).click();
		}
		else if(locatorType.equalsIgnoreCase("linktext")) {
			driver.findElement(By.linkText(locatorValue)).click();
		}
		else {
			Reporter.log("invalid Locator type", true);
		}
		
	}
	
//	Move to CLick web Element
	public static void MoveToClick(String locatorType, String locatorValue) {
		Actions act = new Actions(driver);
		if(locatorType.equalsIgnoreCase("className")) {
			act.moveToElement(driver.findElement(By.className(locatorValue))).click();
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			act.moveToElement(driver.findElement(By.name(locatorValue))).click();
			act.build();
			act.perform();
		}
		else if(locatorType.equalsIgnoreCase("id")) {
			act.moveToElement(driver.findElement(By.id(locatorValue))).click();
			act.build().perform();
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			act.moveToElement(driver.findElement(By.xpath(locatorValue))).click().build().perform();
		}
	}
	
//	Validate Page
	public static void validatePage(String testData) {
		String pageTitle=driver.getTitle();
		if(pageTitle.contains(testData)) {
			Reporter.log("Vaild Page",true);
		}
		else {
			Reporter.log("Invalid Page");
		}
	}
	
//	Select item from drop down list
	public static void selectAction(String locatorType, String locatorValue, String testData) {
		Select slt;
		if(locatorType.equalsIgnoreCase("id")) {
			slt = new Select(driver.findElement(By.id(locatorValue)));
			slt.selectByVisibleText(testData);
		}
		else if(locatorType.equalsIgnoreCase("name")) {
			slt = new Select(driver.findElement(By.name(locatorValue)));
			slt.selectByVisibleText(testData);
		}
		else if(locatorType.equalsIgnoreCase("xpath")) {
			slt = new Select(driver.findElement(By.xpath(locatorValue)));
			slt.selectByVisibleText(testData);
		}
		else {
			Reporter.log("Invalid Locator Type", true);
		}
	}
	
//	Select Item From Table
	public static void SelectItemFromTable(String locatorType, String locatorValue, String testData) throws Throwable 
	{
		WebElement ftable;
		WebElement ftableBody;
		List<WebElement> trows;
		List<WebElement> tcols;
		String fname;
		try 
		{
			ftable=driver.findElement(By.xpath(locatorValue));
			ftableBody=ftable.findElement(By.tagName("tbody"));
			trows=ftableBody.findElements(By.tagName("tr"));
			for(int i=1; i<=trows.size(); i++) {
				tcols=trows.get(i).findElements(By.tagName("td"));
				fname=tcols.get(0).getText();
				if(fname.equalsIgnoreCase(testData)) {
					tcols.get(8).click();
					Thread.sleep(3000);
					break;
				}
			}
				
		} 
		
		catch (StaleElementReferenceException e) 
		{
			System.out.println(e.getMessage());
			ftable=driver.findElement(By.xpath(locatorValue));
			ftableBody=ftable.findElement(By.tagName("tbody"));
			trows=ftableBody.findElements(By.tagName("tr"));
			for(int i=1; i<=trows.size(); i++) {
				tcols=trows.get(i).findElements(By.tagName("td"));
				fname=tcols.get(0).getText();
				if(fname.equalsIgnoreCase(testData)) {
					tcols.get(8).click();
					Thread.sleep(3000);
					break;
				}
			}
		}
	
	}
	
//	Delete Item from Table
	public static void DeleteItemFromTable(String locatorType, String locatorValue, String testData) throws Throwable{
		WebDriverWait wait;
		WebElement ftable;
		WebElement ftableBody;
		List<WebElement> trows;
		List<WebElement> tcols;
		String orderId;
		ftable=driver.findElement(By.xpath(locatorValue));
		ftableBody=ftable.findElement(By.tagName("tbody"));
		trows=ftableBody.findElements(By.tagName("tr"));
		for(int i=1; i<=trows.size(); i++) {
			tcols=trows.get(i).findElements(By.tagName("td"));
			orderId=tcols.get(0).getText();
			if(orderId.equalsIgnoreCase(testData)) {
				WebElement del = tcols.get(9).findElement(By.linkText("Delete"));
				del.click();
				wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.alertIsPresent());
				driver.switchTo().alert().accept();
				Thread.sleep(3000);
				break;
			}
		}
	}
	
	
//	Take Screen Shot
	public static void screenShot(String screenShotName) throws Throwable {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File trg = new File("test-output/ScreenShotsFile/"+screenShotName+"."+"png");
		FileUtils.copyFile(src, trg);
	}
	
//	Click Radio Button
	public static void clickRadioButton(String locatorValue, String testData) {
		if(testData.equalsIgnoreCase("First Class")) {
			driver.findElement(By.xpath(locatorValue)).click();
		}
		else if(testData.equalsIgnoreCase("Business")) {
			driver.findElement(By.xpath(locatorValue)).click();
		}
		else if(testData.equalsIgnoreCase("Economy")){
			driver.findElement(By.xpath(locatorValue)).click();
		}
		else {
			System.out.println("Invalid Test Data");
		}
	}
	
//	Close Browser
	public static void closeBrowser() {
		driver.quit();
	}
	
//	Refresh Page
	public static void refreshPage() {
		driver.navigate().refresh();
	}
}

