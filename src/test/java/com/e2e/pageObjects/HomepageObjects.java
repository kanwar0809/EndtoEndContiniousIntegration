package com.e2e.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomepageObjects {
	 private static WebElement element = null;
	 
	 public static WebElement btn_BML(WebDriver driver){
	        //element = driver.findElement(By.cssSelector("div > div:nth-child(3) > .btn.btn-primary.btn-lg"));
	        element = driver.findElement(By.xpath("//button[@ng-click='manager()']"));
	         return element;
	 }
	 
	 
}
