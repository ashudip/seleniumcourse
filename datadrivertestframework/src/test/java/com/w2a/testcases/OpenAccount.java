package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.BaseClass;
import com.w2a.utilities.ExcelReader;

public class OpenAccount extends BaseClass {

	@Test(dataProvider = "getdata")
	public void openaccounttest(String name, String currency, String alerttext,String runmode) throws IOException {
		
		if (!runmode.equalsIgnoreCase("Y")) {
			
			throw new SkipException("Skipping the test data as RUNMODE = NO");
		}
		
		click("openaccountbtn");
		selectdropdown("selectcust", name);
		selectdropdown("selectcurrency",currency);
		click("processbtn");		
		
		 wait.until(ExpectedConditions.alertIsPresent());
		 Alert alert= driver.switchTo().alert();
		test.log(LogStatus.INFO,"Expected Result = "+alerttext);
		String actuaString = alert.getText();
		System.out.println("alert text="+actuaString);
		alert.accept();
		verifyequals(actuaString, alerttext);
	}
	
	@DataProvider(name = "getdata")
	public Object[][] getdata() {
		if (excel == null) {
			excel = new ExcelReader(path + excelpath);
		}
		String sheetname = "OpenAccountTest";
		System.out.println("sheetname = " + sheetname);
		rows = excel.getRowCount(sheetname);
		System.out.println("rows=" + rows);
		col = excel.getColumnCount(sheetname);

		Object data[][] = new Object[rows - 1][col];
		for (int rowsnum = 2; rowsnum <= rows; rowsnum++) {
			for (int colnum = 0; colnum < col; colnum++) {
				data[rowsnum - 2][colnum] = excel.getCellData(sheetname, colnum, rowsnum);
			}
		}
		return data;

	}
	
}


