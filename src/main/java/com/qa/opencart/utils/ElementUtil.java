package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {   // Element utility having method to find webElement, find multiple WebElements, sendkeys, click on webelement, getattribute
							 // isdisplayed, isenabled, get by etc.
		
	
	private WebDriver driver;    // we would need driver here to take actions on webelement
	
//  -------------------------- Constructor---------------------------------------
	
	public ElementUtil(WebDriver driver)
	{
		this.driver=driver;              // this line will give driver to ElementUtil which will be passed from Test Class (while creating ElementUtil object)
	}

//  --------------------------- Find Single WebElement-----------------------------------------------------
	
	public WebElement getElement(By locator)          // removed static because we want to call it via ElementUtil object 
	{ 												// this method will take By lcoator and return webelement 
		return driver.findElement(locator);
		 
	}
	
//  --------------------------------- Get List of WebElements------------------------------------------------
	
	public List<WebElement> getElements(By locator)          // method for getting multiple elements
	{
		return driver.findElements(locator);
	}

//  --------------------------------- Get count of WebElements------------------------------------------------	
	
	public  int getElementsCount(By locator)                   // to get count of webelements based on By locator 
	{
		return getElements(locator).size();
	}
	
//  --------------------------------- Send Keys ------------------------------------------------	
	
	public void doSendKeys(By locator,String key)     // doSendKeys will take By locator for getElement and
	{												  // take key to be entered.
		WebElement elem=getElement(locator);
		elem.clear();
		elem.sendKeys(key);
	}
		
//  --------------------------------- Click on a Web Element ------------------------------------------------
	
	public void doClick(By locator)					// method to click on a button / webelement
	{
		getElement(locator).click();
	}
	
//  --------------------------------- Get text of a Web Element------------------------------------------------
	
	public String doGetText(By locator)
	{
		return getElement(locator).getText();
	}
	 
//  --------------------------------- Check if a Webelement is displayed on a web page ------------------------------------------------	
	
	public boolean doIsDisplayed(By locator)       // method to check if element is displayed on page
	{
		return getElement(locator).isDisplayed();
	}
	
//  --------------------------------- Check if a WebElement is enabled on a web page -----------------------------------------------
	
	public boolean doIsEnabled(By locator)            // method to check if element is enabled on the page
	{
		return getElement(locator).isEnabled();
	}
	
//  ---------------------------------------------------- Method to Check if element is displayed or not using core selenium feature----------------------------------------	
	
	public  boolean isDisplayedwithSelenium(By locator)
	{
		WebElement ele=getElement(locator);
		List<WebElement> elelist=getElements(locator);
		if(elelist.size()>0)										// taking list of webelements and comparing it with size, here it will be 1
		{
			if(ele.isDisplayed())									// and then finally checking if element is displayed or not
			{
				return true;
			}
		}
			return false;
		
		
	}	
	
//  --------------------------------- Get attribute of a webelement ------------------------------------------------	
	
	public String doGetAttribute(By locator,String atrkey)  //  method to return attribute value based on attribute key and locator passed.
	{
		return getElement(locator).getAttribute(atrkey);
	}
		
//   ------------------------------------ Send Keys method for String locator type (not much used and not much important) ------------------------------
	
	public void doSendKeys(String locatorType,String locatorValue,String key)     // overloaded this method for String locator
	{												  
		getElement(getBy(locatorType, locatorValue)).sendKeys(key);
	}
	
//  -------------------------------------------- Get List of attributes of WebElements----------------------------------------------------------
	
	public  List<String> getElementsAttributeList(By locator,String attrname)          // get list of attributes of webelements
	{
		List <WebElement> eleList=getElements(locator);                   // to get list of webelements from getElement method
		//topcasting
		List<String> strvalue=new ArrayList<String>();              	  // to store attribute values of all elements in arraylist	
		for(WebElement e:eleList)										 // to get attribute for each element
		{
			String atrval=e.getAttribute(attrname);
			strvalue.add(atrval);										// to store attribute values of all elements in arraylist
		}
		return strvalue;											// returning arraylist having attribute values.
	}

//  -------------------------------------------- Get List of text of Webelements-----------------------------------------------------------------	

	public  List<String> getElementsTextList(By locator)							// get list of text of webelements
	{
		List <WebElement> eleList=getElements(locator);                   // to get list of webelements from getElement method
		//topcasting
		List<String> eleTextList=new ArrayList<String>();              	  // to store text of all elements in arraylist	
		for(WebElement e:eleList)										 // to get text for each element
		{
			String text=e.getText();
			eleTextList.add(text);										// to store text of all elements in arraylist
		}
		return eleTextList;											// returning arraylist having link text
	}

//   --------------------------------------------------- Get List of suggesstions returned from google after searching for a key------------------------------	
	
	public  void getGoogleSuggesstionList(By searchbox,By searchlistlocator,String searchkey) throws InterruptedException    // this function will get list of webelements from search key and then 
	{																					// will take text of each element and will return that texts in form of arraylist
		doSendKeys(searchbox, searchkey);												// entering search data inside the google search box
		Thread.sleep(3000);
		List<WebElement> searchlist=getElements(searchlistlocator);      // taking webelements in a list
		List<String> suggvallist=new ArrayList<String>();				// arraylist to store text of webelements
		for(WebElement e:searchlist)
		{
			String text=e.getText();									//taking text
			System.out.println(text);                                    //printing it
			suggvallist.add(text);										//storing text in arraylist
		}

		
	}
	
//	------------------------------------------------------ Selecting a option from 	suggesstions returned from google after searching a key --------------------------------------
	
	public  void selectGoogleSearchOption(By searchbox,By searchlistlocator,String searchkey,String selectkey) throws InterruptedException    // this function will get list of webelements from search key and then 
	{																					// will take text of each element and will return that texts in form of arraylist
		doSendKeys(searchbox, searchkey);												// entering search data inside the google search box
		Thread.sleep(3000);
		List<WebElement> searchlist= getElements(searchlistlocator);    // taking webelements in a list
		
		for(WebElement e:searchlist)
		{
			String text=e.getText();										//taking text
			if(text.equals(selectkey))						//comparing with some string
			{
			e.click();														//selecting that value
			break;
			}
		}

		
	}
	
// ---------------------------------------------------------------------------DROPDOWN METHODS----------------------------------------------------------------------------------------	

//  ---------------------------------------------------- Method to select a dropdown value based on Index------------------------------------------
	
	public  void selectDropDownByIndex(By locator,int index)				// takes by locator and index to be selected
	{
		Select selectobj=new Select(getElement(locator)); 						// calling getelement method as select class constructor needs webelement to be passes
		selectobj.selectByIndex(index);											//selecting the dropdown index.	
	}
	
//  ---------------------------------------------------- Method to select a dropdown value based on Visible Text------------------------------------------
	
	public  void selectDropDownByVisibleText(By locator,String text)				// takes by locator and text to be selected
	{
		Select selectobj=new Select(getElement(locator)); 						// calling getelement method as select class constructor needs webelement to be passes
		selectobj.selectByVisibleText(text);											//selecting the dropdown bsaed on visible text.	
	}
	
//  ---------------------------------------------------- Method to select a dropdown value based on value ------------------------------------------
	
	public  void selectDropDownByValue(By locator,String val)				// takes by locator and value to be selected
	{
		Select selectobj=new Select(getElement(locator)); 						// calling getelement method as select class constructor needs webelement to be passes
		selectobj.selectByValue(val);											//selecting the dropdown bsaed on value.	
	}

//  ---------------------------------------------------- Method to select get list of values in  dropdown ------------------------------------------
	
	public  List<WebElement> selectDropDownAllOptions(By locator)				// takes by locator and value to be selected
	{
		Select selectobj=new Select(getElement(locator)); 						// calling getelement method as select class constructor needs webelement to be passes
		List<WebElement> dropdownlist=selectobj.getOptions();											//selecting the dropdown bsaed on value.	
		return dropdownlist;
	}
	
//  ---------------------------------------------------- Method to select a value from list of values in  dropdown ------------------------------------------
	
	public  void selectDropDownValueWithAllOptions(By locator,String val)				// takes by locator and value to be selected
	{   
		Select selectobj=new Select(getElement(locator)); 						// calling getelement method as select class constructor needs webelement to be passes
		List<WebElement> dropdownlist=selectobj.getOptions();											//selecting the dropdown bsaed on value.	
		for(WebElement e:dropdownlist)
		{
			String text=e.getText();
			if(text.equals(val))
			{
				e.click();
				break;
			}	
		}
	}
	
//  ---------------------------------------------------- Method to select a dropdown value with xpath (without select class) ------------------------------------------
	
	public  void selectDropDownusingXpath(By locator,String val)				// takes by locator and value to be selected
	{
		List<WebElement> listofcountries=getElements(locator);
	
		for(WebElement e:listofcountries)
		{
			String text=e.getText();
			if(text.equals(val)) 
			{
				e.click();
				break;
			}
	    }
	}
	
//  ---------------------------------------------------- Method to get count of number of options in dropdown ------------------------------------------
	
	public  int selectDropDownValuesCount(By locator)					// Method to get count of number of options in dropdown
	{
		Select selobj=new Select(getElement(locator));
		int count=selobj.getOptions().size();
		return count;
	}

//  ---------------------------------------------------- Method to select choice from dropdown which is not having select html-----------------------------------------
	
	public void selectChoice(By locator,String value)					// method for single selection
	{
		List<WebElement> choicelist=getElements(locator);					// getting list of choices
		System.out.println(choicelist.size());
		boolean flag=false;
		for(WebElement e:choicelist)
		{
				String text=e.getText();
				System.out.println(text);
				if(text.trim().equals(value))										
				{
					flag=true;
					e.click();												// selecting the choice...
					break;
				}
		}
		
		if(flag==false)
		{
			System.out.println("Choice is not available for selection : "+value);
		}
		
	}
	
	

//  ---------------------------------------------------- Method to select multiple choices from dropdown which is not having select html-----------------------------------------
	
	public void selectMultipleChoice(By locator,String... value)					// method for multiple selection , variable value will behave as array
	{																		// Note : String... value should  be the last parameter always		
		List<WebElement> choicelist=getElements(locator);					// getting list of choices
		System.out.println(choicelist.size());
		boolean flag=false;
		if(!value[0].equals("all"))											// to handle condition for selection of all selection test case.
		{ 
		
			for(WebElement e:choicelist)
			{
					String text=e.getText();
					System.out.println(text);
						
					for(int i=0;i<value.length;i++)								// we need to iterate string value array as well  as logic in selectChoice wont work here as we are passing array now.
					{
						if(text.equals(value[i]))								
						{
							flag=true;														// element is found
							e.click();
							break;
						}
					}
					
			}													//closure of outer for		
		}													//closure of if
		else
		{
			for(WebElement e:choicelist)									// if all is passed, click on everything.
			{
			e.click();
			flag=true;														// element is found
			}
		}
		
		if(flag==false)
		{
			System.out.println("Choice is not available for selection : "+value[0]);
		}
	}
	
	
//  ---------------------------------------------------- Method to select parent and child menu and sub menu using Actions class-----------------------------------------	
	
	public  void MenuHandling(By ParentMenu,By ChildMenu) throws InterruptedException
	{
		Actions act=new Actions(driver);
		act.moveToElement(getElement(ParentMenu)).build().perform();						// movetoelement takes a webelement so calling getelement inside it
		Thread.sleep(1500);
		getElement(ChildMenu).click();
		
		
	}
	
	
//  ---------------------------------------------------- Method to select child menu  having multiple parents using Actions class-----------------------------------------	
	
	public void multiLevelMenuHandling (By ParentMenu,By level1,By level2,By level3, By menutoclick) throws InterruptedException
	{
		Actions act=new Actions(driver);
		act.moveToElement(getElement(ParentMenu)).build().perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level1)).build().perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level2)).build().perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level3)).build().perform();
		
		getElement(menutoclick).click();
		
	}
	
