package com.hp.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.jacob.com.LibraryLoader;

/**
 * ClassName: SeleniumCore 
 * Function: common function we had wrapped the selenium function .
 * Reason: use the function easily(optional). 
 * date: Aug 18, 2013 2:36:42 PM 
 *
 * @author huchan
 * @version 1.0
 * @since JDK 1.6
 */

/**
 * @author huchan
 *
 */
public class SeleniumCore {

	private static Logger logger = Logger.getLogger(SeleniumCore.class);
	//public WebDriver driver;
    private static String REPORT_DIR=getProjectWorkspace()+"reporter";
	/**
	 *  @author huchan
	 * Open a new URL from the browser
	 * @param driver --the webdriver instance
	 * @param url --  the url you want to open
	 *
	 */
	public static void OpenURL(WebDriver driver, String url) {
		logger.info("Open A New URL:" + url + " in the browser......");
		try{
		driver.get(url);
		}
		catch(NullPointerException e){
			logger.error("Sorry the URL:"+url+" cannot be opened now..");
			Assert.fail("Cannot open the URL:"+url+",...");
		}
	}

	/**
	 * click an element in the page 
	 * @param driver TODO
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void clickElement(WebDriver driver, WebElement e) {
		logger.info("Click elements in page-clicked this element:"
				+ e.getTagName() + ",the text is:" + e.getText());
		//In chrome browser this function didn't work ,so give a solution to load the page correctly
	//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		String code=getInnerHtmlCode(driver, e);
		logger.info("Clicked element html code is:"+code);
		e.click();
	}
	
	/**
	 * click an element in the page 
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void clickElementViaJs(WebDriver driver,WebElement e) {
		logger.info("Click elements in page-clicked this element:"
				+ e.getTagName() + ",the text is:" + e.getText());
		//In chrome browser this function didn't work ,so give a solution to load the page correctly
	//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		  executeJS(driver, "arguments[0].click();", e); 
		//"return arguments[0].fireEvent('onclick');",
		 String code=getInnerHtmlCode(driver, e);
		 logger.info("Clicked element's html code is:"+code);	
	}
	/**
	 * click an element in the page 
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void clickElementViaJsID(WebDriver driver,String elementid) {
		 logger.info("Click elements in page-clicked this element html id is"+elementid);
		//In chrome browser this function didn't work ,so give a solution to load the page correctly
	//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		  executeJS(driver, "document.getElementById('"+elementid+"').click();");
		//"return arguments[0].fireEvent('onclick');",
		 logger.info("Clicked element's html ID is:"+elementid);	
	}
	
	/**
	 * click an element in the page 
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void clickElementViaMouse(WebDriver driver,WebElement e) {
		logger.info("Click elements in page-clicked this element:"
				+ e.getTagName() + ",the text is:" + e.getText());
		//In chrome browser this function didn't work ,so give a solution to load the page correctly
	//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		 new Actions(driver).moveToElement(e).clickAndHold().release().build().perform();
		 //"return arguments[0].fireEvent('onclick');",
		 String code=getInnerHtmlCode(driver, e);
		 logger.info("Clicked element html code is:"+code);
		
	}
	/**
	 * right click an element in the page 
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void rightClickElement(WebDriver driver,WebElement e) {
		logger.info("Right Click elements in page-clicked this element:"
				+ e.getTagName() + ",the text is:" + e.getText());
		//In chrome browser this function didn't work ,so give a solution to load the page correctly
	//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		 new Actions(driver).contextClick(e).perform();
		 //"return arguments[0].fireEvent('onclick');",
		 String code=getInnerHtmlCode(driver, e);
		logger.info("Right Clicked element html code is:"+code);
		
	}
	
	/**
	 * clear the text in the elment and then type the new string in this element
	 * @param e -- the webelment you need to type 
	 * @param text -- the text you want to input
	 * @author huchan
	 */
	public static void clearAndTypeString(WebElement e, String text) {
		logger.info("Type string into the element is:" + e.getTagName()
				+ ", the inputted text:" + text);
		//e.sendKeys(Keys.DELETE);
		//String code=getInnerHtmlCode(driver, e);
	//	logger.info("Clicked element html code is:"+code);
		e.clear();
		e.sendKeys(text);
		//e.sendKeys(Keys.TAB);

	}

