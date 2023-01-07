package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.BaseTestPkg.BaseTest;
import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationTest extends BaseTest {

	
	// here we dont need to login to app, as registration page is available on login page,
	// we dont need to create any setup here.. as by default we land on login page 
	//only pre condition here would be we should be landed on registration page
	
	@BeforeClass
	public void registrationSetup()
	{
		rup=lp.goToRegistration();					// will go to registration page.
	}
	
	@DataProvider
	public Object[][] getRegistrationUserData()
	{
		Object regdata[][]=ExcelUtil.getTestData(Constants_Class.EXCEL_REGISTER_SHEET_NAME);
		return regdata;	
	}
	
	public String createRandomEmail()
	{
		Random randomnum=new Random();
		String email="chnuser"+randomnum.nextInt(1000)+"@gmail.com";		// 1000 is bound value , i.e. numbers from 1 to 1000
		// so actual value will be chnuser1@gmail.com
		return email;
	}
	
	@Test(dataProvider = "getRegistrationUserData")
	public void registrationSuccessTest(String firstname,String lastname, String phone, String password,String subscribe)
	{
		boolean flag=rup.registerUser(firstname,lastname,createRandomEmail(),phone,password,subscribe);  
		Assert.assertTrue(flag);
		
	}
	
}
