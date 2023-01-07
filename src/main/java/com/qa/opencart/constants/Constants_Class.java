package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class Constants_Class {

	// This is Test Driver Approach - to design test cases we are creating constants here 
	
	public final static String LOGIN_PAGE_TITLE="Account Login";	// final - so no one can change  and static - no need to create object of class 
	public final static String LOGIN_PAGE_URL_FRACTION="route=account/login";
	
	public final static String ACCOUNTS_PAGE_TITLE="My Account";
	public final static String ACCOUNTS_PAGE_URL_FRACTION="route=account/account";
	public final static String ACCOUNTS_PAGE_HEADER="naveenopencart";
	
	public static final List<String> EXPECTED_ACCOUNTS_SECTION_LIST = 
								Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");

	public static final String APPLICATION_LOGOUT="Account Logout";
	public static final String ACCOUNTS_PAGE_SEARCH_KEY="macbook";
	
	public static final String REGISTRATION_SUCCESS="Your Account Has Been Created";
	
	
	public final static int DEFAULT_WEB_ELEMENT_TIMEOUT=10;					// for webelements for all page classes
	public final static int DEFEAULT_NON_WEB_ELEMENT_TIMEOUT=5;				// For Non WebElement (Alerts etc)
	
	public static final String EXCEL_PRODUCT_SHEET_NAME = "products";
	public static final String EXCEL_REGISTER_SHEET_NAME = "register";
}
