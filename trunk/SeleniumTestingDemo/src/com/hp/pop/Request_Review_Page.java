package com.hp.pop;

import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimerUtils;

public class Request_Review_Page {

	private static Logger logger = Logger.getLogger(Request_Review_Page.class);
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='divREVIEW_REQUEST']/h2")
	private WebElement header;
	@FindBy(how = How.XPATH, using = ".//*[@id='runBtn']")
	private WebElement runbtn;

	// @FindBy(how=How.XPATH,using=".//*[@id='hpit-topMenu']/li[2]/ul/li[2]/a")
	// private WebElement checkrun;

	public Request_Review_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		String headtitle = SeleniumCore.getElementText(header);
		boolean displayheader = SeleniumCore.isDisplayed(header);
		
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		logger.info("New request assessment review page.the head is:"
				+ headtitle);
		if (displayheader) {
			logger.info("New request assessment review page .we found the header is displayed in the page:"
					+ displayheader);
		} else {
			Assert.fail();
			logger.error("New request assessment review page .Sorry the webelment cannot be found in the page:"
					+ displayheader);
		}
	}

	public Request_Summary_Page RunRequest()
			throws Exception {
		boolean runlater=false;
		
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");
		String isSchedule = mapdata.get("Scheduling").toLowerCase();
		Resulter.log("STATUS_SCAN_REVIEW", "Passed");
		if(isSchedule.equals("yes")){
			runlater=true;
			Resulter.log("COMMENT_SCAN_REVIEW", "Assessment run later");
		}
		
		if (runlater) {
			// run later the schedule
			logger.info("New request created now ,this is the review page ,and click the save button now ");
			driver.findElement(By.xpath(".//*[@id='saveBtn']")).click();
		} else {
			// run now the schedule
			Resulter.log("COMMENT_SCAN_REVIEW", "the assessment run at:"+TimerUtils.getCurrentTime(Calendar.getInstance().getTime()));
			logger.info("New request created now ,this is the review page ,and click the run button now ");
			SeleniumCore.clickElement(runbtn);
		}
		
		SeleniumCore.sleepSeconds(3);
		return PageFactory.initElements(driver, Request_Summary_Page.class);
	}

}