	/**
	 * send key to an element
	 * @param e --the webelement you want to send the key
	 * @param enter -- the key need to send 
	 * @author huchan
	 */
	public static void sendKeys(WebElement e, String enter) {
		logger.info("Send keys in this element:" + e.getTagName()
				+ ",the key we send is:" + enter);
	//	e.clear();
		e.sendKeys(enter);
	}

	/**
	 * select an option from the drop list, depends on the visible text
	 * @param e --the web element object
	 * @param text -- the visible text from the dropdown list
	 * @author huchan
	 */
	public static void selectElementViaText(WebElement e, String text) {
		logger.info("Select option text from the list ,list element is:"
				+ e.getTagName() + ",the option text is:" + text);
		Select select = new Select(e);
		select.selectByVisibleText(text);
	}

	/**
	 * select an option from the drop list, depends on the tag's attribute value
	 * @param e  --the web element object
	 * @param value -- the value attribute for this element
	 * @author huchan
	 */
	public static void SelectElementViaValue(WebElement e, String value) {
		logger.info("Select option value from the list ,list element is:"
				+ e.getTagName() + ",the option value is:" + value);
		Select select = new Select(e);
		select.selectByValue(value);
	}

	/**
	 * select an option from the drop list, depends on the index ,the index begin with 0
	 * @param e  --the web element object
	 * @param index -- the index of this webelement ,begin with 0
	 * @author huchan
	 */
	public static void SelectElementViaIndex(WebElement e, int index) {
		logger.info("Select option index from the list ,list element is:"
				+ e.getTagName() + ",the option index is:" + index);
		Select select = new Select(e);
		select.selectByIndex(index);
	}

	/**
	 * sleep the current step for a few seconds 
	 * @param seconds -- the seconds we need to sleep 
	 * @throws InterruptedException
	 * @author huchan
	 */
	public static void sleepSeconds(int seconds) throws Exception
			 {
		logger.info("Begin to sleep current step for " + seconds
				+ " seconds........");
		//You need to be in a synchronized block in order for Object.wait() to work.

		//Also, I recommend looking at the concurrency packages instead of the old school threading packages. They are safer and way easier to work with.
		//synchronized (driver)
	//	{
		//    driver.wait(seconds * 1000);
	//	}
		Thread.sleep(seconds*1000);
	
	}

	/**
	 * sleep current step for a few seconds
	 * @param driver -- the webdriver instance
	 * @param seconds  --the seconds we need to sleep
	 * @throws InterruptedException
	 * @author huchan
	 */
	public static void sleep(WebDriver driver, int seconds)
			throws InterruptedException {
		driver.wait(seconds * 1000);
	}


	/**
	 * wait for an object to be dislayed in the page
	 * @param e -- the web element object
	 * @return  true: the object displayed ,
	 *          false:the object not displayed in the page ;
	 * @author huchan
	 * @throws Exception 
	 */
	public static boolean waitProcessBarNotAppear(WebElement e)
			throws Exception {
		int waitcount = 0;
		boolean isDisplayed = false;
		while (e.isDisplayed()) {
			waitcount = waitcount + 1;
			isDisplayed = e.isDisplayed();
			logger.info("Waitting for the object displayed status-the load object displayed status is:"
					+ isDisplayed);
			sleepSeconds(3);
			if (waitcount == 5) {
				logger.error("Waitting for the object displayed status-the object cannot show in the page:"
						+ e.getTagName() + ",exit the identify the object ");
				break;
			}

		}
		return isDisplayed;

	}
	
	/**
	 * wait for the object displayed in the page ,the time out will be 120 seconds ,if it still not show ,it will failed
	 * @param driver
	 * @param xpathExpression
	 * @return
	 */
	public static boolean waitForObjectDisplay(WebDriver driver,final String xpathExpression){
		boolean findobject=false;
		WebDriverWait wait=new WebDriverWait(driver, 120);
		try{
		wait.until(new ExpectedCondition<Boolean>() {

				@Override
		        public Boolean apply(WebDriver driver) {
		        	logger.info("Enter the waitForObjectDisplay method to wait for the object displayed in the page ");
		        	return (driver.findElement(By.xpath(xpathExpression)).isDisplayed());
		        }
		});
		findobject=true;
		}
		catch(TimeoutException te){
			logger.info("throw expection ,cannot find the web element:"+te.getMessage());
			logger.info("the time out is 120 ,we cannot find this webelment:"+xpathExpression);
			Assert.fail("Cannot find this web element in the page:"+xpathExpression);
		}
		
		return findobject;
	}
	
