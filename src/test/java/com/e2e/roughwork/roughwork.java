package com.e2e.roughwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.e2e.testUtils.TestConfig;
import com.e2e.testUtils.Xls_Reader;
import com.e2e.testUtils.monitoringMail;

public class roughwork {
		
	public static Xls_Reader excel = new Xls_Reader("D:\\Selenium Practise\\testdata.xlsx");	
	static String path = "D:\\Selenium Practise\\testdata.xlsx";
	

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		monitoringMail mail = new monitoringMail();
		//String Tcase = "Login Test";
		//String Hyperlink = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/job/E2E_GitHub_Integration/HTML_Report/"+Tcase +"Test.html";
		//excel.addHyperLink("test_suite", "Result", "LoginTest", 1, Hyperlink, "click");
		/*String Tcase = "Login Test";
		String Hyperlink = System.getProperty("user.dir")+"\\target\\surefire-reports\\E2E POM Suite\\"+Tcase +" Test.html";
		String A ="https://stackoverflow.com/questions/39303592/how-to-add-space-address-of-hyperlink-in-java-apche";
		excel.addHyperLink("test_suite", "ReportLink", "LoginTest", 1, A, "Hello");
		excel.setCellData("test_suite", "ReportLink", 4, "Hello", Hyperlink);*/
		//String Tcase = "Login Test";
		String Hyperlink = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/job/E2E_POM_Jenkin_Execution/Extent_Report/";
		System.out.println(Hyperlink);	
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		String messgaeBody = Hyperlink;
		//excel.addHyperLink("test_suite", "Result", "LoginTest", 1, Hyperlink, "Hello");
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messgaeBody);
	}
}
