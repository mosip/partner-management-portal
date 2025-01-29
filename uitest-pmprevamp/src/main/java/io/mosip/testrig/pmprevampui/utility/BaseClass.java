package io.mosip.testrig.pmprevampui.utility;

import java.io.File;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.BasePage;

public class BaseClass {
	protected WebDriver driver;
	protected Map<String, Object> vars;
	protected static JavascriptExecutor js;
	protected String langcode;
	protected String envPath = ConfigManager.getiam_adminportal_path();
	protected String envPathPmpRevamp = ConfigManager.getiam_pmprevamp_path();
	protected String PmpRevamp = ConfigManager.getiam_pmprevamp();
	protected String env = ConfigManager.getiam_apienvuser();
	protected String[] Alluserid = ConfigManager.getIAMUsersToCreate().split(",");
	protected String userid = BaseTestCaseFunc.currentModule + "-" + Alluserid[1];
	protected String[] allpassword = ConfigManager.getIAMUsersPassword().split(",");
	protected String password = allpassword[0];
	public static final Logger logger = Logger.getLogger(BaseClass.class);
	public static String data = BasePage.appendDate.substring(0, BasePage.getSplitdigit());

	@BeforeMethod
	public void setUp() throws Exception {
		logger.info("Start set up");
		if (System.getProperty("os.name").equalsIgnoreCase("Linux") && ConfigManager.getdocker().equals("yes")) {

			logger.info("Docker start");
			String configFilePath = "/usr/bin/chromedriver";
			System.setProperty("webdriver.chrome.driver", configFilePath);

		} else {
			WebDriverManager.chromedriver().setup();
			logger.info("window chrome driver start");
		}
		ChromeOptions options = new ChromeOptions();
		String headless = ConfigManager.getheadless();
		if (headless.equalsIgnoreCase("yes")) {
			logger.info("Running is headless mode");
			options.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--window-size=1920x1080",
					"--disable-dev-shm-usage");

		}
		driver = new ChromeDriver(options);
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		driver.get(envPathPmpRevamp);
		logger.info("launch url --" + envPathPmpRevamp);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		String language1 = null;
//		try {
//
//			language1 = ConfigManager.getloginlang();
//			String loginlang = null;
//			System.out.println(language1);
//			if(!language1.equals("sin")) {
//				loginlang = JsonUtil.JsonObjArrayListParsing2(ConfigManager.getlangcode());
//				driver.findElement(By.xpath("//*[@id='kc-locale-dropdown']")).click();
//				String var = "//li/a[contains(text(),'" + loginlang + "')]";
//				driver.findElement(By.xpath(var)).click();
//			}
//		} catch (Exception e) {
//			e.getMessage();
//		}

		BasePage.enter(driver.findElement(By.id("username")), userid);
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
