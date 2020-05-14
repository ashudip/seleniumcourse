package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.BaseClass;

public class LoginClass extends BaseClass{
	@Test
	public void logintest()
	{
		click("bmlbtn");
		logs.debug("clicked on bank manager login button");
		System.out.println(driver.getTitle());
		Assert.assertTrue(IsElementPresent(By.cssSelector(OR.getProperty("addcustbtn"))),"No Such element present");
		
	}
}
