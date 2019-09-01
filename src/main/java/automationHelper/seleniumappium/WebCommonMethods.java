/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


//import jxl.read.biff.BiffException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


// TODO: Auto-generated Javadoc
/**
 * The Class WebCommonMethods.
 */
public class WebCommonMethods{

	/** The driver. */
	public static WebDriver driver;	
	
	/**
	 * Instantiates a new web common methods.
	 *
	 * @param driver the driver
	 */
	public WebCommonMethods(WebDriver driver){
		this.driver=driver;
	}


	/** The sleep time min. */
	public static int sleepTimeMin;
	
	/** The sleep time min2. */
	public static int sleepTimeMin2;
	
	/** The sleep time average. */
	public static int sleepTimeAverage;
	
	/** The sleep time average2. */
	public static int sleepTimeAverage2;
	
	/** The sleep time max. */
	public static int sleepTimeMax;
	
	/** The sleep time max2. */
	public static int sleepTimeMax2;
	
	/** The robot. */
	public static Robot robot;

	
	
	
	
	
	/**
	 * Get text from the Alert window popup.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the string
	 * @throws Exception 
	 */
	public static String alertGetText() throws Exception{
		return driver.switchTo().alert().getText();
	}
	
	/**
	 * accept Alert window popup.
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception 
	 */
	public static void alertAccept() throws Exception{
		driver.switchTo().alert().accept();
	}

	/**
	 * dismiss Alert window popup.
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 */
	public static void alertDismiss(){
		driver.switchTo().alert().dismiss();
	}



	/**
	 * Wait seconds.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param seconds the seconds
	 */
	public static void waitSeconds(int seconds){
		WebDriverWait wait = new WebDriverWait(driver, seconds);
	}

