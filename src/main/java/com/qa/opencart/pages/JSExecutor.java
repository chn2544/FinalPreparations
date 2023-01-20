package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.qa.opencart.utils.JavaScriptUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class JSExecutor {


	static WebDriver driver;
	public static void main(String[] args)  throws InterruptedException {
		
		// For SVG elements we have to create xpath only.

		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		JavaScriptUtil ju=new JavaScriptUtil(driver);
		driver.get("https://www.orangehrm.com/orangehrm-30-day-trial/");
		
//		Title of Page
		String titleofpage=ju.getTitleByJS();
		System.out.println("Title of the page is : "+titleofpage);
		
//		Generate Alert
//		ju.generateAlertByJS("This is Alert from Chinmay");						// commenting as of now to not get alert in between execution
//		Real Time Example can be - you can generate alert during demo , like landed on home page, login page etc.		
		
//		Getting Inner Text of Web Page
		String innertextofpage=ju.getPageInnerText();
		System.out.println("Inner Text ---  : \n"+innertextofpage);
		if(innertextofpage.contains("Shield"))
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
		
//		Refresh the page
//		ju.refreshBrowserByJS();						// commenting it out to avoid unnecessary refresh

//		Draw Border to a WebElement
//		Use Case - bordering the element for a failed functionality.		
		
/*		WebElement fullname=driver.findElement(By.xpath("//input[@name='Name']"));
		ju.drawBorder(fullname);														*/
		
		
//		To Flash the webelement with different color.	
//		Practical use - while demoing certain functionality to client, flash can be used
//		Or to know where driver is availale 
		
/*		WebElement fullname2=driver.findElement(By.xpath("//input[@name='Name']"));
		ju.flash(fullname2);																					*/
		
//		Click on a webelement using JS Executor		

/*		WebElement submitbtn=driver.findElement(By.xpath("//input[@type='submit']"));
		ju.clickElementByJS(submitbtn);																					*/

/*		driver.findelements.click - if element is visible on page , then selenium will perform .click
		for actions.click - it will move to middle of element and will click, but again element should be visible here
		for JSexecutor - it goes in DOM of page - and see if there is such element and click on it but not necessary it is visible on page (can be hidden as well)
		due to this reason , it is not recommended.
		will use this in case of exception element not interactableA
*/		
		
		
		driver.navigate().to("https://www.amazon.in/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		ju.scrollPageDown();
		Thread.sleep(2000);
		ju.scrollPageUp();
		Thread.sleep(2000);
// 		to go to specific height
		ju.scrollPageDown("800");
//		scroll into view - to scroll till specific element is reached		
		WebElement washingmachine=driver.findElement(By.xpath("//h2[contains(text(),'washing machines')]"));
		ju.scrollIntoView(washingmachine);
		System.out.println(washingmachine.getText());
		Thread.sleep(2000);
		ju.scrollPageUp();
		
//		send keys via JS
//		again not recommended as js will work from DOM side (even if element is not visible on UI)
		ju.sendKeysUsingWithId("twotabsearchtextbox","books");
		
		
//		Capture value from text box -
		WebElement searchbox=driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
//		gettext will not work here
//		System.out.println(searchbox.getText());
		String val=searchbox.getAttribute("value");
		System.out.println("Entered Value is "+val);
	
		
	}

}