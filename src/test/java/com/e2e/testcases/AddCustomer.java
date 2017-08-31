package com.e2e.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.e2e.core.TestBase;
import com.e2e.pageoperations.HomePage;
import com.e2e.pageoperations.ManageHomePage;
import com.e2e.testUtils.*;
import com.relevantcodes.extentreports.LogStatus;





//import test_utils.TestUtil;

public class AddCustomer extends TestBase{
	
	TestUtil testutil = new TestUtil();
	HomePage hp = new HomePage();
	ManageHomePage MP = new ManageHomePage();
	
	@BeforeTest
	public void isSkip(){
		TestDescription = "This is the test for Add Customer";
		ExecutionStartTime = testutil.getExecutionStartTime();
		// final long start = System.currentTimeMillis();
		System.out.println("Start value is --");
		//String TestName = this.getClass().getName();
		//CurrentTestName = TestName.substring(TestName.lastIndexOf('.') + 1);
		CurrentTestName = getTestCaseName();
		TestDataSheetName = testutil.getDataSheetName(CurrentTestName);
		System.out.println("test sheet is---" + TestDataSheetName);
		if (!TestUtil.isTestCaseExecutable(CurrentTestName)) {
			test = rep.startTest(CurrentTestName.toUpperCase(), " - "+TestDescription);
			throw new SkipException("Skipping Test case as runmode is set to No");
		}
	}
	
	
	@Test(dataProvider="getData")
	public void addNewCustomer(Hashtable<String, String> testdata,Method method) throws InterruptedException, IOException{
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Iteration = testdata.get("Iteration");
		//CurrentTestName = getTestCaseName()+" - "+ Iteration;
		//CurrentTestName = CurrentTestName+" - "+ Iteration;
		String runmode = testdata.get("RunMode");
		if(runmode.trim().equals("Y")){
			if(driver==null){
				System.out.println("yes");
				driver = TestUtil.SelectDriver(testdata.get("Browser"),testdata.get("TestData"),testdata.get("Iteration"));
				System.out.println("Driver for test case LoginTest with Iteration: "+testdata.get("Iteration")+" is "+testdata.get("Browser"));
				driver.get(testdata.get("URL"));
				driver.manage().window().maximize();
				//driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")), TimeUnit.SECONDS);
				TestUtil.CaptureScreenshot();
			}else{
				System.out.println("no for AddCustomer Test case Iteration: "+testdata.get("Iteration"));
				driver.get(testdata.get("URL"));
				//driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")), TimeUnit.SECONDS);
				TestUtil.CaptureScreenshot();
			}
		}else{
			throw new SkipException("Skipping Test case Iteration as runmode is set to No");
		}
		
		app_logs.debug("Execution started for Adding New Customer Test Case");
		app_logs.debug("Step1 : click Manager Button");
		Thread.sleep(3000);
		//click("BMLBtn_CSS");
		hp.clickManagerLink();
		//driver.findElement(By.cssSelector(OR.getProperty("BMLBtn_CSS"))).click();
		Thread.sleep(3000);
		//Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("BtnaddCustomer"))),"Login is not successful");
		/*driver.findElement(By.cssSelector(OR.getProperty("BtnaddCustomer"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("fname"))).sendKeys(testdata.get("fName"));
		driver.findElement(By.cssSelector(OR.getProperty("lname"))).sendKeys(testdata.get("lName"));
		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(testdata.get("postcode"));
		driver.findElement(By.cssSelector(OR.getProperty("btnaddcust"))).click();*/
		//MP.clickAddCustomerLink();
		driver.findElement(By.xpath("//button[@ng-click='addCust()']")).click();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(3000);
		System.out.println("data"+" "+testdata.get("fName"));
		driver.findElement(By.xpath("//input[@ng-model='fName']")).click();
		driver.findElement(By.xpath("//input[@ng-model='fName']")).clear();
		//driver.findElement(By.xpath("//input[@ng-model='fName']")).sendKeys("Kanwar");
		driver.findElement(By.xpath("//input[@ng-model='fName']")).sendKeys(testdata.get("fName")+TestUtil.generateTimeStamp());
		driver.findElement(By.xpath("//input[@ng-model='lName']")).sendKeys(testdata.get("lName"));
		driver.findElement(By.xpath("//input[@ng-model='postCd']")).sendKeys(testdata.get("postcode"));
		TestUtil.CaptureScreenshot();
		driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains("Customer added successfully with customer id :"));
		alert.accept();
		Thread.sleep(3000);
		app_logs.debug("Execution finished for Add customer Test Case");
		TestUtil.CaptureScreenshot();
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		test.log(LogStatus.PASS, "Add Customer Registertaion Page is displayed successfuly");
		Reporter.log("Execution finished for Add Customer Test Case");
		tearDown();
		//Assert.fail("Add Customer is not successful");
	}
	
	@DataProvider
	public static Object[][] getData() {
		System.out.println("Name is data source is " + CurrentTestName);
		return TestUtil.getTestData(CurrentTestName);
	}
	
	public String getTestCaseName(){
		String TestName = this.getClass().getName();
		TestName = TestName.substring(TestName.lastIndexOf('.') + 1);
		return TestName;
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
	public void closebrowser(){
		System.out.println("I m in After Test");
		
	}
	
	
	
}
