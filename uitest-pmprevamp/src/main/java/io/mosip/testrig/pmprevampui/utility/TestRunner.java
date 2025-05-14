package io.mosip.testrig.pmprevampui.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import io.mosip.testrig.pmprevampui.dbaccess.DBManager;
import io.mosip.testrig.pmprevampui.fw.util.AdminTestUtil;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;

public class TestRunner {
	static TestListenerAdapter tla = new TestListenerAdapter();
	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static String uin = "";
	public static String perpetualVid = "";
	public static String onetimeuseVid = "";
	public static String temporaryVid = "";

	static TestNG testNg;

	public static void main(String[] args) throws Exception {
		AdminTestUtil.initialize();
		startTestRunner();

	}

	public static void startTestRunner() throws Exception {
		File homeDir = null;
		TestNG runner = new TestNG();
		if (!ConfigManager.gettestcases().equals("")) {

			XmlSuite suite = new XmlSuite();
			suite.setName("MySuite");
			suite.addListener("io.mosip.testrig.pmprevampui.utility.EmailableReport");
			XmlClass AuthPartnerTest = new XmlClass("io.mosip.testrig.pmprevampui.testcase.AuthPartnerTest");
			XmlClass devicePartnerTest = new XmlClass("io.mosip.testrig.pmprevampui.testcase.DevicePartnerTest");
			XmlClass FTMDeviceTest = new XmlClass("io.mosip.testrig.pmprevampui.testcase.FTMDeviceTest");

			List<XmlClass> classes = new ArrayList<>();
			String[] Scenarioname = ConfigManager.gettestcases().split(",");
			for (String test : Scenarioname) {

				if (test.equals("AuthPartnerTest")) {
					classes.add(AuthPartnerTest);
				}else if (test.equals("devicePartnerTest")) {
					classes.add(devicePartnerTest);
				}
				if (test.equals("FTMDeviceTest")) {
					classes.add(FTMDeviceTest);
				}

			}

			XmlTest test = new XmlTest(suite);
			test.setName("MyTest");
			test.setXmlClasses(classes);

			List<XmlSuite> suites = new ArrayList<>();
			suites.add(suite);

			runner.setXmlSuites(suites);

		} else {
			List<String> suitefiles = new ArrayList<String>();
			String os = System.getProperty("os.name");
			if (checkRunType().contains("IDE") || os.toLowerCase().contains("windows") == true) {
				homeDir = new File(getResourcePath() + "/testngFile");

			} else {
				homeDir = new File(getResourcePath() + "/testngFile");

			}

			for (File file : homeDir.listFiles()) {
				if (file.getName().toLowerCase() != null) {
					suitefiles.add(file.getAbsolutePath());
				}
			}

			runner.setTestSuites(suitefiles);

		}

		System.getProperties().setProperty("testng.outpur.dir", "testng-report");
		runner.setOutputDirectory("testng-report");
		System.getProperties().setProperty("emailable.report2.name",
				"PMPUI-" + BaseTestCaseFunc.environment + "-run-" + BaseClass.Date() + "-report.html");

		runner.run();
		DBManager.executeDBQueries(ConfigManager.getPMSDbUrl(), ConfigManager.getPMSDbUser(),
				ConfigManager.getPMSDbPass(), ConfigManager.getPMSDbSchema(),
				TestRunner.getResourcePath() + "\\" + "config\\partnerRevampDataDeleteQueries.txt");

		DBManager.executeDBQueries(ConfigManager.getKMDbUrl(), ConfigManager.getMasterDbUser(),
				ConfigManager.getMasterDbPass(), ConfigManager.getMasterDbSchema(),
				TestRunner.getResourcePath() + "/" + "config/partnerRevampDataDeleteQueriesForKeyMgr.txt");

		DBManager.executeDBQueries(ConfigManager.getIdaDbUrl(), ConfigManager.getMasterDbUser(),
				ConfigManager.getPMSDbPass(), ConfigManager.getIDADBSchema(),
				TestRunner.getResourcePath() + "/" + "config/partnerRevampDataDeleteQueriesForIDA.txt");
		System.exit(0);

	}

	public static String getGlobalResourcePath() {
		if (checkRunType().equalsIgnoreCase("JAR")) {
			return new File(jarUrl).getParentFile().getAbsolutePath().toString();
		} else if (checkRunType().equalsIgnoreCase("IDE")) {
			String path = new File(TestRunner.class.getClassLoader().getResource("").getPath()).getAbsolutePath()
					.toString();
			if (path.contains("test-classes"))
				path = path.replace("test-classes", "classes");
			return path;
		}
		return "Global Resource File Path Not Found";
	}

	public static String getResourcePath() {
		if (checkRunType().equalsIgnoreCase("JAR")) {
			return new File(jarUrl).getParentFile().getAbsolutePath().toString() + "/resources/";
		} else if (checkRunType().equalsIgnoreCase("IDE")) {
			String path = System.getProperty("user.dir") + System.getProperty("path.config");

			if (path.contains("test-classes"))
				path = path.replace("test-classes", "classes");
			return path;
		}
		return "Global Resource File Path Not Found";
	}

	public static String checkRunType() {
		if (TestRunner.class.getResource("TestRunner.class").getPath().toString().contains(".jar"))
			return "JAR";
		else
			return "IDE";
	}

	public static String GetKernalFilename() {
		String path = System.getProperty("env.user");
		String kernalpath = null;
		if (System.getProperty("env.user") == null || System.getProperty("env.user").equals("")) {
			kernalpath = "Kernel.properties";

		} else {
			kernalpath = "Kernel_" + path + ".properties";
		}
		return kernalpath;
	}

}