	/**
	 * wait for the object be visible in the page 
	 * @param driver
	 * @param e
	 * @return
	 * @author huchan
	 */
	public static WebElement waitForObjectVisible(WebDriver driver,WebElement e){
	//	WebDriverWait wait=new WebDriverWait(driver, 3);
		//wait.until(ExpectedConditions.);
		return e;
	}

	/**
	 * import the test data into the test flow for testing
	 * @param sheetname -- the sheet name in this excel file
	 * @return the map data for this row 
	 * @author huchan
	 */
	public static Map<String, String> importDataTable(String sheetname) {
		String excelpath = getProjectWorkspace()+ "resources" + File.separator + "TestData.xls";
		//String casetype = HostUtils.getFQDN();
		@SuppressWarnings("unchecked")
		Map<String, String> mapdata = new LinkedMap();
		if (sheetname.toLowerCase() == "login_page") {
			mapdata = ExcelUtils.getSpecifySheet(excelpath,"Login_Page","postive");
		} else if (sheetname.toLowerCase() == "home_page") {
			mapdata =ExcelUtils.getSpecifySheet(excelpath,"Home_Page","postive");
		} else if (sheetname.toLowerCase() == "device_detail") {
			mapdata = ExcelUtils.getSpecifySheet(excelpath,"Device_Detail","postive");
		} else if (sheetname.toLowerCase() == "email_settings") {
			mapdata=ExcelUtils.getSpecifySheet(excelpath,"Email_Settings","postive");
		} else {
			logger.error("Import datatable into project-Sorry we cannot find the sheet in the test data ,stop the testing now ");
		}

		logger.info("Imported data,the imported Map data is:" + mapdata);
		return mapdata;
	}


	/**
	 * execute the script to set the datepicker value
	 * @param driver --the webdriver instance
	 * @param elementid -- the web element's id
	 * @param date   --the date we need to set ,it's string
	 * @author huchan
	 */
	public static void setDateTimePicker(WebDriver driver, String elementid,
			String date) {
		logger.info("Set DatePicker Date or Time --Execute the java script to modify the weblement's attribute:value,the element id is:"
				+ elementid);
		executeJS(driver, "window.document.getElementById('" + elementid
				+ "').setAttribute('value', '" + date + "');");

	}

	/**
	 * select the checkbox ,if it selectd ,we will not select it again
	 * @param e -- the web element object
	 * @author huchan
	 */
	public static void checkboxed(WebElement e) {
		if (!(e.isSelected())) {
			logger.info("Check the checkbox,the webelment :" + e.getTagName()
					+ e.getText() + ",had been selected now");
			e.click();
		} else {
			logger.info("Check the checkbox,the webelment :" + e.getTagName()
					+ e.getText() + ",had been selected default");
		}
	}

	/**
	 * whether this object displayed in the page
	 * @param e  -- the web element object
	 * @return true:object displayed in the page ,false:the object not displayed in the page
	 * @author huchan
	 */
	public static boolean isDisplayed(WebElement e) {
		boolean isdisplay = false;
		if (e.isDisplayed()) {
			logger.info("Verified Object displayed in the page,the object is:"
					+ e.getTagName() + ",Displayed flag in this object is:"
					+ e.isDisplayed());
			isdisplay = true;
		} else {
			logger.info("Verified Object not displayed in the page,the object is:"
					+ e.getTagName()
					+ ",Displayed flag in this object is:"
					+ e.isDisplayed());
		}
		return isdisplay;
	}

	/**
	 * get the text in the web element
	 * @param e  -- the web element object
	 * @return  String -- the web element's text
	 * @author huchan
	 */
	public static String getElementText(WebElement e) {
		logger.info("Get the element text.The webelement is:" + e.getTagName()
				+ ",the text in the webelement is:" + e.getText().trim());
		return e.getText().trim();
	}

