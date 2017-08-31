package com.e2e.testUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import com.e2e.core.*;

import io.appium.java_client.android.AndroidDriver;

public class TestUtil extends TestBase {

	public static String mailscreenshotpath;
	public static String newdriver;
	public static String screenshotPath;
	public static String screenshotName;
	public static String destFilePath;
	
	TestBase testbase = new TestBase();
	
	public static String generateTimeStamp() {

		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH); // 3
		int year = cal.get(Calendar.YEAR); // 2014
		int sec = cal.get(Calendar.SECOND);
		int min = cal.get(Calendar.MINUTE);
		int date = cal.get(Calendar.DATE);
		int day = cal.get(Calendar.HOUR_OF_DAY);

		String timestamp = year + "_" + date + "_" + (month + 1) + "_" + day + "_" + min + "_" + sec;
		return timestamp;
	}

	public static void CaptureScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		//System.out.println(screenshotName);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
	}
	
	/*public static String Screenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		System.out.println(screenshotName);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\" + screenshotName));
		String filepath;
		return filepath = System.getProperty("user.dir") + "\\screenshots\\" + screenshotName;
	}*/
	
	public static boolean isTestCaseExecutable(String tcid) {

		for (int rowNum = 2; rowNum <= excel.getRowCount("test_suite"); rowNum++) {
			if (excel.getCellData("test_suite", "TCID", rowNum).equals(tcid)) {
				if (excel.getCellData("test_suite", "runmode", rowNum).equalsIgnoreCase("Y")) {
					if(excel.getCellData("test_suite", "TestingType", rowNum).equalsIgnoreCase("Web") && (Config.getProperty("Web").equalsIgnoreCase("Yes"))){
						System.out.println("Web is yes");
						return true;
					}else if(excel.getCellData("test_suite", "TestingType", rowNum).equalsIgnoreCase("Mobile") && (Config.getProperty("Android").equalsIgnoreCase("Yes"))){
						System.out.println("Android is yes");
						return true;
						
					}else if(excel.getCellData("test_suite", "TestingType", rowNum).equalsIgnoreCase("Database") && (Config.getProperty("Database").equalsIgnoreCase("Yes"))){
						System.out.println("Database is yes");
						return true;
						
					}else{
						System.out.println("Web/database/mobile is No");
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@DataProvider
	public static Object[][] getTestData(String TestCase) {

		// String SheetName = "TestData";
		// String TestCase = "LoginTest";
		// test starts from RowNum 1
		int testCaseRowNum = 1;
		System.out.println("Sheet name in data provider is"+TestDataSheetName);
		while (!excel.getCellData(TestDataSheetName, 0, testCaseRowNum).equalsIgnoreCase(TestCase)) {
			testCaseRowNum++;
		}
		// System.out.println("Test Case starts from:"+testCaseRowNum);
		// total columns And Rows for test data
		int DatacolStartRowNum = testCaseRowNum + 1;
		// System.out.println(DatacolStartRowNum);
		int TestDatastartRowNum = DatacolStartRowNum + 1;
		// System.out.println(TestDatastartRowNum);
		// Total Data Columns for test
		int testdatacols = 0;
		while (!excel.getCellData(TestDataSheetName, testdatacols, DatacolStartRowNum).trim().equals("")) {
			testdatacols++;
		}
		// System.out.println(excel.getCellData(SheetName, testdatacols,
		// DatacolStartRowNum));
		// System.out.println("Total columns ="+testdatacols);
		// Total Data Rows for the test
		int testdatarows = 0;
		while (!excel.getCellData(TestDataSheetName, 0, TestDatastartRowNum + testdatarows).trim().equals("")) {
			testdatarows++;
		}
		// System.out.println("Total rows ="+testdatarows);
		// Object[][] data = new Object[testdatarows][testdatacols];
		Object[][] data = new Object[testdatarows][1];
		int i = 0;
		for (int row = TestDatastartRowNum; row < TestDatastartRowNum + testdatarows; row++) {
			Hashtable<String, String> Testdatatable = new Hashtable<String, String>();
			for (int col = 0; col < testdatacols; col++) {
				// System.out.println(excel.getCellData(SheetName, col, row));
				// data[row-TestDatastartRowNum][col] =
				// excel.getCellData(SheetName, col, row);
				String Testdata = excel.getCellData(TestDataSheetName, col, row);
				String Columname = excel.getCellData(TestDataSheetName, col, DatacolStartRowNum);
				Testdatatable.put(Columname, Testdata);
			}
			data[i][0] = Testdatatable;
			i++;
		}
		return data;
	}

	public static Object[][] getData(String sheetName) {

		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum); // -2
			}
		}

		return data;
	}

	public static void zip(String filepath) {
		try {
			File inFolder = new File(filepath);
			File outFolder = new File("Reports.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data = new byte[1000];
			String files[] = inFolder.list();
			for (int i = 0; i < files.length; i++) {
				in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
				out.putNextEntry(new ZipEntry(files[i]));
				int count;
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebDriver SelectDriver(String BrowserName, String tcid, String Iteration)
			throws MalformedURLException {
		if (BrowserName.trim().equalsIgnoreCase("firefox")) {
			System.out.println("Start Firefox Driver");
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
			driver.manage().window().maximize();
		} else if (BrowserName.trim().equalsIgnoreCase("chrome")) {
			System.out.println("Driver for test case " + tcid + " with Iteration: " + Iteration + " is " + BrowserName);
			// System.out.println("Start chrome Driver");
			System.out.println("initialize Chrome driver1");
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver_2.29.exe");
			System.out.println("initialize Chrome driver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			//wait = new WebDriverWait(driver,5);
		}
		wait = new WebDriverWait(driver,5);
		return driver;
	}

	public static RemoteWebDriver SelectAndroidDriver(String BrowserName, String tcid, String Iteration)
			throws MalformedURLException {
		if (BrowserName.trim().equalsIgnoreCase("Android")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			System.out.println("Driver for test case " + tcid + " with Iteration :" + Iteration + " is " + BrowserName);
			capabilities.setCapability("BROWSER_NAME", BrowserName);
			capabilities.setCapability("VERSION", Config.getProperty("Android_Version"));
			capabilities.setCapability("deviceName", Config.getProperty("DeviceName"));
			capabilities.setCapability("platformName", Config.getProperty("PlatformName"));
			capabilities.setCapability("appPackage", Config.getProperty("AppPackage"));
			capabilities.setCapability("appActivity", Config.getProperty("AppActivity"));
			Androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			Androiddriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			// return Androiddriver;
		}else if (BrowserName.trim().equalsIgnoreCase("AndroidWeb")) {
			System.out.println("inside Adroid Web driver");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			System.out.println("Driver for test case " + tcid + " with Iteration :" + Iteration + " is " + BrowserName);
			capabilities.setCapability("BROWSER_NAME", "chrome");
			capabilities.setCapability("VERSION", Config.getProperty("Android_Version"));
			capabilities.setCapability("deviceName", Config.getProperty("DeviceName"));
			capabilities.setCapability("platformName", Config.getProperty("PlatformName"));
			capabilities.setCapability("appPackage", "com.android.chrome");
			capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
			Androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			Androiddriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		return Androiddriver;
	}
	
	
	public void ExecutionResult(String Tcase) throws MalformedURLException{
		String LastResult;
		ITestResult result1 = Reporter.getCurrentTestResult();
		System.out.println("test result in beginning is "+TestResult);
		LastResult = TestResult;
		if(result1.getStatus()== ITestResult.SUCCESS){
					TestResult = "Passed";
				}else if(result1.getStatus()== ITestResult.FAILURE){
					TestResult = "Failed";
				}else if(result1.getStatus()== ITestResult.SKIP ){
					TestResult = "Test Skipped";
				}
		if(TestResult!= null){
			System.out.println("I m not null");
			TestResult = TestResult+";"+LastResult;
		}else{
			System.out.println("i m null");
			TestResult = TestResult+";"+LastResult;
		}
		
		System.out.println("Test Results are "+TestResult);
		}
			
		
		
	public void UpdateExecutionResult(String Tcase,String TestCaseResult) throws MalformedURLException{
		String LastResult = "";
		String FinalResult ="";
		
		for (int rowNum = 2; rowNum <= excel.getRowCount("test_suite"); rowNum++) {
			String Excel_testcasename = excel.getCellData("test_suite", "TCID", rowNum);
			//System.out.println(Excel_testcasename);
			//System.out.println("Test vcase from the scripts is "+Tcase);
			if(Excel_testcasename.equalsIgnoreCase(Tcase)){
				//System.out.println("Execution for Iteration-"+rowNum+"is "+Tcase);
				String Hyperlink = System.getProperty("user.dir")+"\\target\\surefire-reports\\E2E POM Suite\\"+Tcase +" Test.html";
				ExecutionEndTime = getExecutionEndTime();
					//int ExecutionTime = Integer.parseInt(ExecutionEndTime)-Integer.parseInt(ExecutionStartTime);
					//if(TestResult == "Passed" || TestResult == "Failed"){
						LastResult = excel.getCellData("test_suite", "Result", rowNum);
						if(!LastResult.isEmpty()){
							TestResult = LastResult+";"+TestCaseResult;
							System.out.println("Test Result with last results "+TestResult);
							excel.setCellData("test_suite", "Result", rowNum, "Iteration "+Iteration+" - "+TestResult);
						}else{
							System.out.println("Test Result without last results "+TestResult);
							TestResult = TestCaseResult;
							excel.setCellData("test_suite", "Result", rowNum, "Iteration "+Iteration+" - "+TestResult);
						}
						
						if(TestResult.contains("Failed")){
							FinalResult = "Failed";
						}else if(TestResult.contains("Passed")){
							FinalResult = "Passed";
						}else{
							FinalResult = "Skipped";
						}
						//else if(TestResult.("Passed") )
						excel.setCellData("test_suite", "ExecutionStartTime", rowNum, ExecutionStartTime);
						System.out.println("Execution End time is"+ExecutionEndTime);
						excel.setCellData("test_suite", "ExecutionEndTime", rowNum, ExecutionEndTime);
						excel.setCellData("test_suite", "Result", rowNum, FinalResult);
						//excel.setCellData("test_suite", "Result Link", rowNum, Hyperlink);
						//excel.setCellData("test_suite", "ReportLink", rowNum, "Hello", Hyperlink);
						//URL url = new URL(Hyperlink);
						//UpdateExcel.setCellData("test_suite", "ReportLink", rowNum, url);
						//UpdateExcel.setCellData("test_suite", "ReportLink", rowNum, Hyperlink, Hyperlink);
						//UpdateExcel.setCellData("test_suite", "ReportLink", rowNum, Hyperlink, url);
						
						System.out.println("Test Result is updated successfuly for "+ Tcase);
						break;
					}
			}
				//}
			}
		
		public static void prepareResultSheet(){
		InputStream inStream = null;
		OutputStream outStream = null;
	 
	    	try{
	    		Date d = new Date();
	    		String fileName = "Result-"+d.toString().replace(":", "_").replace(" ", "_")+".xlsx";
	    	    destFilePath=DescXlsPath+fileName;
	    	    File afile =new File(ScrXlsPath);
	    	    File bfile =new File(destFilePath);
	    	    
	    	   // if(!bfile.exists())
	    	   // 	bfile.createNewFile();
	 
	    	    inStream = new FileInputStream(afile);
	    	    outStream = new FileOutputStream(bfile);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0){
	 
	    	    	outStream.write(buffer, 0, length);
	 
	    	    }

	    	    inStream.close();
	    	    outStream.close();

	    	    excel = new Xls_Reader(destFilePath);
	 
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
		}
		
		public static String getExecutionEndTime(){
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	        System.out.println("ExecutionEndTime---"+ sdf2.format(cal.getTime()) );
			ExecutionEndTime = sdf2.format(cal.getTime());
			return ExecutionEndTime;
		}
		
		public String getExecutionStartTime(){
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	        System.out.println("ExecutionStartTime---"+ sdf2.format(cal.getTime()) );
	        ExecutionStartTime = sdf2.format(cal.getTime());
			return ExecutionStartTime;
		}
		
		public String getDataSheetName(String tcid){
			String TestDataSheet = null;
			for (int rowNum = 2; rowNum <= excel.getRowCount("test_suite"); rowNum++) {
				if (excel.getCellData("test_suite", "TCID", rowNum).equals(tcid)) {
					String TestingType = excel.getCellData("test_suite", "TestingType", rowNum);
					if(TestingType.equalsIgnoreCase("Web")){
						TestDataSheet = "Web_TestData";
					}else if(TestingType.equalsIgnoreCase("Mobile")){
						TestDataSheet = "Mobile_TestData";
					}else if(TestingType.equalsIgnoreCase("Database")){
						TestDataSheet = "Database_TestData";
					}
			}
		}
			return TestDataSheet;
		}
		
	}
