package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameWorkException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	WebDriver driver;			// we will maintain driver here...
	Properties prop;			// creating object of Properties class to load properties from config.properties file
	OptionsManager om;			//		creating ref of OptionsManager to initialize it in init_driver method.
	
	private static ThreadLocal<WebDriver> tldriver=new ThreadLocal<WebDriver>();    // tldriver will have copy of that particular webdriver ref object.
	public static final Logger log=Logger.getLogger(DriverFactory.class);
		
//		 this method is used to initialized driver based on browser name
//		 and this method returns the driver.	
	
	public WebDriver init_driver(Properties prop)			
	{
		String browsername=prop.getProperty("browser").trim();
		String url=prop.getProperty("url").trim();
		
		om=new OptionsManager(prop);						// here we can pass prop reference coming in init_driver method;
		
		System.out.println("Browsername is : "+browsername);
		log.info("Browsername is : "+browsername+" --->Message Coming from Log4J DriverFactory Class");
		
		
		if(browsername.equalsIgnoreCase("chrome"))
		{
			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//remote execution on docker/cloud
				init_remoteDriver("chrome");
			}
			else
			{
				//local execution
				WebDriverManager.chromedriver().setup();
				tldriver.set(new ChromeDriver(om.getChromeOptions()));					// we are passing chromeDriver object value	
			}
			
		}
		else if(browsername.equalsIgnoreCase("firefox"))
		{
			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//remote execution on docker/cloud
				init_remoteDriver("firefox");
			}
			else
			{
				//local execution
				WebDriverManager.firefoxdriver().setup();
				tldriver.set(new FirefoxDriver(om.getFireFoxOptions()));					// we are passing FirefoxDriver object value	
			}
		}
		else if(browsername.equalsIgnoreCase("safari"))			// does not support headless mode
		{
//			driver=new SafariDriver();						// setup not req for safari
			tldriver.set(new SafariDriver());					// same for Safari
		}
		else
			System.out.println("Please pass correct browser... "+browsername+" is not a valid browser");
		
		getDriver().manage().deleteAllCookies();						// we will change now from driver to getDriver method as getDriver is returning thread-local driver
		getDriver().manage().window().maximize();
		getDriver().get(url);

		return getDriver();					// we are returning driver here to pass it further , now instead of driver we need to return thread-local driver
	}
	
	private void init_remoteDriver(String browserName)
	{
		System.out.println("Running Test Cases on Remote Environment... "+" on "+browserName);
		
		if(browserName.equalsIgnoreCase("chrome"))
		{
			try 
			{	// RemoteWebDriver provides a feature to run test cases on GRID machine /cloud machine, so we are using RemoteWebDriver ref here instead of chromeDriver.
				
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), om.getChromeOptions()));
			} 
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}

		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			try 
			{	// RemoteWebDriver provides a feature to run test cases on GRID machine /cloud machine, so we are using RemoteWebDriver ref here instead of chromeDriver.
				
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), om.getFireFoxOptions()));
			} 
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static WebDriver getDriver()			// we are making this static so that we can call it directly in other methods.
	{
		return tldriver.get();
	}
	
	public Properties init_prop()
	{
		prop=new Properties();	// create object.
		FileInputStream fo=null;
		
//		mvn clean install -Denv="qa"                -> -D is used for command line argument   and variable name as env
		String envName=System.getProperty("env");		
		System.out.println("Running Test Cases on "+envName+" Environment...");
		
		if(envName == null)
		{
			System.out.println("No Environment Defined... hence running test cases on QA Environment...");
			try 
			{
				fo=new FileInputStream("./src/test/resources/config/qa.config.properties");       // this will take path of config.prop file
				
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			try
			{
			switch (envName.toLowerCase()) 
			{
			case "qa":
						fo=new FileInputStream("./src/test/resources/config/qa.config.properties"); 
						break;
			case "dev":
						fo=new FileInputStream("./src/test/resources/config/dev.config.properties"); 
						break;
			case "stage":
						fo=new FileInputStream("./src/test/resources/config/stage.config.properties"); 
						break;
			case "uat":
						fo=new FileInputStream("./src/test/resources/config/uat.config.properties"); 
						break;
			case "prod":
						fo=new FileInputStream("./src/test/resources/config/prod.config.properties"); 
						break;
				
			default:
						System.out.println("Please pass the right environment name... "+envName+" is not valid");
						throw new FrameWorkException("---No such environment found---");
//						break;
			}
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			prop.load(fo);					// loading the properties now...
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return prop;
	}
	
	public String getScreenshot()
	{
		// first we need to type cast driver with TakesScreenshot and then wrap it getScreenshotAs method by giving outputtype as File 
		// TakesScreenShot is a interface having a single method getScreenshotAs
		
	  File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);				// right hand side will take screenshot and will store in srcFile
	  String path =	"./"+"Failed_TC_ScreenShots/"+ 	System.currentTimeMillis()+".png";		  // we want to store in specific path in FrameWork , here ./ ir project root, failed tc screenshots 
	  																							// folder will get created and screenshots will be stored with current timestamp and png extension
	  File destFile = new File(path);														// convert this path into File object ,so that we can move from srcFile to destination
	  try 
	  {
		FileUtils.copyFile(srcFile, destFile);
	  } 
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
	  
	  return path;											// we are returning path , because in extentreport listener class  , in onTestFailure/onTestSkipped method 
	  												// there is a  MediaEntityBuilder class having createScreenCaptureFromPath method which takes path where screenshot is available
	  												//	attach with failure screenshot and thats why ExtentReportListener class extends DriverFactory class
	
	}
	
}
