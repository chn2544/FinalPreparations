package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eu;
	
	// Private By Locators : Object Repository
	
	private By emailid=By.id("input-email");					//username feild
	private By password=By.id("input-password");				//password feild
	private By loginbtn=By.xpath("//input[@type='submit']");	// login button
	private By forgotpwdlink=By.linkText("Forgotten Password");	 // forgotpassword lisk
	private By registerlink=By.linkText("Register");	 		// Register link
	
	private By logoutsuccess=By.cssSelector("div#content h1");	// Check logout sucess message
	
	private By forGit=By.id("GitPurpose");						// adding this locator for code push to git after a change in code
		
	//  Constructor
	
	public  LoginPage(WebDriver driver)						// this will receive driver from class where object
	{														// for LoginPage is created (i.e. BaseTest Class)
		this.driver=driver;									// and will pass it to local driver.
		eu=new ElementUtil(driver);							// we are creating object in constructor of login page 
	}														// because we need to pass driver to elementutil class constructor.		
	
	//Page Actions
	
	public String getLoginPageTitle()
	{
		return eu.waitForTitleExplicitWait(Constants_Class.LOGIN_PAGE_TITLE, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
	}
	
	public String getLoginPageURL()
	{
		return eu.waitForURLExplicitWait(Constants_Class.LOGIN_PAGE_URL_FRACTION, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
	}
	
	@Step("User is able to login with username: {0} and password: {1}")			// here 0 will represent first parameter and 1 will represent second paramater
	public AccountsPage doLogin(String uname,String pwd)
	{
		System.out.println("Login Credentials are - username : " + uname+" & password : "+pwd);
		eu.waitForVisibilityOfElementExplicitWait(emailid, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).sendKeys(uname);	// this method returns webelement so we can write sendkeys
		eu.doSendKeys(password, pwd);																						// for other locators we dont need to wait
		eu.doClick(loginbtn);
		return new AccountsPage(driver);			// returning next landing page class object.  - this is called Page Chaining Model
	}
	
	public boolean isForgotPwdLinkExist()
	{
		return eu.doIsDisplayed(forgotpwdlink);
	}

	@Step("Function to check Registration Link...")	
	public boolean isRegisterLinkExist()
	{
		return eu.doIsDisplayed(registerlink);
	}
	
	public String isUserLoggedOut()
	{
		return eu.waitForVisibilityOfElementExplicitWait(logoutsuccess, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).getText();
	}
	
	@Step("For navigating to Registration Page...")	
	public RegisterUserPage goToRegistration()			// as we are landing on new page , writing return type as  that class ref type
	{
		eu.doClick(registerlink);
		return new RegisterUserPage(driver);
	}

}
