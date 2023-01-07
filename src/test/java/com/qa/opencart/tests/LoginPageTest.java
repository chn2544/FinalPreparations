package com.qa.opencart.tests;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.BaseTestPkg.BaseTest;
import com.qa.opencart.constants.Constants_Class;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Feature 54528 - Implement Login Page Test Cases...")
@Story("Product Backlog Item - 782345- Login Page User Story...")
public class LoginPageTest extends BaseTest {

	
	@Test(priority = 1)
	public void loginPageTitleTest()
	{
		String acttitle=lp.getLoginPageTitle();								// as we have declared object of login page in base test
		System.out.println("Login Page Title : "+acttitle);					// we can access it here (lp) and its methods as well.
		Assert.assertEquals(acttitle, Constants_Class.LOGIN_PAGE_TITLE);	// now instead of writing hard coded value here, if some other
	}																		// TC's wants to use title , we should use Constant value	
	
	@Description("This test is to verify if login page url is correct or not...")
	@Severity(SeverityLevel.MINOR)
	@Test (priority = 2)
	public void loginPageURLTest()
	{
		String acturl=lp.getLoginPageURL();									
		System.out.println("Login Page Title : "+acturl);					
		Assert.assertTrue(acturl.contains(Constants_Class.LOGIN_PAGE_URL_FRACTION));	
	}
	
	@Test (priority = 3)
	public void forgotPasswordLinkTest()
	{
		Assert.assertTrue(lp.isForgotPwdLinkExist());
	}
	
	@Description("This test is to verify if registration link exists or not...")
	@Severity(SeverityLevel.TRIVIAL)
	@Test (priority = 4)
	public void registerLinkTest()
	{
		Assert.assertTrue(lp.isRegisterLinkExist());
	}
	
	@Test (priority = 5)
	public void loginTest()
	{
		Assert.assertTrue(lp.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim()).isLogOutLinkExist());
	}
	
}






















