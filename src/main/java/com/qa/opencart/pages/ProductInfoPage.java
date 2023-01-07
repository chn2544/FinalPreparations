package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants_Class;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eu;
	
	private By productheader = By.cssSelector("div#content h1");
	private By imgtest=By.cssSelector("ul.thumbnails img");
	private By productMetaData=By.xpath("(//div[@class='col-sm-4']//ul)[1]/li");
	private By productPricingData=By.xpath("(//div[@class='col-sm-4']//ul)[2]/li");
	private By quantity=By.xpath("//input[@name='quantity']");
	private By addtoCart=By.xpath("//button[@id='button-cart']");
	private By successmsg=By.cssSelector("div.alert.alert-success.alert-dismissible");		// locator to check if product is added to cart
	private By cartitemsvalidator=By.cssSelector("div#cart button.dropdown-toggle");		// to check if correct items are added to cart
	
	Map<String,String> productInfoMap;  //		 Declaring here so that  getProductPriceData and getProductMetaData , both methods can access it.
	
	ProductInfoPage(WebDriver driver)						
	{														
		this.driver=driver;									
		eu=new ElementUtil(driver);							 
	}					
	
	@Step("Function to get the name of the Product displayed in Header...")	
	public String getProductHeaderName()
	{
		return eu.waitForVisibilityOfElementExplicitWait(productheader, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).getText();
	}
	
	@Step("Function to get the Number of Images displyed for Product...")	
	public int getImageCount()
	{
		return eu.waitforElementsVisible(imgtest, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).size();
	}
	
	public Map<String, String> getProductInfoData()
	{
		// we will maintain a hashmap here based on key and value format , both values looks as string
		// and we will use top casting here.
		productInfoMap=new HashMap<String, String>();
		productInfoMap.put("name", getProductHeaderName()); 	// we are taking header of product as well , we dont have key, so using custom key
        getProductMetaData();
        getProductPriceData();
        productInfoMap.forEach((k,v)->System.out.println(k+":"+v));		// printing the map
		return productInfoMap;
	
	}
	
	@Step("Function to get Metadata for Product ...")	
	private void getProductMetaData()
	{
		// meta data 
      
        List<WebElement> metaDataList=eu.getElements(productMetaData);
		System.out.println("Total Size of MetaData is :"+metaDataList.size());
		
		// Brand: Apple
		// Product Code: Product 18
		// Reward Points: 1800
		//Availability: In Stock
		
		for(WebElement e: metaDataList)								// storing metadata list
		{
			String metaArray[]=e.getText().split(":");				// we want to split text as its coming in key value format seperated by colon
			String metaKey=metaArray[0].trim();						// Brand
			String metaValue=metaArray[1].trim();					// Apple	
			productInfoMap.put(metaKey, metaValue);					// insert above values in hashmap
		}
		
	}
	
	@Step("Function to get Pricing Data for Product...")	
	private void getProductPriceData()
	{
		// pricing data 
    
        // this data is not in key value format , so we wont split unnecessarily
		 List<WebElement> pricingDataList=eu.getElements(productPricingData);
		System.out.println("Total Size of PriceData is :"+pricingDataList.size());
		
		//size of pricelist will be 2 only, so we wont iterate with for loop unnecessarily
		String price=pricingDataList.get(0).getText();  // $2000.00   - no key here
		String ExTaxPrice=pricingDataList.get(1).getText();
		
		productInfoMap.put("price", price); 	// we are taking header of product as well , we dont have key, so using custom key
		productInfoMap.put("ExtPrice", ExTaxPrice);
		
	}
	
	
	public String getProductInfoInnertext()			// will give you text of entire page
	{
/*		selenium says if you want to execute a javascript method, you have to use javascriptexecutor interface,
		which has method to execute javascript  */
	
		//convert driver to javascriptexecutor interface , which will give reference of that interface
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String innertext=js.executeScript("return document.documentElement.innerText").toString();
		System.out.println("============== Start of Inner Text============================");
		System.out.println(innertext);
		System.out.println("============== End of Inner Text============================");
		return innertext;
	}	
		
	@Step("Function to enter number of items to add to cart...")	
	public void enterQuantity(String qty)
	{
		eu.doSendKeys(quantity, qty);
	}
	
	@Step("Function to click on Add to Cart Button ...")	
	public void addToCart()
	{
		eu.doClick(addtoCart);
	}
	
	@Step("Function to check if product was added successfully to cart...")	
	public String getSuccessMsg()
	{
		 // as page is getting refreshed, we will wait
		return eu.waitForElementPresentExplicitWait(successmsg, Constants_Class.DEFAULT_WEB_ELEMENT_TIMEOUT).getText();  
	}
	
	public String getCartItemsText()
	{
		return eu.getElement(cartitemsvalidator).getText();
	}
}
