package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.Map;

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


@Epic("Feature 9438375 - Implement Product Information Page Test Cases...")
@Story("Product Backlog Item - 938375- Product Information Page User Story...")
public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void ProductInfoPageSetup()
	{
		ap=lp.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}
	
	
	
	@DataProvider
	public Object[][] getProductKey()
	{
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"iMac","iMac",3},
			{"Sony","Sony VAIO",1},
			{"ChinTalk","CHIN PC",3},
			{"HP","HP LP3065",1},
			{"Apple","Apple Cinema 30\"",6}		// giving 30\" because , that value is 30" so adding \ to ask java to consider it as string
		};
	}
	
	@Description("This test is to get the total number of images for a product...")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getProductKey")
	public void productImageCountTest(String searchkey,String validationkey,int imgcount)
	{
		srp=ap.doSearch(searchkey);
		pip=srp.selectProduct(validationkey);
		int prodimgcount=pip.getImageCount();
		Assert.assertEquals(prodimgcount, imgcount);
	}
	

	@Description("This test is to assert metadata and pricing data for product...")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void productInfoTest()			// test case to validate meta data and price data
	{
		srp=ap.doSearch("Macbook");
		pip=srp.selectProduct("MacBook Pro");
		Map<String,String> actprodinfomap=pip.getProductInfoData();
		Assert.assertTrue(actprodinfomap.get("name").equals("MacBook Pro11"));					// we can write multiple assertions here,
		Assert.assertTrue(actprodinfomap.get("Brand").equals("Apple"));							// but if first one fails, method wont validate others
		Assert.assertTrue(actprodinfomap.get("Availability").equals("In Stock"));				// this is called Hard Assertion
		Assert.assertTrue(actprodinfomap.get("price").equals("$2,000.00"));
		
	}
	
	// To Bypass this hard assertion, we will use Soft Assertion
	

	@Description("SoftAssert - This test is to assert metadata and pricing data for product...")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void productInfoTestwithSoftAssert()			// test case to validate meta data and price data
	{
		srp=ap.doSearch("Macbook");
		pip=srp.selectProduct("MacBook Pro");
		Map<String,String> actprodinfomap=pip.getProductInfoData();
		saobj.assertEquals(actprodinfomap.get("name"),"MacBook Pro11");					//  if any one fails, method will validate others
		saobj.assertEquals(actprodinfomap.get("Brand"),"Apple");							// this is called Soft Assertion
		saobj.assertEquals(actprodinfomap.get("Availability"),"Is In Stock?");				
		saobj.assertEquals(actprodinfomap.get("price"),"$2,000.00");
		saobj.assertAll();													 			// mandatory to write, else it will pass tc, even if any 1 condition passes
	}
	
	// Let's Do the same with Data Provider
	
	//  -- assignment   -  if we pass key and value as 3rd and 4th parameter
	//	   how to handle it in assertequals for all 4 values, i.e. brand, availability, reward points, price 			
	
	

	@Description("SoftAssert - This test is to assert product description for product...")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void productInfoDescriptionTest()			// test case to validate product description
	{
		srp=ap.doSearch("Macbook");
		pip=srp.selectProduct("MacBook Pro");
		saobj.assertTrue(pip.getProductInfoInnertext().contains("For the ultimate creative canvas"));
		saobj.assertTrue(pip.getProductInfoInnertext().contains("Featuring 802.11n wireless technology"));
		saobj.assertTrue(pip.getProductInfoInnertext().contains("19-inch model with a 1920-by-1200"));
		saobj.assertAll();
	}
	

	@Description("This test is to assert additon of product to cart...")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void addToCartTest()
	{
		srp=ap.doSearch("Macbook");
		pip=srp.selectProduct("MacBook Pro");
		pip.enterQuantity("3");
		pip.addToCart();
		
		String checkmsg=pip.getSuccessMsg();
		saobj.assertTrue(checkmsg.contains("MacBook Pro"));
		String itemtext=pip.getCartItemsText();
		saobj.assertTrue(itemtext.contains("3 item(s)"));
	}
}
