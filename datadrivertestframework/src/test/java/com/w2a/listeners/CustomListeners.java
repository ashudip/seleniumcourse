package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.BaseClass;
import com.w2a.utilities.TestUtil;


public class CustomListeners extends BaseClass implements ITestListener {

	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = exprep.startTest(result.getName());
		
		if(!TestUtil.runmode(result.getName(), excel,excelpath))
		{
			throw new SkipException("Skipping the test "+result.getName()+"As the RUN MODE = NO");
		}	
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.PASS,result.getName()+" PASS");
		exprep.endTest(test);
		exprep.flush();
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		try {
			TestUtil.CaptureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL,result.getTestName()+" Fail with exception "+result.getThrowable());
		test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenshot));
		exprep.endTest(test);
		exprep.flush();
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.SKIP,result.getName()+"skipped the test as runmode is NO");
		exprep.endTest(test);
		exprep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
