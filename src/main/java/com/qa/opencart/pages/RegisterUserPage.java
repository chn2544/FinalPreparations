package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ElementUtil;

public class RegisterUserPage {
	
	private WebDriver driver;
	private ElementUtil eu;
	
	//  Constructor
	
	public  RegisterUserPage(WebDriver driver)				
	{														
		this.driver=driver;									
		eu=new ElementUtil(driver);							 
	}						
	
	
	// Private By Locators : Object Repository
	
		private By fname=By.xpath("//input[@name='firstname']");		// First Name
		private By lname=By.xpath("//input[@name='lastname']");		// Last Name
		private By email=By.xpath("//input[@name='email']");		// email button
		private By telephone=By.xpath("//input[@name='telephone']");		// telephone
		private By password=By.xpath("//input[@name='password']");		// Password
		private By confirmpwd=By.xpath("//input[@name='confirm']");		// Confirm Password
		private By yesradio=By.xpath("//div[@class='col-sm-10']//input[@value='1' and @name='newsletter']");			// yes radiobutton
		private By noradio=By.xpath("//div[@class='col-sm-10']//input[@value='0' and @name='newsletter']");			// no radiobutton
		private By consent=By.xpath("//input[@name='agree']");			// consent checkbox
		private By continuebtn=By.xpath("//input[@value='Continue']");		// continue button
		
		private By regsuccessmsg=By.cssSelector("div#content h1"); 		// registration success message
		private By logoutlink=By.linkText("Logout");				// logout link
		private By registerlink=By.linkText("Register");	 		// Register link
		
	//Page Actions
	
		public boolean registerUser(String firstName,String lastName,String emailid,String phone,String pwd,String subscription)
		{
			WebElement firstname=eu.waitForElementPresentExplicitWait(fname, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
			firstname.clear();
			firstname.sendKeys(firstName);
			eu.doSendKeys(lname, lastName);
			eu.doSendKeys(email, emailid);
			eu.doSendKeys(telephone, phone);
			eu.doSendKeys(password, pwd);
			eu.doSendKeys(confirmpwd, pwd);
			
			if(subscription.equalsIgnoreCase("yes"))
			{
				eu.doClick(yesradio);
			}
			else
			{
				eu.doClick(noradio);
			}
			
			eu.doClick(consent);
			eu.doClick(continuebtn);
			
			String successmessage=eu.waitForElementPresentExplicitWait(regsuccessmsg, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).getText();
			if(successmessage.contains(Constants_Class.REGISTRATION_SUCCESS))
			{
				eu.doClick(logoutlink);
				eu.doClick(registerlink);
				return true;
			}
			else
			{
				eu.doClick(logoutlink);
				eu.doClick(registerlink);
				return false;
				
			}
				
			
		}
		
	
		
}
