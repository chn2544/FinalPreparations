package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.BaseTestPkg.BaseTest;
import com.qa.opencart.constants.Constants_Class;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Feature 55728 - Implement Accounts Page Test Cases...")
@Story("Product Backlog Item - 558373- Accounts Page User Story...")
public class AccountsPageTest extends BaseTest{

	// Now Pre Condition for AccountsPageTest is user should be logged in 
	// we can call dologin method of Login Page in setup method of Base Test but it will disturb login page test cases
	// as for login page test as well precondition would get executed i.e. setup method which will login to application and
	// then test cases of login page test would fail as to execute those we dont need to login to app
	
	// so to handle this we will use @BeforeClass annotation which gets executed after @BeforeTest
	
	@BeforeClass
	public void accPageSetup()
	{
//		ap=new AccountsPage(driver);			// we dont need to initialize it as it will be initialized in do login method return.
		ap=lp.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		
	}
	
	@Test (priority = 1, enabled=false)
	public void accountsPageTitleTest()
	{
		String acctitle=ap.getAccountsPageTitle();
		System.out.println("Accounts Page Title : "+acctitle);	
		Assert.assertEquals(acctitle, Constants_Class.ACCOUNTS_PAGE_TITLE);
	}
	
	@Description("This test is to verify URL of Page...")
	@Severity(SeverityLevel.CRITICAL)
	@Test (priority = 2)
	public void accountsPageURLTest()
	{
		String accurl=ap.getAccountsPageURL();									
		System.out.println("Account Page Url : "+accurl);					
		Assert.assertTrue(accurl.contains(Constants_Class.ACCOUNTS_PAGE_URL_FRACTION));	
	}
	
	@Test (priority = 3)
	public void accountsPageImageHeader()
	{
		Assert.assertEquals(ap.getAccountsPageHeader(), Constants_Class.ACCOUNTS_PAGE_HEADER);
	}
	
	@Description("This test is to verify if sections are displayed correctly or not...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 4)
	public void accountsPageSectionsTest()
	{
		List<String> testlist=ap.getAccountsPageSectionsList();
		System.out.println("Sections Names : "+testlist);
		Assert.assertEquals(testlist, Constants_Class.EXPECTED_ACCOUNTS_SECTION_LIST);
	}
	
	@Description("This test is to verify if logout link is displayed correctly or not...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 5)
	public void accountsPageisLogOutLinkExist()
	{
		Assert.assertTrue(ap.isLogOutLinkExist());
	}
	
	@Description("This test is to verify if search field is displayed correctly or not...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6)
	public void accountsPageisSearchFeildExist()
	{
		Assert.assertTrue(ap.isSearchBoxExist());
	}
	

	@Description("This test is to verify if logout is working or not...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 7)
	public void accountsPageLogoutTest()
	{
		Assert.assertEquals(ap.clickLogOut().isUserLoggedOut(), Constants_Class.APPLICATION_LOGOUT);		
	}
	
	@DataProvider
	public Object[][] getSearchKey()
	{
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Apple"},
			{"ChinTalk"},
			{"Samsung"}
		};
	}
	
	@Test(priority = 8, dataProvider = "getSearchKey")
	public void accountsPageSearchTest(String searchkey)
	{
		srp=ap.doSearch(searchkey);									// do search is returning search page class object, so we can access
																		// search page methods		
		Assert.assertTrue(srp.getSearchResultsCount()>0);				// if count > 0 , test will get passed
	}
}



























