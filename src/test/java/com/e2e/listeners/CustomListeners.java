package com.e2e.listeners;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.e2e.core.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import com.e2e.testUtils.*;
import com.mysql.cj.api.jdbc.result.*;
//import com.mysql.cj.jdbc.UpdatableResultSet;


public class CustomListeners extends TestBase implements ITestListener,ISuiteListener {

	public 	String messageBody;
	TestUtil testutil = new TestUtil();
	
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			TestUtil.CaptureScreenshot();
			//System.out.println("test is failed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, CurrentTestName.toUpperCase()+" Failed with exception : "+arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		rep.endTest(test);
		rep.flush();
		try {
			testutil.UpdateExecutionResult(CurrentTestName,"Failed");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult arg0) {
		System.setProperty("org.uncommons.reportng.escape-output","false");
		//test.log(LogStatus.SKIP, "Skipped");
		System.out.println("I am in Skiped Test");
		try {
			//System.out.println("Current Test Case Name in try is "+CurrentTestName);
			testutil.UpdateExecutionResult(CurrentTestName,"Skipped");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("I am in Skiped Test2");
		System.out.println("Current Test Case Name in Skiped is "+CurrentTestName);
		test.log(LogStatus.SKIP, CurrentTestName.toUpperCase()+" Skipped the test as the Run mode is NO");
		System.out.println("I am in Skiped Test3");
		rep.endTest(test);
		rep.flush();
		
	}


	public void onTestStart(ITestResult arg0) {
		//int iteration = DefaultIteration + CurrentIteration;
		//System.out.println();
		//System.out.println(iteration);
		test = rep.startTest(CurrentTestName.toUpperCase(),TestDescription);
		//System.out.println("I m in Test Start");
	}

	public void onTestSuccess(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output","false");
		test.log(LogStatus.PASS, CurrentTestName.toUpperCase()+" PASS");
		rep.endTest(test);
		rep.flush();
		System.out.println("I am in success Test");
		try {
			testutil.UpdateExecutionResult(CurrentTestName,"Passed");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onFinish(ISuite arg0) {
		
		/*monitoringMail mail = new monitoringMail();
		 
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenLiveProject/Extent_Reports/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		//test = rep.startTest(arg0.getName().toUpperCase());
		//System.out.println("I m in Test Start");
	}
}
