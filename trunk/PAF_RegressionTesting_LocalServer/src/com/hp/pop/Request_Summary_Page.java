package com.hp.pop;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

public class Request_Summary_Page extends PageObject{



	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[1]")
	private WebElement runid;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[2]")
	private WebElement requesttype;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[3]")
	private WebElement customername;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[4]")
	private WebElement address;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[5]")
	private WebElement languages;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[6]")
	private WebElement devicenumbers;

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[2]/div/div")
	private WebElement notes;
	

	public Request_Summary_Page(WebDriver driver) {
		super(driver);
	}

	public boolean verifyPageElements(String pagename) throws Exception 
	{
		try{
        SeleniumCore.assertDisplayed("Assert the element displayed in the Request summary page", runid);
        SeleniumCore.assertDisplayed("Assert the element displayed in the Request summary page", requesttype);
        SeleniumCore.assertDisplayed("Assert the element displayed in the Request summary page", customername);
        SeleniumCore.assertDisplayed("Assert the element displayed in the Request summary page", address);
        SeleniumCore.assertDisplayed("Assert the element displayed in the Request summary page", languages);
		}
		catch(NoSuchElementException e){
			//Resulter.log("STATUS_SCAN_EMAIL_RESULT", "Failed");
			//Resulter.log("COMMENT_SCAN_EMAIL_RESULT", "the request summary page cannot display correctly now");
			SeleniumCore.generateEmailStep("Request Summary detail", "Veryify the page load correctly", "Failed", "the page object cannot load correctly:"+e.getMessage(), driver);
			Assert.fail("All the webelement canot display in the summary page ");
		}
		return super.verifyPageElements(pagename);
      
	}

	public String getRunID() throws InterruptedException, IOException {
		String returnrunid = SeleniumCore.getElementText(runid);
		logger.info("Request summary view page .Currently the request run over ,and the Run ID is:"
				+ returnrunid);
		//Resulter.log("STATUS_SCAN_EMAIL_RESULT", "Passed");
		//Resulter.log("COMMENT_SCAN_EMAIL_RESULT", "RUN ID:"+returnrunid+",Requst Type:"+getRequestType()+",Customer Name:"+getCustomerName());
		SeleniumCore.generateEmailStep("Request Summary detail", "Get the Request ID", "Passed", "the new Assessment Request Run ID:"+returnrunid, driver);
		return returnrunid;
	}

	public String getRunStartTime(){
		return TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
	}
	public String getRequestType() {
		return requesttype.getText().trim();
	}

	public String getCustomerName() {
		return customername.getText().trim();
	}

	public String getAddress() {
		return address.getText().trim();
	}

	public String getLanguage() {
		return languages.getText().trim();
	}

	public String getDeviceNumber() {
		return devicenumbers.getText().trim().split("[")[0];
	}
	
	public ListSearch_Assessment_Run_Page goSearchRunPage(){
		//go to the search run result page
		Map<String,String> mapdata=SeleniumCore.importDataTable("login_page");
		String baseurl=mapdata.get("Base_URL");
		String runurl=baseurl+"/run";
		driver.get(runurl);
		return PageFactory.initElements(driver, ListSearch_Assessment_Run_Page.class);
	}

}