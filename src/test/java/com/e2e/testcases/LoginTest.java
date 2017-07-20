package com.e2e.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.velocity.runtime.directive.Parse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.e2e.core.TestBase;
import com.e2e.pageoperations.HomePage;
import com.e2e.testUtils.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends TestBase {
	// public static WebDriver driver;
	TestUtil testutil = new TestUtil();
	HomePage hp = new HomePage();

	@BeforeTest
	public void isSkip() {
		ExecutionStartTime = testutil.getExecutionStartTime();
		// final long start = System.currentTimeMillis();
		System.out.println("Start value is --");
		String TestName = this.getClass().getName();
		CurrentTestName = TestName.substring(TestName.lastIndexOf('.') + 1);
		TestDataSheetName = testutil.getDataSheetName(CurrentTestName);
		System.out.println("test sheet is---" + TestDataSheetName);
		if (!TestUtil.isTestCaseExecutable(CurrentTestName)) {
			throw new SkipException("Skipping Test case as runmode is set to No");
		}
	}

	@Test(dataProvider = "getData")
	public void logintest(Hashtable<String, String> testdata, Method method) throws IOException, InterruptedException {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Iteration = testdata.get("Iteration");
		System.out.println("Iteration is" + Iteration);
		//CurrentIteration = Integer.parseInt(Iteration);
		//System.out.println("CurrentIteration is  "+CurrentIteration);
		String runmode = testdata.get("RunMode");
		if (runmode.trim().equals("Y")) {
			if (driver == null) {
				System.out.println("yes");
				driver = TestUtil.SelectDriver(testdata.get("Browser"), testdata.get("TestData"),
						testdata.get("Iteration"));
				System.out.println("Driver for test case LoginTest with Iteration: " + testdata.get("Iteration")
						+ " is " + testdata.get("Browser"));
				driver.get(testdata.get("URL"));
				driver.manage().window().maximize();
				//driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")),
						//TimeUnit.SECONDS);
				TestUtil.CaptureScreenshot();
			} else {
				System.out.println("no for Iteration: " + testdata.get("Iteration"));
				driver.get(testdata.get("URL"));
				//driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")),
					//	TimeUnit.SECONDS);
				TestUtil.CaptureScreenshot();
			}
		} else {
			throw new SkipException("Skipping Test case Iteration as runmode is set to No");
		}
		Thread.sleep(3000);
		//app_logs.debug("Execution started for Login Test Case Iteration: " + testdata.get("Iteration"));
		//app_logs.debug("Step1 : click Manager Button");
		System.out.println("Before click");
		hp.clickManagerLink();
		System.out.println("clicked");
		Thread.sleep(3000);
		//app_logs.debug("Execution finished for Login Test Case");
		TestUtil.CaptureScreenshot();
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		test.log(LogStatus.PASS, "Manage Home page is displayed successfuly");
		Reporter.log("Execution finished for Login Test Case");
		tearDown();
	}

	@DataProvider
	public static Object[][] getData() {
		System.out.println("Name is data source is " + CurrentTestName);
		return TestUtil.getTestData(CurrentTestName);
	}
	
	public void tearDown(){
		System.out.println("I am in tearDown Test");
		if (Config.getProperty("browser").equals("") && (driver!=null)) {
			// driver.quit();
			driver.close();
			driver.quit();
			driver=null;  
			System.out.println("Closing Browser from Login test");
			// String url = "file:///D:\\Selenium
			// Practise\\E2E_DataDriven_Framework\\test-output\\E2E Data Driven
			// Suite\\Add Customer Test.html";
			// excel.setCellData("test_suite", "ReportLink", 2, "click");
			System.out.println("I am in After tearDown");
		}
	}

	@AfterTest
	public void closebrowser() {
		System.out.println("I m in After Test");
			try {
				testutil.UpdateExecutionResult(CurrentTestName);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
