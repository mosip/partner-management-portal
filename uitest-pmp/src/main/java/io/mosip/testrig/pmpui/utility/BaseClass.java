package io.mosip.testrig.pmpui.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.kernel.util.KeycloakUserManager;

public class BaseClass {
	protected WebDriver driver;
	protected Map<String, Object> vars;
	protected static JavascriptExecutor js;
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
	private static final Logger logger = Logger.getLogger(BaseClass.class);
	

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
		logger.info("launch url --"+envPath);
		driver.manage().window().maximize();
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//driver.findElement(By.linkText("Admin")).click();
		String language1 = null;
		try {

			language1 = ConfigManager.getloginlang();
			String loginlang = null;
			System.out.println(language1);
			if(!language1.equals("sin")) {
				loginlang = JsonUtil.JsonObjArrayListParsing2(ConfigManager.getlangcode());
				Commons.click(test,driver, By.xpath("//*[@id='kc-locale-dropdown']"));
				String var = "//li/a[contains(text(),'" + loginlang + "')]";
				Commons.click(test,driver, By.xpath(var));
			}

		} catch (Exception e) {
			e.getMessage();
		}

		driver.findElement(By.id("username")).sendKeys(userid);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name=\'login\']")).click();

	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		//Once we will get the logout id we are going to use[TODO]
		//		Commons.click(test,driver, By.id("menuButton"));
		//		Commons.click(test,driver, By.id("Logout"));
		driver.quit();
		extent.flush();
	}

	@DataProvider(name = "data-provider-ca")
	public Object[] caDataProvider() {
		String listFilename[] = readFolderJsonList("//ca_cert//");
		logger.info("listFilename ca cert="+listFilename);
		return listFilename;
	}
	//	@DataProvider(name = "data-provider-partner")
	//	public Object[] partnerDataProvider() {
	//		String listFilename[] = readFolderJsonList("\\partner_cert\\");
	//
	//		return listFilename;
	//	}

	@DataProvider(name = "data-provider-FTM")
	public Object[] ftmDataProvider() {
		String listFilename[] = readFolderJsonList("//ftm_cert//");
		logger.info("listFilename ftm cert="+listFilename);
		return listFilename;
	}

	@DataProvider(name = "data-provider-DEVICE-SBI")
	public Object[] deviceSbiDataProvider() {
		String listFilename[] = readFolderJsonList("//device_sbi_cert");
		logger.info("listFilename device sbi cert="+listFilename);
		return listFilename;
	}


	@DataProvider(name = "data-provider-AUTH")
	public Object[] authDataProvider() {
		String listFilename[] = readFolderJsonList("//auth_cert//");
		logger.info("listFilename auth cert="+listFilename);
		return listFilename;
	}

	public static String[] readFolderJsonList(String str) {
		String contents[] = null;
		File directoryPath=null;
		try {

			if (TestRunner.checkRunType().equalsIgnoreCase("JAR")) {
				directoryPath = new File(TestRunner.getResourcePath() + "/"  + str);
			} else if (TestRunner.checkRunType().equalsIgnoreCase("IDE")) {
				directoryPath= new File(System.getProperty("user.dir") + System.getProperty("path.config")+"/"+str);


			}




			logger.info("file directory for "+directoryPath);
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

}
