package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	//Here we need properties references as we have defined those parameters (headless and incognito) as properties.
													
		private Properties prop;				// creating object of Properties class to load properties from config.properties file
		private ChromeOptions co;				// we have to use ChromeOptions class from selenium to have headless and incognito functionality.
		private FirefoxOptions fo;
		
		public OptionsManager(Properties prop)		// defined constructor and will pass prop ref coming from another class
		{
			this.prop=prop;
		}
		
		public ChromeOptions getChromeOptions()				// writing for chrome browser
		{
			co=new ChromeOptions();			
			
			if(Boolean.parseBoolean(prop.getProperty("headless")))		// we need to convert to boolean from string to check if condition
			{
				co.setHeadless(true);									// use method addArguments / setHeadless
			}
			if(Boolean.parseBoolean(prop.getProperty("incognito")))
			{
				co.addArguments("--incognito");							// use method addArguments / setHeadless
			}
			return co;												// returning Chrome class object.
		}
		
		
		public FirefoxOptions getFireFoxOptions()				// writing for chrome browser
		{
			fo=new FirefoxOptions();			
			
			if(Boolean.parseBoolean(prop.getProperty("headless")))		// we need to convert to boolean from string to check if condition
			{
				fo.setHeadless(true);									// use method addArguments / setHeadless
			}
			if(Boolean.parseBoolean(prop.getProperty("incognito")))
			{
				fo.addArguments("--incognito");							// use method addArguments / setHeadless
			}
			return fo;												// returning Firefox class object.
		}
}