	/**
	 * verify the object is enabled in the page
	 * @param e  -- the web element object
	 * @return  true :the object is enabled ,false:the object is disabled
	 * @author huchan
	 */
	public static boolean isEnabled(WebElement e) {
		logger.info("Verify webelement Enabled in the page-the current webelement is:"
				+ e.getTagName()
				+ ",the text in the webelement is:"
				+ e.getText().trim());
		return e.isEnabled();
	}

	/**
	 * verify the object is selected in the page
	 * @param e --the web element object
	 * @return  true:the object is selected,false:the object is not selected
	 * @author huchan
	 */
	public static boolean isSelected(WebElement e) {
		logger.info("Verify webelement Selected in the page-the current webelement is:"
				+ e.getTagName()
				+ ",the text in the webelement is:"
				+ e.getText().trim());
		return e.isSelected();

	}

	/**
	 * get the webelement's attribute value
	 * @param e  --the web element object
	 * @param attributename  -- the web element's attribute
	 * @return String-- the attribute value for this web element
	 * @author huchan
	 */
	public static String getAttribute(WebElement e, String attributename) {
		logger.info("Get the webelement Attribute-the webelement's attribute:"
				+ e.getTagName() + ",the text in the webelement is:"
				+ e.getText().trim());
		String attributevalue = e.getAttribute(attributename);
		logger.info("Get the webelement Attribute-the webelement's attribute:"
				+ attributename + " value is:" + attributevalue);
		return attributevalue;
	}

	/**
	 * get the web element's tag name 
	 * @param e -- the web element object
	 * @return  String --the web element's tag name
	 * @author huchan
	 */
	public static String getTagName(WebElement e) {
		logger.info("Get the webelement TagName-the webelement's tag name is:"
				+ e.getTagName() + ",the text in the webelement is:"
				+ e.getText().trim());
		String tagname = e.getTagName();
		logger.info("Get the webelement TagName-the webelement's tag name is:"
				+ e.getTagName());
		return tagname;
	}

	/**
	 * get all the web elements behind the specified element
	 * @param e -- the web element object
	 * @param tagname  -- the web element's tag name
	 * @return  List<WebElement> a list of all the sub web element we found
	 * @author huchan
	 */
	public static List<WebElement> findElementListByTagName(WebElement e,
			String tagname) {
		logger.info("Find all subelements by Tag Name:" + e.getTagName()
				+ ",the sub elment's tag name is:" + tagname);
		List<WebElement> elements = e.findElements(By.tagName(tagname));
		return elements;
	}

	/**
	 * find the element by xpath in the page
	 * @param e --the web element object
	 * @param xpath -- the web element's xpath
	 * @return  WebElement -- get the found web element
	 */
	public static WebElement findElementByXpath(WebElement e, String xpath) {
		logger.info("Find subelement by Xpath-we will find an sub element with the xpath:"
				+ xpath);
		WebElement element = e.findElement(By.xpath(xpath));
		//highLight(driver, element);
		return element;
	}
	/**
	 * find the element by xpath in the page
	 * @param e --the web element object
	 * @param xpath -- the web element's xpath
	 * @return  WebElement -- get the found web element
	 */
	public static WebElement findElementByCSS(WebElement e, String css) {
		logger.info("Find subelement by css-we will find an sub element with the css selector:"
				+ css);
		WebElement element = e.findElement(By.cssSelector(css));
		//highLight(driver, element);
		return element;
	}


	/**
	 * highLight:(highlight the web element in the page). 
	 * 

	 * @author huchan
	 * @param driver -- the web driver instance 
	 * @param e -- the web element object
	 * @since JDK 1.6
	 */
	public static void highLightExt(WebDriver driver, WebElement e) {
		logger.info("Highlight the element ,the object is:" + e.getTagName()
				+ ",the text in this object is:" + e.getText());
		Actions action = new Actions(driver);
		action.contextClick(e).perform();
		logger.info("Had right click the object ,then press the escape key");
		e.sendKeys(Keys.ESCAPE);
	}
	public static void highLight(WebDriver driver, WebElement e) {
		if (driver instanceof JavascriptExecutor) {
			executeJS(driver,"arguments[0].style.border='4px solid red'",e);
		}
	}

