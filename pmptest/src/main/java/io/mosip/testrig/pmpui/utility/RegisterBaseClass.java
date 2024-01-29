package io.mosip.testrig.pmpui.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpui.kernel.util.S3Adapter;

public class RegisterBaseClass {
	protected WebDriver driver;
	protected Map<String, Object> vars;
	protected JavascriptExecutor js;
	protected String langcode;
	protected String envPath = ConfigManager.getiam_adminportal_path();
	protected String env=ConfigManager.getiam_apienvuser();
	protected String userid = KeycloakUserManager.moduleSpecificUser;
	protected String[] allpassword = ConfigManager.getIAMUsersPassword().split(",");
	protected String password = allpassword[0];
	protected String data = Commons.appendDate.substring(0, Commons.getSplitdigit());
	public static ExtentSparkReporter html;
    public static    ExtentReports extent;
    public static    ExtentTest test;
    private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(Commons.class);
	
	
@BeforeMethod
	
    public void set() {
        extent=ExtentReportManager.getReports();

}

	@BeforeMethod
	public void setUp() throws Exception {
		Reporter.log("BaseClass",true);
		   test=extent.createTest(env,getCommitId());
		 
		   logger.info("Start set up");
			if(System.getProperty("os.name").equalsIgnoreCase("Linux") && ConfigManager.getdocker().equals("yes") ) {


				logger.info("Docker start");
				String configFilePath ="/usr/bin/chromedriver";
				System.setProperty("webdriver.chrome.driver", configFilePath);

			}else {
				WebDriverManager.chromedriver().setup();
				logger.info("window chrome driver start");
			}
			ChromeOptions options = new ChromeOptions();
			String headless=ConfigManager.getheadless();
			if(headless.equalsIgnoreCase("yes")) {
				logger.info("Running is headless mode");
				options.addArguments("--headless", "--disable-gpu","--no-sandbox", "--window-size=1920x1080","--disable-dev-shm-usage");


			}
			driver=new ChromeDriver(options);
			js = (JavascriptExecutor) driver;
			vars = new HashMap<String, Object>();
			driver.get(envPath);
			driver.manage().window().maximize();
			Thread.sleep(500);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		

	}

	@AfterMethod
	public void tearDown() throws InterruptedException {

		driver.quit();
		extent.flush();
	}

	@AfterSuite
	public void pushFileToS3() {
		getCommitId();
		if (ConfigManager.getPushReportsToS3().equalsIgnoreCase("yes")) {
			// EXTENT REPORT
			
			File repotFile = new File(ExtentReportManager.Filepath);
			System.out.println("reportFile is::" + repotFile);
			 String reportname = repotFile.getName();
			
			
			S3Adapter s3Adapter = new S3Adapter();
			boolean isStoreSuccess = false;
			try {
				isStoreSuccess = s3Adapter.putObject(ConfigManager.getS3Account(), BaseTestCaseFunc.testLevel, null,
						"PmpUi",env+BaseTestCaseFunc.currentModule+data+".html", repotFile);
				
				System.out.println("isStoreSuccess:: " + isStoreSuccess);
			} catch (Exception e) {
				System.out.println("error occured while pushing the object" + e.getLocalizedMessage());
				e.printStackTrace();
			}
			if (isStoreSuccess) {
				System.out.println("Pushed file to S3");
			} else {
				System.out.println("Failed while pushing file to S3");
			}
		}
		
		}
//	@DataProvider(name = "data-provider-partner")
//	public Object[] dpMethod() {
//		String listFilename[] = readFolderJsonList();
//		
//		return listFilename;
//	}
	
	@DataProvider(name = "data-provider-FTM")
	public Object[] ftmDataProvider() {
		String listFilename[] = readFolderJsonList("//ftm_cert//");

		return listFilename;
	}
	
	@DataProvider(name = "data-provider-DEVICE-SBI")
	public Object[] deviceSbiDataProvider() {
		String listFilename[] = readFolderJsonList("//device_sbi_cert//");

		return listFilename;
	}
	
	
	@DataProvider(name = "data-provider-AUTH")
	public Object[] authDataProvider() {
		String listFilename[] = readFolderJsonList("//auth_cert//");

		return listFilename;
	}

	
	public static String[] readFolderJsonList(String str) {
		String contents[] = null;
		try {
			File directoryPath = new File(TestRunner.getResourcePath() + "/"+ str);
logger.info("readFolderJsonList"+directoryPath);
			if (directoryPath.exists()) {

				contents = directoryPath.list();
				System.out.println("List of files and directories in the specified directory:");
				for (int i = 0; i < contents.length; i++) {
					System.out.println(contents[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
	
	 private String getCommitId(){
		 
	    	Properties properties = new Properties();
			try (InputStream is = ExtentReportManager.class.getClassLoader().getResourceAsStream("git.properties")) {
				properties.load(is);
				
				return "Commit Id is: " + properties.getProperty("git.commit.id.abbrev") + " & Branch Name is:" + properties.getProperty("git.branch");

			} catch (IOException e) {
//				logger.error(e.getStackTrace());
				return "";
			}
			
	    }
	

//	public static String[] readFolderJsonList() {
//		String contents[] = null;
//		try {
//				
//			File directoryPath = new File(System.getProperty("user.dir") + "\\partner_cert\\");
//
//			if (directoryPath.exists()) {
//
//				contents = directoryPath.list();
//				System.out.println("List of files and directories in the specified directory:");
//				for (int i = 0; i < contents.length; i++) {
//					System.out.println(contents[i]);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return contents;
//	}
}
