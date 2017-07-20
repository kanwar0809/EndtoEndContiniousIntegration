package com.e2e.pageoperations;

import org.testng.Reporter;

import com.e2e.core.TestBase;
import com.e2e.pageObjects.HomepageObjects;
import com.e2e.pageObjects.ManagerHomePageObjects;

public class ManageHomePage extends TestBase {
	
	ManagerHomePageObjects mp = new ManagerHomePageObjects();
	
	public void clickAddCustomerLink(){
		mp.BtnAddCustomer(driver).click();
		
	}
}
