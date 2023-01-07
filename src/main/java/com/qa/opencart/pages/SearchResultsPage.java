package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eu;
	
	private By searchresults = By.cssSelector("div.product-layout.product-grid");
	
	public  SearchResultsPage(WebDriver driver)						
	{														
		this.driver=driver;									
		eu=new ElementUtil(driver);							 
	}					
	
	@Step("Function to check How Many Products are Returned...")	
	public int getSearchResultsCount()
	{
		List<WebElement> searchlist=eu.waitforElementsVisible(searchresults, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT);
		return searchlist.size();
		
	}
	
	@Step("Function to Select the Product...")	
	public ProductInfoPage selectProduct(String produtName)
	{
		By productlink=By.linkText(produtName);				// here we are creating by locator at run time based on productName received.
															// if we create at class level, that would be for a fixed product name
		eu.doClick(productlink);
		return new ProductInfoPage(driver);
	}	
	
}