//  ---------------------------------------------------- Method to  right click on a option and get list of options displayed after right click ----------------------------------------	
	
	public List<String> getRightClickMenuItemsList(By rightclickoption,By listOfItems)
	{
		List<String> rightclicklist=new ArrayList<String>();
		Actions act=new Actions(driver);
		act.contextClick(getElement(rightclickoption)).perform();             //  opening right click menu options
		List<WebElement> list= getElements(listOfItems);					// getting list of options occuring after right click
		
		for(WebElement e:list)
		{
			String text=e.getText();
			rightclicklist.add(text);
		}
		return rightclicklist;
		
	}
	
//  ---------------------------------------------------- Method to  right click on a option and right click on one of the menu ----------------------------------------	
	
	public void RightClickMenuItem(By rightclickoption,By rightclickonmenu)
	{
		Actions act=new Actions(driver);
		act.contextClick(getElement(rightclickoption)).perform();             //  opening right click menu options
		getElement(rightclickonmenu).click();
	}
	
	
//  ---------------------------------------------------- Method to  Drag and Drop Fcuntionality ----------------------------------------	
	
	public void DragAndDrop(By source,By target)
	{
		Actions act=new Actions(driver);
		act.clickAndHold(getElement(source)).moveToElement(getElement(target)).release().build().perform();
		
	}
	
