package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class BaseClass {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	FileInputStream fis;
	protected static String path = System.getProperty("user.dir");
	public static Logger logs = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public  ExtentReports exprep = ExtentManager.getinstance();
	protected ExcelReader excel;
	protected SoftAssert sfassert = new SoftAssert();
	
	protected int rows;
	protected int col;
	protected String excelpath = "/src/test/resources/excel/Testdata.xlsx";
	public static ExtentTest test;
	
	public static String browser;
	
	@BeforeSuite
	public void setup() {
		if (driver == null) {
			try {
				fis = new FileInputStream(path + "/src/test/resources/properties/config.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				config.load(fis);
				logs.debug("config file loaded !!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(path + "/src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				logs.debug("OR file Loaded !!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty())
			{
				browser = System.getenv("browser");
				System.out.println("browser value = "+browser);
			}
			else {
				browser = config.getProperty("browser");
			}
			
			config.setProperty("browser", browser);
			System.out.println("browser config value = "+config.getProperty("browser"));
			
			if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", path + "/src/test/resources/Executables/chromedriver");
				driver = new ChromeDriver();
				logs.debug("chrome browser launched");
			} else if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", path + "/src/test/resources/Executables/geckodriver");
				driver = new FirefoxDriver();
				logs.debug("Firefox browser launched");
			}
			driver.navigate().to(config.getProperty("testurl"));
			logs.debug("Navigated to" + config.getProperty("testurl"));
			driver.manage().window().maximize();
			logs.debug("window maximized");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,10);
			Reporter.log("logged in");
		}
	}

	public void click(String locator) {
		
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		test.log(LogStatus.INFO," clicking on :"+locator);
	}
	
	public void Type(String locator,String value) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO," Typing in "+locator+" value : "+value);
	}
		
	public void selectdropdown(String locator , String value) {
		WebElement dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		Select sel = new Select(dropdown);
		sel.selectByVisibleText(value);
		test.log(LogStatus.INFO,"clicked on dropdown "+locator+" and selected value "+value);
	}
	public Boolean IsElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			
			return false;
			
		}
	}

	public static void verifyequals(String actual,String expected) throws IOException {
		try {
			Assert.assertEquals(actual,expected);
		} catch (Throwable e) {
			TestUtil.CaptureScreenShot();
			test.log(LogStatus.FAIL,"Failed :-"+e.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenshot));
			
		}
	}
	
	
	
	@AfterSuite
	public void Teardown() {
		if (driver != null) {
			driver.close();
		}

	}
	
	
	
}
