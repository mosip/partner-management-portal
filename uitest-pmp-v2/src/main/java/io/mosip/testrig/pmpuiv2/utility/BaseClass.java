package io.mosip.testrig.pmpuiv2.utility;

import java.io.File;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.mosip.testrig.pmpuiv2.driver.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;

public class BaseClass {
	protected WebDriver driver;
	protected Map<String, Object> vars;
	protected JavascriptExecutor js;
	protected String langcode;
	protected String envPath = ConfigManager.getiam_adminportal_path();
	protected String envPathPmpUiv2 = ConfigManager.getiam_pmpuiv2_path();
	protected String Pmpuiv2 = ConfigManager.getiam_pmpuiv2();
	protected String env = ConfigManager.getiam_apienvuser();
	protected String[] Alluserid = ConfigManager.getIAMUsersToCreate().split(",");
	protected String userid = BaseTestCaseFunc.currentModule + "-" + Alluserid[1];
	protected String[] allpassword = ConfigManager.getIAMUsersPassword().split(",");
	protected String password = allpassword[0];
	public static final Logger logger = Logger.getLogger(BaseClass.class);
	public static String data;
	private static boolean chromeDriverReady;

	@BeforeMethod
	public void setUp(Method method) throws Exception {
		logger.info("Start set up");
		if (System.getProperty("os.name").equalsIgnoreCase("Linux") && ConfigManager.getdocker().equals("yes")) {
			logger.info("Docker start");
			String configFilePath = "/usr/bin/chromedriver";
			System.setProperty("webdriver.chrome.driver", configFilePath);
		} else if (!chromeDriverReady) {
			WebDriverManager.chromedriver().setup();
			chromeDriverReady = true;
			logger.info("window chrome driver start");
		}

		ChromeOptions options = new ChromeOptions();
	    boolean isHeadless = ConfigManager.getheadless().equalsIgnoreCase("yes");

	    options.addArguments("--disable-extensions", "--disable-notifications", "--disable-popup-blocking");

	    if (isHeadless) {
	        logger.info("Running in HEADLESS mode");

	        options.addArguments(
	            "--headless=new",
	            "--disable-gpu",
	            "--no-sandbox",
	            "--disable-dev-shm-usage",
	            "--window-size=1920,1080",
	            "--force-device-scale-factor=1",
	            "--high-dpi-support=1",
	            "--blink-settings=imagesEnabled=false"
	        );
	    } else {
	        logger.info("Running in HEADED mode");
	    }

	    WebDriver driver = new ChromeDriver(options);
	    DriverManager.setDriver(driver);
	    this.driver = driver;

	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

	    if (!isHeadless) {
	        driver.manage().window().maximize();
	    } else {
	        driver.manage().window().setSize(new Dimension(1920, 1080));
	    }

	    driver.get(envPathPmpUiv2);
	    logger.info("Launched URL: {"+envPathPmpUiv2+"}");

	    if (isHeadless) {
	        ((JavascriptExecutor) driver).executeScript(
	            "window.resizeTo(1920,1080);"
	        );
	    }

		String testName = method.getName();
		String description = method.getAnnotation(Test.class) != null ? method.getAnnotation(Test.class).description()
				: "";
		LogUtil.step("--------------------------------------------------");
		LogUtil.step("Starting Test: " + testName);
		LogUtil.step("Description: " + description);
		LogUtil.step("--------------------------------------------------");

		String rawData = BasePage.getPreAppend() + BasePage.getDateTime();
		BaseClass.data = rawData.substring(0, BasePage.getSplitdigit());
		logger.info("Test data suffix generated: " + BaseClass.data);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userid, password);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		try {
			DriverManager.quitDriver();
		} catch (Exception ignored) {
		}
		this.driver = null;
	}

	@DataProvider(name = "data-provider-ca")
	public Object[] caDataProvider() {
		String listFilename[] = readFolderJsonList("//ca_cert//");
		logger.info("listFilename ca cert=" + listFilename);
		return listFilename;
	}

	@DataProvider(name = "data-provider-FTM")
	public Object[] ftmDataProvider() {
		String listFilename[] = readFolderJsonList("//ftm_cert//");
		logger.info("listFilename ftm cert=" + listFilename);
		return listFilename;
	}

	@DataProvider(name = "data-provider-DEVICE-SBI")
	public Object[] deviceSbiDataProvider() {
		String listFilename[] = readFolderJsonList("//device_sbi_cert");
		logger.info("listFilename device sbi cert=" + listFilename);
		return listFilename;
	}

	@DataProvider(name = "data-provider-AUTH")
	public Object[] authDataProvider() {
		String listFilename[] = readFolderJsonList("//auth_cert//");
		logger.info("listFilename auth cert=" + listFilename);
		return listFilename;
	}

	public static String[] readFolderJsonList(String str) {
		String contents[] = null;
		File directoryPath = null;
		try {
			if (TestRunner.checkRunType().equalsIgnoreCase("JAR")) {
				directoryPath = new File(TestRunner.getResourcePath() + "/" + str);
			} else if (TestRunner.checkRunType().equalsIgnoreCase("IDE")) {
				directoryPath = new File(
						System.getProperty("user.dir") + System.getProperty("path.config") + "/" + str);
			}

			logger.info("file directory for " + directoryPath);
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

	private String getCommitId() {
		Properties properties = new Properties();
		return "Commit Id is: " + properties.getProperty("git.commit.id.abbrev") + " & Branch Name is:"
				+ properties.getProperty("git.branch");
	}

	public static String Date() {
		NumberFormat integerFormat = NumberFormat.getIntegerInstance();
		LocalDate currentDate = LocalDate.now();
		String formattedDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formattedDate = currentDate.format(formatter);

		System.out.println(formattedDate);
		return formattedDate;
	}
}
