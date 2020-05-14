package com.w2a.testcases;


import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.BaseClass;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.TestUtil;


public class AddCustomerTest extends BaseClass {

	
	@Test(dataProvider = "getdata")
	public void addcustomertest(Hashtable<String,String> data) throws IOException {
		
		String title = driver.getTitle();
		
		//verifyequals(title,"test ttile");
		if(!data.get("RunMode").equalsIgnoreCase("Y"))
		{
			throw new SkipException("Skipping the test data as RUNMODE = NO");
		}
		click("addcustbtn");
		Type("custfirstname", data.get("FirstName"));
		Type("custLastName", data.get("LastName"));
		Type("postcode", data.get("Postcode"));
		click("addactualcustomer");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		test.log(LogStatus.INFO, "Expected Result :" + data.get("AlertText"));
	
		sfassert.assertTrue(alert.getText().contains(data.get("AlertText")), "Customer added successfully with customer id");
		test.log(LogStatus.INFO, "Actual Alert text : " + alert.getText());
		alert.accept();
		sfassert.assertAll();

	}

	@DataProvider(name = "getdata")
	public Object[][] getdata() {
		if (excel == null) {
			excel = new ExcelReader(path + excelpath);
		}
		String sheetname = "AddcustomerTest";
		System.out.println("sheetname = " + sheetname);
		rows = excel.getRowCount(sheetname);
		System.out.println("rows=" + rows);
		
		col = excel.getColumnCount(sheetname);

		Object data[][] = new Object[rows - 1][1];
		
		Hashtable<String,String>table = null;
		
		for (int rowsnum = 2; rowsnum <= rows; rowsnum++) {
			
			table = new Hashtable<String, String>();
			
			for (int colnum = 0; colnum < col; colnum++) {
			
				table.put(excel.getCellData(sheetname, colnum, 1),excel.getCellData(sheetname, colnum, rowsnum));
				
				data[rowsnum - 2][0] = table;
			}
		}
		return data;

	}

}