	/**
	 * executeJS:(execute the java script in this page). 

	 * @author huchan
	 * @param driver -- the web driver's instance
	 * @param script  --the java script we need to execute
	 * @since JDK 1.6
	 */
	public static Object executeJS(WebDriver driver, String script) {
		logger.info("Run the javascript from page ,the java script is:"
				+ script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeScript(script);

	}
	public static void executeJS(WebDriver driver, String script,WebElement e) {
		logger.info("Run the javascript from page ,the java script is:"
				+ script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(script,e);

	}
	public static Object executeJSReturn(WebDriver driver, String script,WebElement e) {
		logger.info("Run the javascript from page ,the java script is:"
				+ script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		Object object=je.executeScript(script,e);
		return object;

	}
	/**
	 * click the ok button in the pop up dialog (alert dialog)
	 * @param driver  -- the web driver's instance
	 * @param seconds -- the seconds we need to wait to click it
	 * @author huchan
	 */
	public static boolean alertClickOK(WebDriver driver,int seconds) {
		boolean isclicked=false;
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		try{
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		logger.info("Pop up Alert text is:"+alert.getText());
		alert.accept();
		isclicked=true;
		}catch(NoAlertPresentException e){
			logger.info("the Alert didn't pop up currently:"+e.getMessage());
		}catch(TimeoutException e){
			logger.error("Time out we cannot find this OK button:"+seconds);
		}
		
		return isclicked;
		// driver.w
	}
	
	/**
	 * this fuction is used for clicking the cancel button
	 * @category click the Alert dialog ,click the cancel button
	 * @param driver -- the web driver instance
	 * @param seconds -- the second we need to wait to click the cancel button
	 * @see alertClickOK
	 * @author huchan
	 */

	public static boolean alertClickCancel(WebDriver driver,int seconds) {
		boolean isclicked=false;
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		try{
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		logger.info("Pop up Alert text is:"+alert.getText());
		alert.dismiss();
		isclicked=true;
		}catch(NoAlertPresentException e){
			logger.info("the alert didn't pop up currently:"+e.getMessage());
		}
		catch(TimeoutException e){
			logger.error("Time out we cannot find this Cancel button:"+seconds);
		}
		
		return isclicked;
	}
	
	
	/**
	 * getBrowserType:(get the current running browser type and version number). 
	 * @author huchan
	 * @param driver  ---the web driver instance
	 * @return String --- the browser type and version number
	 * @since JDK 1.6
	 */
	public static String getBrowserType(WebDriver driver){
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		logger.info("Get the current running browser is:"+browserName+",the browser version is:"+browserVersion);
		return browserName+" "+browserVersion;
	}
	
	/**
	 * getCurrentURL:(get the current page URL address). 

	 * @author huchan
	 * @param driver  --- the web driver instance
	 * @return String ---the url of current page
	 * @since JDK 1.6
	 */
	public static String getCurrentPageURL(WebDriver driver){
		String pageurl="";
		JavascriptExecutor je=(JavascriptExecutor) driver;
		final String docstate=(String) je.executeScript("return document.readyState");
		logger.info("Current loading page state is:"+docstate);
		WebDriverWait wait=new WebDriverWait(driver,120);
		ExpectedCondition<Boolean> ec = new ExpectedCondition<Boolean>() {
	          @Override
			public Boolean apply(WebDriver d) {
	            return (docstate.equals("complete"));
	          }
	        };
	    try{
	       logger.info("We just wait for the current page load correctly now...");
	       wait.until(ec);	       
	       pageurl=driver.getCurrentUrl();
	       logger.info("we found the current url is:"+pageurl);
	    }
	    catch(TimeoutException e){
	    	pageurl="time out:120 seconds";
	    	logger.error("Sorry the page cannot be loaded correctly:"+e.getMessage()+driver.getCurrentUrl());
	    }
		return pageurl;
	}
	
	
	
	public static void assertEqualsExpected(String stepname,String expectedvalue,String actualvalue){
		if(expectedvalue.trim().equalsIgnoreCase(actualvalue.trim())){
			Reporter.log(stepname+"--Assert element value Passed");
			logger.info("Compare the actual page value with expected value in step:"+stepname+",the expected page element value is:["+
		    expectedvalue+"],and actual value in page is:["+actualvalue+"]");
		}
		else
		{
			Reporter.log(stepname+"--Assert element value Failed");
			logger.info("Compare the actual page value with the expected value failed in step ["+stepname+"],the expected page element value is:["+
		    expectedvalue+"],and actual value in page is:["+actualvalue+"]");
			Assert.fail("Compare the actual page value with the expected value failed in step ["+stepname+"],the expected page element value is:["+
				    expectedvalue+"],and actual value in page is:["+actualvalue+"]");
		}
	}
	public static void assertDisplayed(String stepname,WebElement e){
		if(e.isDisplayed()){
			Reporter.log(stepname+"--Assert element displayed in the page  Passed");
			logger.info("the weblement displayed in the page as we expected ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
		else
		{
			Reporter.log(stepname+"--Assert element displayed in the page  Failed");
			logger.info("the weblement not displayed in the page as we expected ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
            Assert.fail("the weblement not displayed in the page as we expected ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
	}
	public static void assertEnabled(String stepname,WebElement e){
		if(e.isEnabled()){
			Reporter.log(stepname+"--Assert element enabled status in the page  Passed");
			logger.info("the weblement enabled status is true in the page as we expected ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
		else
		{
			Reporter.log(stepname+"--Assert element enabled status in the page  Failed");
			logger.info("the weblement enabled status is false in the page ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
            Assert.fail("the weblement enabled status is false in the page ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
	}
	
	public static void assertDisabled(String stepname,WebElement e){
		if(!e.isEnabled()){
			Reporter.log(stepname+"--Assert element disabled status in the page  Passed");
			logger.info("the weblement enabled status is false in the page as we expected ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
		else
		{
			Reporter.log(stepname+"--Assert element disabled status in the page  Failed");
			logger.info("the weblement enabled status is true in the page ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
            Assert.fail("the weblement enabled status is true in the page ,the element is:["+e.getTagName()+"],the text is the element is:["+e.getText()+"]");
		}
	}
	
	
	/**
	 * get current eclipse workspace we used 
	 * @return
	 */
	public static String getProjectWorkspace(){
		String path = new File(".").getAbsolutePath();
		String probasepath = path.substring(0, path.length() - 1);
		logger.debug("Current project's workspace path is:"+probasepath);
		return probasepath;
	}
	
//******************************************AutoItX dll API*****************************************************************************	
	/**
	 * getAutoItX:(get the AutoItX instance). 
	 *
	 * @author huchan
	 * @return
	 * @since JDK 1.6
	 */
	public static AutoItXUtils getAutoItX(){
		File file;
		try{
		   file= new File("lib", "jacob-1.18-M1-x86.dll"); //path to the jacob dll
		   logger.info("Using jacob dll x86 bit file to load the jacob now...");
		}catch(UnsatisfiedLinkError error){
			logger.info("the java VM is x86 so we change to load the jacob dll with x64 bit:"+error.getMessage());
			file= new File("lib", "jacob-1.18-M1-x64.dll"); //path to the jacob dll
		}
		logger.info("Get the jacob dll file path is:"+file.getAbsolutePath());
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        
        return new AutoItXUtils();
	}
	
	/**
	 * Using AutoIt to click the button ,this is not suitable for the RemoteWebDriver
	 * @param title
	 * @param text
	 * @param controlID
	 */
	public static void autoit_clickButton(String title,String text,String controlID){
		AutoItXUtils autoit=getAutoItX();
		autoit.winWait(title, text, 120);
		autoit.winActivate(title, text);
		boolean clicked=autoit.controlClick(title, text, controlID);
		if(clicked){
			logger.info("Now we use the AutoItx API to click the button ");
			logger.info("the clicked button's window title is:"+title+",the window text is:"+text+",the button ID is:"+controlID);
		}
		else
		{
			logger.info("Now we try to use the AutoItX API to click the button ,it's failed ,we cannot cick it .sorry: ");
			logger.info("the failed button's window title is:"+title+",the window text is:"+text+",the button ID is:"+controlID);
		}
		
	}
	
	/**
	 * Using the AutoIt to set the string into a control object
	 * @param title
	 * @param text
	 * @param controlID
	 * @param typetext
	 */
	public static void autoit_typeText(String title,String text,String controlID,String typetext){
		AutoItXUtils autoit=getAutoItX();
		autoit.winWait(title, text, 120);
		autoit.winActivate(title, text);
		boolean seet=autoit.controlSend(title, text, controlID, typetext);
		if(seet){
			logger.info("Now we use the AutoItx API to send string to the control ");
			logger.info("the control window title is:"+title+",the window text is:"+text+",the control ID is:"+controlID);
		}
		else
		{
			logger.info("Now we try to use the AutoItX API to input string ,it's failed ,we cannot input the string .sorry: ");
			logger.info("the failed control's window title is:"+title+",the window text is:"+text+",the control ID is:"+controlID);
		}
	}
	
	public static void firefox_saveFile(String title) throws Exception{
		//[TITLE:Opening;CLASS:MozillaDialogClass]
		//[CLASS:Button; INSTANCE:1]
		sleepSeconds(5);
		AutoItXUtils autoit=getAutoItX();
		autoit.winWait(title, "", 120);
		logger.info("had waited 120 secondsto activate the open dialog");
		autoit.winActivate(title, "");
		logger.info("now activated the open file dialog....");
		//send alt +S key to save the file
		autoit.send("{ALTDOWN}s{ALTUP}");
		logger.info("Now send key to the current window....");
		sleepSeconds(2);
		//click the ok button
		autoit.send("{ENTER}");
		logger.info("send the enter key to current windows ,click the ok button...");
		sleepSeconds(3);
		String poptitle="Enter name";
		boolean folderdlg=autoit.winExists(poptitle);
		if(folderdlg){
			autoit_clickButton(poptitle,"","[ID:1;CLASSNN:Button1;CLASS:Button;INSTANCE:1]");
		}else
		{
			logger.info("Now we had not found the download folder dialog from current instance,we will send the ok button again");
			autoit.winWait(title, "", 120);
			autoit.winActivate(title, "");
			autoit.send("{ALTDOWN}s{ALTUP}");
			autoit.send("{ENTER}");
		}
		SeleniumCore.sleepSeconds(5);
		logger.info("great ,we had download the zip file into our default folder");
		
	}
//**************************************************AutoItX API**************************************************************************

//***********************************************************************************************************
	/**
	 * switchtoWindow:(Here describle the usage of this function). 
	 * http://santoshsarmajv.blogspot.com/2012/04/how-to-switch-control-to-pop-up-window.html
	 * http://stackoverflow.com/questions/11614188/switch-between-two-browser-windows-using-selenium-webdriver
	 *
	 * @author huchan
	 * @param driver
	 * @param windowTitle
	 * @throws AWTException 
	 * @since JDK 1.6
	 */
	public static void switchtoWindow(WebDriver driver,String windowTitle) throws AWTException{
		Robot robot=new Robot();
		Set<String> allwindows=driver.getWindowHandles();
		for (String window : allwindows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
               robot.delay(5000);
              // robot.keyPress(keycode);
            }
        }
	}
	
	/**
	 * refresh the current page
	 * @param driver
	 */
	public static void refreshPage(WebDriver driver){
		//driver.navigate().refresh();
		logger.info("Now refresh the page to keep the session valid");
		//or blow
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.F5).perform();
	}
	/**
	 * get the page title
	 * @param driver
	 * @return String
	 */
	public static String getPageTitle(WebDriver driver){
		String title=driver.getTitle();
		logger.info("Get current page title is:"+title);
		return title;
	}
	
	/**
	 * get the webelement's html code
	 * @param driver
	 * @param e
	 * @return String
	 */
	public static String getInnerHtmlCode(WebDriver driver,WebElement e){
		String contents = (String)executeJSReturn(driver,"return arguments[0].innerHTML;", e);
		logger.info("Get the html code for this webelement:"+contents);
		return contents;
	}
	
	/**
	 * wait for a few time to find the object displayed in the page
	 * @param driver
	 * @param e
	 * @param timeout
	 * @return true found the element visible displayed in the page ,false ,cannot find the webelement
	 */
	public static boolean  waitForGUIAppear(WebDriver driver,WebElement e,long timeout){
		boolean findelement=false;
		WebDriverWait wdw=new WebDriverWait(driver, timeout);
		logger.info("wait for the object displayed in the page:"+getInnerHtmlCode(driver, e));
		wdw.until(ExpectedConditions.visibilityOf(e));
		return findelement;
		
	}
	
	/**
	 * scroll the view to we can see in the page
	 * @param driver
	 * @param e
	 */
	public static void scrollToView(WebDriver driver,WebElement e){
		   executeJS(driver, "window.scrollTo(0,"+e.getLocation().y+")");
		   executeJS(driver, "arguments[0].scrollIntoView(true);", e);
		   logger.info("Now we scroll the view to the position we can see");

	}
	/**
	 * click the upload button to upload the file ,this is for hte webFile element ,the input type is file
	 * @param driver
	 * @param e
	 * @param filepath
	 * http://sauceio.com/index.php/2012/03/selenium-tips-uploading-files-in-remote-webdriver/
	 * upload the local file from remote webdriver
	 */
	public static void uploadFile(WebDriver driver,WebElement e,String filepath){
		String uploadcode=getInnerHtmlCode(driver, e);
		logger.info("the upload webelement html code we get is:"+uploadcode);
		e.sendKeys(filepath);
	}
	//read the email
	//http://www.avajava.com/tutorials/lessons/how-do-i-receive-email-in-java.html?page=1
	//http://metoojava.wordpress.com/2010/03/21/java-code-to-receive-mail-using-javamailapi/
	 public static void receiveEmail(String pop3Host, String storeType, String user, String password) {
		 
	   }
	  
	 /*
	  * 
	  * the blow is for generating an email we need 
	  */
	 public static void generateEmailData(String datadescription,String datavalue){
		 String outputfile=REPORT_DIR+File.separator+"dataoutput.log";
		 boolean updatedbefore=false;
		 File dataoutputfile=null;
		 try{
			 dataoutputfile= new File(outputfile);
			 if (!dataoutputfile.exists()) {
					dataoutputfile.createNewFile();
				}
				
			    BufferedReader br=new BufferedReader(new FileReader(outputfile));
			    String strline = null;
			    while ((strline = br.readLine()) != null) {
					if (strline.contains(datadescription)) {
						//find the status report we had reported before
		                FileUtils.replaceStringOfFile(outputfile,strline,datadescription + "|" + datavalue);
						updatedbefore=true;
					}
				}
			    br.close();
			    if(!updatedbefore){
				    //if this is the first time to report the step
				    FileWriter writer = new FileWriter(outputfile, true);
					BufferedWriter bufferwriter = new BufferedWriter(writer);
					bufferwriter.write(datadescription + "|" + datavalue + "\n");					
					bufferwriter.close();
			    }
			    
	    	}
				catch (IOException e) {
					logger.error("Sorry Met the IOException for the reporter file :"
							+ dataoutputfile.getAbsolutePath() + " the error Exception is :"
							+ e.getMessage());
				}
		 
	 }
	 
	 /**
	  * generate a email step 
	 * @param stepname
	 * @param stepchecker
	 * @param status
	 * @param comments
	 */
	public static void generateEmailStep(String stepname,String stepchecker,String status,String comments){
		 String stepfile=REPORT_DIR+File.separator+"stepoutput.log";
		 boolean updatedbefore=false;
		 File stepoutputfile=null;
		 try{
			 stepoutputfile= new File(stepfile);
			 if (!stepoutputfile.exists()) {
				 stepoutputfile.createNewFile();
				}			
			    BufferedReader br=new BufferedReader(new FileReader(stepfile));
			    String strline = null;
			    while ((strline = br.readLine()) != null) {
					if (strline.contains(stepchecker)) {
						//find the status report we had reported before
		                FileUtils.replaceStringOfFile(stepfile,strline,stepname + "|" + stepchecker+"|"+status+"|"+comments);
						updatedbefore=true;
					}
				}
			    br.close();
			    if(!updatedbefore){
				    //if this is the first time to report the step
				    FileWriter writer = new FileWriter(stepfile, true);
					BufferedWriter bufferwriter = new BufferedWriter(writer);
					bufferwriter.write(stepname + "|" + stepchecker+"|"+status+"|"+comments + "\n");					
					bufferwriter.close();
			    }
			    
	    	}
				catch (IOException e) {
					logger.error("Sorry Met the IOException for the reporter file :"
							+ stepoutputfile.getAbsolutePath() + " the error Exception is :"
							+ e.getMessage());
				}
		 
	 }
	
	
}