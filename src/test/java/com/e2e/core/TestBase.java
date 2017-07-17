package com.e2e.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.e2e.testUtils.ExtentManager;
import com.e2e.testUtils.TestUtil;
import com.e2e.testUtils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;

public class TestBase {
	public static WebDriver driver;
	public static RemoteWebDriver Androiddriver;
	//public static RemoteWebDriver Androidwebdriver;
	public static Properties Config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static String ProjectPath = System.getProperty("user.dir");
	public static Xls_Reader excel = new Xls_Reader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	//public static Xls_Reader UpdateExcel;
	public static String ScrXlsPath =  ProjectPath +"\\src\\test\\resources\\excel\\testdata.xlsx";
	public static String DescXlsPath =  ProjectPath +"\\src\\test\\resources\\excel\\Output\\";
	public static Logger app_logs = Logger.getLogger("devpinoyLogger");
	public static String driverPath = "D:\\Set ups\\Selenium\\Drivers\\";
	//public static String driverPath = Config.getProperty("DriverPath");
	public static WebDriverWait wait;
	public static String TestDataSheetName = "TestData";
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String Browser;
	public static String TestResult=null;
	public static String CurrentTestName;
	public static String ExecutionStartTime;
	public static String ExecutionEndTime;
	public static String Iteration;
	public static int CurrentIteration;
	
	@BeforeSuite
	public void setUp() throws MalformedURLException{
		TestUtil testutil = new TestUtil();
		//testutil.prepareResultSheet();
		//if(driver==null){
			try {
				fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Config.load(fis);
				//app_logs.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				//app_logs.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		//}
		
		if(!Config.getProperty("browser").trim().equals("")){
			/*if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				Browser = System.getenv("browser");
				System.out.println("Browser was selected from Env");
			}else{
				Browser = Config.getProperty("browser");
				System.out.println("Browser was selected from Config");
			}
			
			Config.setProperty("browser", Browser);*/
			
			System.out.println("browser in conf is"+ Config.getProperty("browser"));
			if (Config.getProperty("browser").equalsIgnoreCase("ie")) {

				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}else if(Config.getProperty("browser").equalsIgnoreCase("chrome")) {
				System.out.println("initialize Chrome driver1");
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver_2.29.exe");
				System.out.println("initialize Chrome driver");
				driver = new ChromeDriver();
				System.out.println("initialize Chrome driver1");
			}else if(Config.getProperty("browser").equalsIgnoreCase("Android")){
				  System.out.println("Yes");
				  DesiredCapabilities capabilities = new DesiredCapabilities();
				  capabilities.setCapability("BROWSER_NAME", Config.getProperty("AndroidBROWSERNAME"));
				  System.out.println(Config.getProperty("AndroidBROWSERNAME"));
				  capabilities.setCapability("VERSION", Config.getProperty("Android_Version")); 
				  System.out.println(Config.getProperty("Android_Version"));
				  capabilities.setCapability("deviceName",Config.getProperty("DeviceName"));
				  System.out.println(Config.getProperty("DeviceName"));
				  capabilities.setCapability("platformName",Config.getProperty("PlatformName"));
				  System.out.println(Config.getProperty("PlatformName"));
				  capabilities.setCapability("appPackage", Config.getProperty("AppPackage"));
				  System.out.println(Config.getProperty("AppPackage"));
				  capabilities.setCapability("appActivity",Config.getProperty("AppActivity")); 
				  System.out.println(Config.getProperty("AppActivity"));
				  System.out.println("Yes1");
				  Androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				  Androiddriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
				 
			  }
			
			//driver.get(Config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,5);
		}else{
			System.out.println("No Browser initialized in config file");
			
		}
	}
	
	public void click(String locator) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		/*if (locator.endsWith("_CSS")) {
			System.out.println(locator);
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}*/
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}
	
	static WebElement dropdown;

	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}

	
	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	
	@AfterSuite
	public void QuitDriver() throws AddressException, MessagingException{
		
		if(!(driver==null)){
			driver.quit();
			System.out.println("Closing Browser from Suite");
		}else if(!(Androiddriver==null)){
			Androiddriver.quit();
			System.out.println("Closing Android Driver from Suite");
		}
	}
}
