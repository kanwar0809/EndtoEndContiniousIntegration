package com.e2e.pageoperations;

import org.testng.Reporter;

import com.e2e.core.TestBase;

import com.e2e.pageObjects.HomepageObjects;

public class HomePage extends TestBase{
	
	HomepageObjects hp = new HomepageObjects();
	
	public void clickManagerLink(){
		hp.btn_BML(driver).click();
		
	}
}
