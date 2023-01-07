package com.qa.opencart.BaseTestPkg;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterUserPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	public DriverFactory df;	// creating ref var for DF because in setup method
								// we would need to initialize driver
	
	public LoginPage lp;		// in respective test class (eg Loginpagetest) we need object of page class 
								// to call its methods, so creating object at basetest level
	
	public AccountsPage ap;
	public SearchResultsPage srp;
	public ProductInfoPage pip;
	public RegisterUserPage rup;
	
	public WebDriver driver;	// we are declaring this because init driver is returning driver 
								// and we can use it further
	
	public Properties prop;		// declared this at class level, so that this can be ussed by other classes extending base class
	
	public SoftAssert saobj;	// soft assert object...
	
	@BeforeTest
	public void setup()			// this method should initialize driver and launch browser
	{							// which init method in driverfactory does
		df=new DriverFactory();
		prop=df.init_prop();					// initializing the properties as soon as we create driver factory object and taking those values in properties class object.
		driver=df.init_driver(prop);		// Here we are passing object of Properties file to init_driver method and that method will decide which properties it needs inside it. 
		lp=new LoginPage(driver);		// creating object in setup method, so that before test execution object will be created
										// and we can pass driver coming from driver factory to loginpage.
		
		saobj=new SoftAssert();			// initializing soft assertion object
	}	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
}
