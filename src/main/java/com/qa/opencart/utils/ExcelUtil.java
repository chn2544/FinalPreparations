package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	public static final String TEST_DATA_SHEET="./src/test/resources/testdata/TestData.xlsx";
											 // . means current project directory	
	
	private static Workbook book;
	private static Sheet sheet;					// remember to import from this - > apache.poi.ss.usermodel.Sheet;
	
	public static Object[][] getTestData(String sheetName)		// this method will take sheetname and return data and we will use it in data 
	{															// provider so we should return 2D Object Array	
		Object[][] exceldata=null;								// this 2D Array represents rows and columns
		
		// Now to make connection with Excel file we have to use FileInputStream class , ip will make connection with excel file
		
		try 
		{
			
			FileInputStream ip= new FileInputStream(TEST_DATA_SHEET);				// surrounding with try catch - if file is not available
			book=WorkbookFactory.create(ip); 										// create method will give access on workbook on that excel file. (where multiple sheets are present)
																					// and will return reference of Workbook class
			sheet=book.getSheet(sheetName);											// to go to specific sheet in workbook and will return reference of Sheet Class

			// now we need to read data in forms of row and columns
			
			exceldata=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()]; 		// initializing Object array with number or rows and columns in sheet 
			
			for(int i=0;i<sheet.getLastRowNum();i++)
			{
				for (int j=0;j<sheet.getRow(0).getLastCellNum();j++)
				{
					exceldata[i][j]=sheet.getRow(i+1).getCell(j).toString();					// this will capture value , taken i+1 as actual data is starting from second row
				}
			}
			
			return exceldata; 
		}
		
		
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (InvalidFormatException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;														
		
	}
	
}