	/**
	 * Waits for desired Seconds and then checks for the element to be Selected. 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 */
	public static void waitForElementToBeSelected(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeSelected(element));
	}

	/**
	 * Waits for desired Seconds for the element to be Clickable. 
	 * <br>Sometimes an element/button is inactive/greyed out and can't be interacted with it. So, it waits for specified seconds and checks again if element is clickable or not.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 */
	public static void waitForElementToBeClickable(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**
	 * Waits for desired seconds and then checks if the element is visible or not.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 */
	public static void waitingForTheElementToLoad(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	/**
	 * Wait for invisibility of web element in the page
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param locator the locator
	 */
	//Wait for invisibility of Web Element in the page
	public static void waitForInvisibilityOfWebElement(By locator){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	/**
	 * Wait for invisibility of web element.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param locator the locator
	 * @param text the text
	 */
	public static void waitForInvisibilityOfWebElement(By locator, String text){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	}
	
	/**
	 * Wait for visibility of all web elements in the page
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param webElement the web element
	 */
	public static void waitForVisibilityOfAllWebElements(List<WebElement> webElement){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfAllElements(webElement));
	}
	
	/**
	 * Wait for visibility of all web elements.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param locator the locator
	 */
	public static void waitForVisibilityOfAllWebElements(By locator){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * Wait for text to be present in web element.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param webElement the web element
	 * @param text the text
	 */
	//Wait for text to be present in the Web Element
	public static void waitForTextToBePresentInWebElement(WebElement webElement, String text){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
	}
	
	/**
	 * Wait for number of windows to be.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param numberOfWindows the number of windows
	 */
	public static void waitForNumberOfWindowsToBe(int numberOfWindows){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
	}
	
	/**
	 * Wait for presence of element to be located.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param locator the locator
	 */
	public static void waitForPresenceOfElementToBeLocated(By locator){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}


	/**
	 * Initialize all sleep timings variables. Method is used for Project internal purpose.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void initializeSleepTimings() throws IOException
	{
		FileReader reader = new FileReader("./config.properties"); //Reading configuration file
		Properties prop = new Properties();
		prop.load(reader);
		sleepTimeMin = Integer.parseInt(prop.getProperty("sleepTimeMin"));
		//System.out.println("sleepTimeMin:" + sleepTimeMin);
		sleepTimeMin2 = Integer.parseInt(prop.getProperty("sleepTimeMin2"));
		//System.out.println("sleepTimeMin2:" + sleepTimeMin2);
		sleepTimeAverage = Integer.parseInt(prop.getProperty("sleepTimeAverage"));
		//System.out.println("sleepTimeAverage:" + sleepTimeAverage);
		sleepTimeAverage2 = Integer.parseInt(prop.getProperty("sleepTimeAverage2"));
		//System.out.println("sleepTimeAverage2:" + sleepTimeAverage2);
		sleepTimeMax = Integer.parseInt(prop.getProperty("sleepTimeMax"));
		//System.out.println("sleepTimeMax:" + sleepTimeMax);
		sleepTimeMax2 = Integer.parseInt(prop.getProperty("sleepTimeMax2"));
		//System.out.println("sleepTimeMax2:" + sleepTimeMax2);
	}

	/**
	 * Webdriver implicit sleep. Waits for seconds specified in "implicitWaitTime" key from config.properties file
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void implicitSleep() throws Exception
	{
		String implicitWaitTime = FilesAndFolders.getPropValue("implicitWaitTime");
		
		int implicitWait = Integer.parseInt(implicitWaitTime);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	/**
	 * Checks if element is present on the current screen. Returns true/false if element is present/not present
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @throws Exception the exception
	 */
	public static boolean isElementPresent(WebElement element) throws Exception
	{
		try
		{
			element.isDisplayed();
			return true;
		}
		catch(NoSuchElementException e) //NoSuchElementException
		{
			System.out.println("Element is not dispalyed in the page");
			throw (e);
		}
	}

	/**
	 * Checks if element is present on the current screen. Returns true/false if element is present/not present
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param driver the WebDriver
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @return True, if is elements present. False, if not present
	 * @throws Exception the exception
	 */
	public static boolean isElementPresent(WebDriver driver, By by) throws Exception
	{
		try
		{
			return driver.findElements(by).size() > 0;
		}
		catch(Exception e) //NoSuchElementException
		{
			return false;
		}
	}

	
	/**
	 * Checks if attribute value is present and not null. Ex: 'a' tag has attribute "href", so method will check if "href" attribute value is present or not
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @param attribute the attribute
	 * @return true, if attribute value is present else false for null/empty
	 * @throws Exception the exception
	 */
	public static boolean isAttributeValuePresent(WebElement element, String attribute) throws Exception 
	{
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}
	

	/**
	 * Screenshot for Web Page.<br>
	 * Stored within "failure_Screenshot" folder with File name format: methodName_dd_MM_yyyy_hh_mm_ss.png<br>
	 * Ex: loginCheck_28_10_2015_07_10_10.png
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param methodName the method name
	 * @throws Exception the exception
	 */
	public static void screenshot(String methodName) throws Exception 
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");                   
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		String screenshotLocationWeb = FilesAndFolders.getPropValue("screenshotLocationWeb");
		try {
			FileUtils.copyFile(scrFile, new
					File((screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime())+".png")));
			Reporter.log("<a href='" +
					screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime()) + ".png'> <imgsrc='" + screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime()) + ".png' /> </a>");
			Reporter.setCurrentTestResult(null);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}



	/**
	 * Retrieve heading td value.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param excelCellTabNameHeadingsTdValue the excel cell tab name headings td value
	 * @param excelCellTabNameUniqueHashValue the excel cell tab name unique hash value
	 * @return the int
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*This method is helpful when there is a table created with N columns but the rows are created dynamically based on data.
	 * Example: suppose we wish to click on 'Edit' button (which is column 8 i.e, Html <Td> tab value is 8) for a username 'Yagnesh'.
	 * Now, username 'Yagnesh' could be on any row (ie, Html <tr> value could be anything as the row data is dynamic)
	 * This method aids to dynamically retrieve <tr> & <td> value for specific cell in a table structure. 
	 * Once it is obtained we may combine the xPath by using that dynamic <tr> & <td> value retrieved in order to click on 'Edit' button
	 */
	public static int RetrieveHeadingTdValue(String excelCellTabNameHeadingsTdValue, String excelCellTabNameUniqueHashValue) throws BiffException, IOException, Exception
	{
		//String orgHeading;
		String orgHeadingTdNumber;
		int headingTdValue=0;
		Cell[] orgTdValues = FilesAndFolders.readExcelNextRowOfUniqueValue("webTabsWithSubheading", excelCellTabNameUniqueHashValue);
		for(int i=1; i<=orgTdValues.length; i++)
		{
			if(orgTdValues[i].getContents().contains(excelCellTabNameHeadingsTdValue))
			{
				orgHeadingTdNumber = orgTdValues[i].getContents();
				orgHeadingTdNumber = orgHeadingTdNumber.replaceAll("[a-zA-Z ]", "");
				headingTdValue = Integer.parseInt(orgHeadingTdNumber);
				//System.out.println(orgTdValues[i].getContents() + " / " + orgHeadingTdNumber + " / " + headingTdValue);
				break;
			}
		}
		Assert.assertTrue(headingTdValue!=0, "Failed to retrieve <td> value of Heading...");		
		return headingTdValue;
	}

	/**
	 * Retrieve heading tr value.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param headingClassName the heading class name
	 * @param orgCodeValue the org code value
	 * @return the int
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int RetrieveHeadingTrValue(String headingClassName, String orgCodeValue) throws BiffException, IOException, Exception
	{	
		int headingTrValue=0; 
		String orgCodeListValue;

		List<WebElement> headingColumnValuesFromAllRowsList = driver.findElements(By.className(headingClassName));

		for (int i = 0; i < headingColumnValuesFromAllRowsList.size(); i++) 
		{
			System.out.println(headingColumnValuesFromAllRowsList.get(i).getText() + " / " + orgCodeValue);
			orgCodeListValue = headingColumnValuesFromAllRowsList.get(i).getText();
			if(orgCodeListValue.contains(orgCodeValue))
			{
				headingTrValue=i+1;
				break;
			}
		}
		Assert.assertTrue(headingTrValue!=0, "Failed to retrieve <tr> value of Heading...");
		return headingTrValue;
	}

	/**
	 * Select dropdown by Text value.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @param value the value
	 * @throws Exception 
	 */
	public static void selectDropdownByText(WebElement element, String value) throws Exception
	{
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(value);
	}

	/**
	 * Gets the dropdown items.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @return the dropdown items
	 * @throws Exception 
	 */
	public static List<WebElement> getDropdownItems(WebElement element) throws Exception
	{
		Select select = new Select(element);
		return select.getOptions();
	}

	/**
	 * Gets the selected dropdown item.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @return the selected dropdown item
	 * @throws Exception 
	 */
	public static String getSelectedDropdownItem(WebElement element) throws Exception
	{
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Deselect all items(if any) from drop down.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @throws Exception 
	 */
	public static void deselectDropDown(WebElement element) throws Exception
	{
		int size=new Select(element).getOptions().size();
		for(int i=0;i<size;i++){
			new Select(element).deselectByIndex(i);
		}
	}

	/**
	 * Select dropdown item boot strap.
	 * <br>Sometimes "select" tag is not used for drop-down menu. In such cases, this method assist's in selecting item from drop-down which is in the for of list("li") under "ul"/"ol" tags in view-source.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param dropdown the dropdown
	 * @param dropdownList the dropdown list
	 * @param commonLocatorWithinLiTagForAllItems the common locator within li tag for all items. Ex: By.className("active-result")
	 * @param itemName the item name
	 * @throws Exception the exception
	 */
	public static void selectDropdownItemBootStrap(WebElement dropdown, WebElement dropdownList, By commonLocatorWithinLiTagForAllItems, String itemName) throws Exception
	{
		Thread.sleep(WebCommonMethods.sleepTimeMin2);
		dropdown.click();
		Thread.sleep(WebCommonMethods.sleepTimeMin2);
		int flag=0;		

		WebCommonMethods.implicitSleep();
		List<WebElement> dropdownItemList = dropdownList.findElements(commonLocatorWithinLiTagForAllItems);
		flag=0;
		for (WebElement dropdownItem : dropdownItemList) 
		{	
			System.out.println(dropdownItem.getText());
			if (dropdownItem.getText().equals(itemName)) 
			{
				dropdownItem.click();
				Thread.sleep(WebCommonMethods.sleepTimeMin2);
				flag=1;
				break;
			}
		}
		Assert.assertTrue(flag==1, "List Item :  '" + itemName + "' :  passed as parameter is a mismatch with values available in drop-down...");
	}


	/**
	 * Select dropdown item boot strap.
	 * <br>Sometimes "select" tag is not used for drop-down menu. In such cases, this method assist's in selecting item from drop-down which is in the for of list("li") under "ul"/"ol" tags in view-source.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param dropdown the dropdown
	 * @param itemName the item name
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 * @throws AWTException the AWT exception
	 * @throws Exception the exception
	 */
	public static void selectDropdownItemBootStrap(WebElement dropdown, String itemName) throws IOException, InterruptedException, AWTException, Exception
	{
		Thread.sleep(WebCommonMethods.sleepTimeMin);
		dropdown.click();
		Thread.sleep(WebCommonMethods.sleepTimeMin);
		//dropdown.findElement(By.xpath("../div/ul/li[text()='"+itemName+"']")).click();
		dropdown.findElement(By.xpath("../ul/li[text()='"+itemName+"']")).click();

		System.out.println("Clicked on "+  itemName);
	}

	/**
	 * Check if item is not present in dropdown items.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param dropdown the dropdown
	 * @param dropdownList the dropdown list
	 * @param commonLocatorWithinLiTagForAllItems the common locator within "li" tag for all items. Ex: By.className("active-result")
	 * @param itemName the item name
	 * @throws Exception the exception
	 */
	public static void checkItemNotPresentInDropdownItems(WebElement dropdown, WebElement dropdownList, By commonLocatorWithinLiTagForAllItems, String itemName) throws Exception
	{
		dropdown.click();
		int flag=0;		

		WebCommonMethods.implicitSleep();
		List<WebElement> dropdownItemList = dropdownList.findElements(commonLocatorWithinLiTagForAllItems);
		flag=0;
		for (WebElement dropdownItem : dropdownItemList) 
		{	
			if (dropdownItem.getText().equals(itemName)) 
			{
				flag=1;
			}

		}
		Assert.assertTrue(flag==0, "List Item :  '" + itemName + "' :  passed as parameter is PRESENT with values available in  drop-down");
		System.out.println("List item"  + itemName + ": passed as parameter is NOT PRESENT in dropdown items " );
	}

	/**
	 * Sets the check box.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param element the webelement
	 * @param status the status. Ex: "check" or "uncheck"
	 * @throws Exception the exception
	 */
	public static void setCheckBox(WebElement element, String status) throws Exception
	{
		if (status.equalsIgnoreCase("uncheck")){
			if(element.isSelected()){
				//waitForElementToBeSelected(element);
				element.click();
				System.out.println("CheckBox Disabled");
			}
			else{
				System.out.println("CheckBox is already disabled");
			}
		}
		else if(status.equalsIgnoreCase("Check")){
			if(element.isSelected()){
				System.out.println("CheckBox is already Enabled");
			}
			else{
				//waitForElementToBeSelected(element);
				element.click();
				System.out.println("CheckBox Enabled");
			}
		}
		else{
			Reporter.log("Invalid Status Entry, The status value should either be 'Check' or 'Uncheck'",true);
		}
	}

	/**
	 * Sets the radio button.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param element the WebElement
	 * @param status the status. Ex: "Select" or "Deselect"
	 * @throws Exception the exception
	 */
	public static void setRadioButton(WebElement element, String status) throws Exception
	{
		if (status.equalsIgnoreCase("deselect")){
			if(element.isSelected()){
				element.click();
				System.out.println("RadioButton Disabled");
			}
			else{
				System.out.println("RadioButton is already Disabled");
			}
		}
		else if(status.equalsIgnoreCase("select")){
			if(element.isSelected()){
				System.out.println("RadioButton is already Enabled");
			}
			else{
				element.click();
				System.out.println("RadioButton Enabled");
			}
		}
		else{
			System.out.println("Invalid Status Entry, The status value should either be 'Select' or 'Deselect'");
		}
	}

	/**
	 * Toggle check box. If check box is already enabled then it is disabled and vise versa.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param element the element
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public static Boolean toggleCheckBox(WebElement element) throws Exception
	{
		if(element.isSelected()){
			element.click();
			System.out.println("CheckBox Disabled");
			return false;
		}
		else{
			element.click();
			System.out.println("CheckBox Enabled");
			return true;
		}
	}

	/**
	 * Sets profile preference for Firefox browser to handle download file via OS window popup 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @param downloadDirectory the download directory. Ex for Windows:"e:\\SampleExcel", for Linux:"/e/SampleExcel/"
	 * @throws Exception the exception
	 */
	public static void downloadPopupPreferenceForFirefox(String downloadDirectory) throws Exception
	{
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk" , "application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;"); 
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting",false);
		profile.setPreference("browser.download.folderList", 2); 
		profile.setPreference("browser.download.dir",downloadDirectory);
	}


//	/**
//	 * Robots handling.
//	 */
//	public static void robotsHandling()
//	{
//		//		robot = new Robot();
//		//		StringSelection ss = new StringSelection(itemName);
//		//		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
//		//		dropdown.click();
//		//		dropdown.sendKeys(itemName);
//
//		//		robot.keyPress(KeyEvent.VK_CONTROL);
//		//		robot.keyPress(KeyEvent.VK_V);
//		//		robot.keyRelease(KeyEvent.VK_V);
//		//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		//				Thread.sleep(1000);
//		//				dropdown.sendKeys(Keys.ENTER);
//
//		//		robot.keyPress(KeyEvent.VK_ENTER);
//		//		robot.keyRelease(KeyEvent.VK_ENTER);
//		System.out.println("Clicked on ");
//	}

	
	

}