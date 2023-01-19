package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eu;
	
	private By imgheader=By.cssSelector("div#logo a img");
	private By logoutlink=By.linkText("Logout");
	private By sectionheaders=By.xpath("//div[@id='content']//h2");
	private By searchfeild=By.xpath("//input[@name='search']");		

	private By searchfeild1=By.xpath("//input[@name='search']");	// changes made

	private By searchclick = By.xpath("//span[@class='input-group-btn']//button[@type='button']");
	
	
//  Constructor
	
	public  AccountsPage(WebDriver driver)						
	{														
		this.driver=driver;									
		eu=new ElementUtil(driver);							 
	}																
	
	//Page Actions
	
	public String getAccountsPageTitle()
	{
		return eu.waitForTitleExplicitWait(Constants_Class.ACCOUNTS_PAGE_TITLE, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
	}
	
	public String getAccountsPageURL()
	{
		return eu.waitForURLExplicitWait(Constants_Class.ACCOUNTS_PAGE_URL_FRACTION, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
	}
	
	public String getAccountsPageHeader()
	{
		String titleattribute=eu.doGetAttribute(imgheader, "title");  // should return attribute value for key title
		System.out.println("Image contains : "+titleattribute);
		return titleattribute;			 
	}
	
	@Step("This Function is to get Section Text List...")
	public List<String>  getAccountsPageSectionsList()
	{
		List<WebElement> webelelist =eu.getElements(sectionheaders);
		List<String> sectiontextlist=new ArrayList<String>();
		for(WebElement e:webelelist)
		{
			sectiontextlist.add(e.getText());
		}
		return sectiontextlist;
	}
	
	@Step("This Function is to find Search Box ...")
	public boolean isSearchBoxExist()
	{
		return eu.waitForVisibilityOfElementExplicitWait(searchfeild, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).isDisplayed();
	}
	

	public boolean isLogOutLinkExist()
	{
		return eu.waitForVisibilityOfElementExplicitWait(logoutlink, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).isDisplayed();
	}
	
	public LoginPage clickLogOut()
	{
		if(isLogOutLinkExist())
		{
			eu.doClick(logoutlink);
		}
		return new LoginPage(driver);			//  because we are taking reference of page where Account Logout comes in Login Page 
	}
	
	public SearchResultsPage doSearch(String searchkey)
	{
		if(isSearchBoxExist())
		{
			
			eu.doSendKeys(searchfeild, searchkey);
			eu.doClick(searchclick);
			return new SearchResultsPage(driver);				// next landing page
		}
		return null;											// if search box doesnt exist return null
	}
	
}
