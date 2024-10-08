package io.mosip.testrig.pmprevampui.utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmprevampui.pages.BasePage;

public class BaseClass {
	protected WebDriver driver;
	protected Map<String, Object> vars;
	protected static JavascriptExecutor js;
	protected String langcode;
	protected String envPath = ConfigManager.getiam_adminportal_path();
	protected String envPathPmpRevamp = ConfigManager.getiam_pmprevamp_path();
	protected String PmpRevamp = ConfigManager.getiam_pmprevamp();
	protected String env=ConfigManager.getiam_apienvuser();
	protected String userid = KeycloakUserManager.moduleSpecificUser;
	protected String[] allpassword = ConfigManager.getIAMUsersPassword().split(",");
	protected String password = allpassword[0];
	private static final Logger logger = Logger.getLogger(BaseClass.class);
	protected String data = BasePage.appendDate.substring(0, BasePage.getSplitdigit());


	@BeforeMethod
	public void setUp() throws Exception {
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
		driver.get(envPathPmpRevamp);
		logger.info("launch url --"+envPathPmpRevamp);
		driver.manage().window().maximize();
		Thread.sleep(500);	
		BasePage.enter(driver.findElement(By.id("username")), "automationuiiii");
		BasePage.enter(driver.findElement(By.id("password")), "mosip123");
		driver.findElement(By.xpath("//input[@name=\'login\']")).click();

	}

	@AfterMethod
    public void tearDown(ITestResult result) {
        driver.quit();
    }


	@DataProvider(name = "data-provider-ca")
	public Object[] caDataProvider() {
		String listFilename[] = readFolderJsonList("//ca_cert//");
		logger.info("listFilename ca cert="+listFilename);
		return listFilename;
	}

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
		return "Commit Id is: " + properties.getProperty("git.commit.id.abbrev") + " & Branch Name is:" + properties.getProperty("git.branch");
	}

	public static String generateRandomString(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}

		return sb.toString();
	}
}