//  ---------------------------------------------------- Method to  perform send keys via action class----------------------------------------	
	
	public  void doSendKeysActionClass(By locator,String value)
	{
		Actions act=new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
//  ---------------------------------------------------- Method to  perform click  via action class----------------------------------------	
	
	public  void doClickActionClass(By locator)
	{
		Actions act=new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
//  ---------------------------------------------------- Method to  click on checkbox in webtable (xpath implementation)---------------------------------------	
//   https://selectorshub.com/xpath-practice-page/
	
	public  void WebTableCheckBoxClick(String username)
	{
		WebElement checkbox=driver.findElement(By.xpath("//a[text()='"+username+"']/../preceding-sibling::td//input[@type='checkbox']"));    // remember to include parameter in '"+parameter+"' format
		checkbox.click();
	}
	
//  ---------------------------------------------------- Method to  get score of a player from cricbuzz match---------------------------------------	
//  https://www.cricbuzz.com/live-cricket-scorecard/49509/nz-vs-ind-1st-odi-india-tour-of-new-zealand-2022
	
	public  List<String> getScoreForPlayer(String playername)
	{
		System.out.println("Getting Scores for Player : "+playername);
		List<WebElement> listofscore=driver.findElements(By.xpath("(//a[normalize-space()='"+playername+"'])[1]/parent::div/..//div//following-sibling::div"));    // remember to include parameter
		List<String> stringofscores=new ArrayList<String>();                                                                                                       // in '"+parameter+"' format 
		for(WebElement e:listofscore)
		{
			String scorevar=e.getText();
			stringofscores.add(scorevar);
		}
		return stringofscores;
		
			
		
	}	
	
//  ---------------------------------------------------- Method to  get print Amazon Footer List Using Xpath ---------------------------------------	

	
	public  List<String> getAmazonFooterListLinkText(String footerHeader)
	{
		System.out.println("Getting List for Footer Links of Header : "+footerHeader);
		List<WebElement> listoffooterlinks=driver.findElements(By.xpath("//div[text()='"+footerHeader+"']/../ul/li"));    // remember to include parameter  in '"+parameter+"' format
		List<String> footerText=new ArrayList<String>();                                                                                                        
		for(WebElement e:listoffooterlinks)
		{
			String footertextitems=e.getText();
			footerText.add(footertextitems);
		}
		return footerText;
		
	}	

//  ---------------------------------------------------- Method to  get print all elements of static webtable from w3 school ---------------------------------------	

	
	public  List<String> printStaticWebTable(By rows,By columns)
	{
		
		int rowcount=getElementsCount(rows);
		System.out.println("Total Rows : "+rowcount);
		int colcount=getElementsCount(columns);
		System.out.println("Total Columns : "+colcount);
		
		System.out.println("Printing the WebTable...");
		List<WebElement> listofwebtableitems=driver.findElements(By.xpath("//table[@id='customers']//tr/following-sibling::tr//td"));    // xpath configuration is very very important
		List<String> footerText=new ArrayList<String>();                                                                                                        
		for(WebElement e:listofwebtableitems)
		{
			String webtableitems=e.getText();
			footerText.add(webtableitems);
		}
		return footerText;
		
	}	
	


//------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	public By getBy(String locatorType,String locatorValue)			// no need to check this method in detail as this was written for approach 7
	{
		By locator=null;
		switch (locatorType.toLowerCase()) {
		case "id":
			locator=By.id(locatorValue);
			break;
		case "name":
			locator=By.name(locatorValue);
			break;
		case "classname":
			locator=By.className(locatorValue);
			break;
		case "xpath":
			locator=By.xpath(locatorValue);
			break;
		case "cssSelector":
			locator=By.cssSelector(locatorValue);
		case "linktext":
			locator=By.linkText(locatorValue);
		case "partialLinkText":
			locator=By.partialLinkText(locatorValue);
		case "tagname":
			locator=By.tagName(locatorValue);
			
		default:
			break;
		}
		return locator;
	}

	
//  //********************************************************************** WAIT UTILITIES ***********************************************************************************************
	
	public  WebElement waitForElementPresentExplicitWait(By locator,int timeout)
	{
		// I will take by locator for whom i need to wait and timeout
		// An expectation for checking that an element is present on the DOM of a page. This does notnecessarily mean that the element is visible
		// Not Necessary element will be visible on the page or not.
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public  WebElement waitForVisibilityOfElementExplicitWait(By locator,int timeout)
	{
		// I will take by locator for whom i need to wait and timeout
		// An expectation for checking that an element is present on the DOM of a page and visible.Visibility means that the element is not only displayed
		//but also has a height and width that isgreater than 0.
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public String waitForTitleExplicitWait(String titlematch,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		Boolean success=wait.until(ExpectedConditions.titleContains(titlematch));
		
		if(success)
		{
			return driver.getTitle();
		}
		return null;
		
	}
	
	public String waitForURLExplicitWait(String urlmatch,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		Boolean success=wait.until(ExpectedConditions.urlContains(urlmatch));
		
		if(success)
		{
			return driver.getCurrentUrl();
		}
		return null;
		
	}
	
	public Alert waitForAlert(int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.alertIsPresent());
			
		// with above lines of code we wont need to switch to alert, as we are storing output of until method in alert class object
	}
	
	public void alertAccept(int timeout)
	{
		waitForAlert(timeout).accept();
	}

	public void alertDismiss(int timeout)
	{
		waitForAlert(timeout).dismiss();
	}
	
	public String alertGetText(int timeout)
	{
		return waitForAlert(timeout).getText();
	}
	
	public void alertSendKeys(int timeout,String keys)
	{
		waitForAlert(timeout).sendKeys(keys);
	}
	
	public void waitForFrame(By locator,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));                 // so until method will wait for 10 seconds and it will switch to frame also
																								// 	and it will give driver which would be inside frame (no need to write switch to)
	}
	
	public void clickElementWhenReady(By locator,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();                            		// checks if element is visible and enable
	}
	
	public List<WebElement> waitforElementsVisible(By locator,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> footerlist=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));    // no need to write driver.findelements seperately
		return footerlist;

	}
	
	public List<WebElement> waitforElementsPresent(By locator,int timeout)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> footerlist=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));    // no need to write driver.findelements seperately
		return footerlist;

	}
	
	public void waitForElementToBeClickable(By locator,int timeout,int pollingtime) 								// For Polling Time.
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofMillis(pollingtime));		// so 10 attempts will be performed as interval is of 10 seconds
		// default polling time is 500 ms
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

	}

	public void waitForElementPresencewithFluentWait(By locator,int timeout, int pollingtime,String msg)
	{
		Wait<WebDriver> waitref=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
				// to ignore any excepting coming withing polling intervall till whole timeout is reached.
				.withMessage(msg);
		
		waitref.until(ExpectedConditions.presenceOfElementLocated(locator)).click();

	}
	
	public WebElement customWaitretryingElement(WebDriver driver,By locator, int timeout, int polling) throws InterruptedException
	{
		WebElement element= null;
		int attempts=0;
		
		while (attempts<timeout)
		{
			try 
			{
			element=driver.findElement(locator);
			element.click();
			break;
			}
			catch(NoSuchElementException e)			// Dont Know why but code is not jumping into catch block and going to break statement only.
			{
				System.out.println("Element is not found in attempt :"+attempts+"for :"+locator);
				try
				{
					Thread.sleep(polling);
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
				
			}
			attempts++; 
		}
		if(element==null)					// after complete while loop if element is not found.
		{
			try 
			{
			throw new Exception("Element not found Here");
			}
			catch(Exception e)
			{
				System.out.println("Element is not found in attempt... tried for  :"+timeout+" secs with interval of "+polling+"ms");
			}
		}
		return element;
	}		
	
	
	public  void waitForPageLoad(int timeout)
	{
		long endTime=System.currentTimeMillis()+timeout;
		while(System.currentTimeMillis()<endTime)
		{
			// Now we want to execute the script to check if document.readystate gives us complete
			// so we need to create reference of JavaScriptExecutor Interface for this as follows -
			
			JavascriptExecutor js=(JavascriptExecutor)driver;
			String status=js.executeScript("return document.readyState").toString();
			System.out.println("State is : "+status);
			if(status.equals("complete"))
			{
				System.out.println("Page is fully loaded now...");
				break;
			}
			
		}
	}
	
	
	
	
}

