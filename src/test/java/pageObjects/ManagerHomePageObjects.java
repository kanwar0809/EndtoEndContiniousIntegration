package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManagerHomePageObjects {
	
	private static WebElement element = null;
	 
	 public static WebElement BtnAddCustomer(WebDriver driver){
	        element = driver.findElement(By.xpath("button[ng-click='addCust()']"));
	         return element;
	 }
	 
	 public static WebElement txtbox_fname(WebDriver driver){
	        element = driver.findElement(By.xpath("input[ng-model='fName']"));
	         return element;
	 }
	 
	 public static WebElement txtbox_lname(WebDriver driver){
	        element = driver.findElement(By.xpath("input[ng-model='lName']"));
	         return element;
	 }
	 
	 public static WebElement txtbox_postcode(WebDriver driver){
	        element = driver.findElement(By.xpath("input[ng-model='postCd']"));
	         return element;
	 }
	 
	 public static WebElement BtnSubmit(WebDriver driver){
	        element = driver.findElement(By.xpath("button[type='submit']"));
	         return element;
	 }
}
