package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.BaseTestPkg.BaseTest;
import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Feature 568393 - Implement Search Results Page Test Cases...")
@Story("Product Backlog Item - 839202- Search Resuls Page User Story...")
public class SearchResultsPageTest extends BaseTest{

	@BeforeClass
	public void searchPageSetup()
	{
//		ap=new AccountsPage(driver);			// we dont need to initialize it as it will be initialized in do login method return.
		ap=lp.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}
	
	
/*	
	@DataProvider
	public Object[][] getProductKey()
	{
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"iMac","iMac"},
			{"Sony","Sony VAIO"},
			{"ChinTalk","CHIN PC"},
			{"HP","HP LP3065"},
			{"Apple","Apple Cinema 30\""}		// giving 30\" because , that value is 30" so adding \ to ask java to consider it as string
		};
	}
	
	
*/
	
	@DataProvider
	public Object[][] getProductKey()
	{
		Object proddata[][]=ExcelUtil.getTestData(Constants_Class.EXCEL_PRODUCT_SHEET_NAME);
		return proddata;
	}

	

	@Description("This test is to assert if product searched is correct or not...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getProductKey")
	public void selectProductTest(String searchkey,String validationkey)
	{
		srp=ap.doSearch(searchkey);
		pip=srp.selectProduct(validationkey);
		String prodheadername=pip.getProductHeaderName();
		Assert.assertEquals(prodheadername, validationkey);
	}
	
}
