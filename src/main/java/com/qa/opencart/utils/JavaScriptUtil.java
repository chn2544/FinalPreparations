package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	
	public JavaScriptUtil(WebDriver driver)
	{
		this.driver=driver;
	}
	
	public void flash(WebElement element)							// to see where driver is taking action - internally calls changeColor
	{																// to show demo to client
		String bgcolor=element.getCssValue("backgroundColor");
		for(int i=0;i<50;i++)
		{
			changeColor("rgb(0,200,0)",element);
			changeColor(bgcolor,element);
		}
	}
	
	private void changeColor(String color,WebElement element)						// to see where driver is taking action
	{
//		convert driver to javascript executor and it will become javascriptexecutor instance and it will be referred by reference variable of javascriptexecutor
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor= '" + color + "'" , element);
		
		try
		{
			Thread.sleep(20);
			
		}
		catch(InterruptedException e)
		{
			
		}
	 }

	
	public String getTitleByJS()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		return js.executeScript("return document.title").toString();		// we cannot use this string directly as this is javascript string
	}																		// we need to convert it to java string with tostring
	
	public void refreshBrowserByJS()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go(0)");
	}
	
	public void generateAlertByJS(String message)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("alert('" + message + "')");
	}
	
	public String getPageInnerText()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void clickElementByJS(WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()",element);
	}
	
	public void sendKeysUsingWithId(String id,String value)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.getElementById('" + id+ "').value='" + value + "'");
	}
	
	public void scrollPageDown()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollPageDown(String height)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}
	
	public void scrollPageUp()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}
	
	public void scrollIntoView(WebElement element)							// to stop scrolling when element is located...
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	
	public void drawBorder(WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}
	
	public void waitForPageLoaded()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String loadingStatus=js.executeScript("return document.readyState;").toString();
		
		if(loadingStatus.equals("complete"))
		{
			System.out.println("Page is fully loaded...");
		}
		
		else
		{
			for(int i=1;i<=20;i++)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				loadingStatus=js.executeScript("return document.readyState;").toString();
				System.out.println("Current Page Loading Status : "+loadingStatus);
				if(loadingStatus.equals("complete"))
				{
					break;
				}
			}
		}
	}
	
}
