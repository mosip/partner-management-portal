package io.mosip.testrig.pmpuiv2.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import io.mosip.testrig.pmpuiv2.dbaccess.DBManager;
import io.mosip.testrig.pmpuiv2.fw.util.AdminTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

public class TestRunner {
	static TestListenerAdapter tla = new TestListenerAdapter();
	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static String uin = "";
	public static String perpetualVid = "";
	public static String onetimeuseVid = "";
	public static String temporaryVid = "";

	static TestNG testNg;
	private static final Logger logger = Logger.getLogger(TestRunner.class);

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
			suite.addListener("io.mosip.testrig.pmpuiv2.utility.EmailableReport");

			// Define all available XmlClasses
			XmlClass partnerAdminCreation = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.PartnerAdminCreation");
			XmlClass authPartnerCreation = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.AuthPartnerCreation");
			XmlClass devicePartnerCreation = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.DevicePartnerCreation");
			XmlClass ftmPartnerCreation = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.FtmPartnerCreation");
			XmlClass policyAdminAndPartnerCreation = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PolicyAdminAndPartnerCreation");
			XmlClass deactivatePartnerCreation = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivatePartnerCreation");
			XmlClass ftmDeviceTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.FtmDeviceTest");
			XmlClass deviceProviderTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.DeviceProviderTest");
			XmlClass policyCreationForAuthPartner = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PolicyCreationForAuthPartner");
			XmlClass oidcClientAuthPartnerTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.OidcClientAuthPartnerTest");
			XmlClass deviceCreationTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.DeviceCreationTest");
			XmlClass policyGroupTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.PolicyGroupTest");
			XmlClass certificateTrustStoreTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.CertificateTrustStoreTest");
			XmlClass apiKeyAuthPartnerTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.ApiKeyAuthPartnerTest");
			XmlClass datasharePolicyTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.DatasharePolicyTest");
			XmlClass sbiDeviceProviderTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.SbiDeviceProviderTest");
			XmlClass authPartnerWithoutCertificateTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.AuthPartnerWithoutCertificateTest");
			XmlClass partnerDetailsTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.PartnerDetailsTest");
			XmlClass authPolicyTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.AuthPolicyTest");
			XmlClass partnerPolicyMappingTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PartnerPolicyMappingTest");

			List<XmlClass> classes = new ArrayList<>();
			String[] scenarioNames = ConfigManager.gettestcases().split(",");

			for (String test : scenarioNames) {
				switch (test.trim()) {

				// STEP 1
				case "PartnerAdminCreation":
					classes.add(partnerAdminCreation);
					break;
				case "AuthPartnerCreation":
					classes.add(authPartnerCreation);
					break;
				case "DevicePartnerCreation":
					classes.add(devicePartnerCreation);
					break;
				case "FtmPartnerCreation":
					classes.add(ftmPartnerCreation);
					break;
				case "PolicyAdminAndPartnerCreation":
					classes.add(policyAdminAndPartnerCreation);
					break;
				case "DeactivatePartnerCreation":
					classes.add(deactivatePartnerCreation);
					break;

				// STEP 2
				case "FTMDeviceTest":
					classes.add(ftmDeviceTest);
					break;
				case "DeviceProviderTest":
					classes.add(deviceProviderTest);
					break;
				case "PolicyCreationForAuthPartner":
					classes.add(policyCreationForAuthPartner);
					break;

				// STEP 3
				case "OidcClientAuthPartnerTest":
					classes.add(oidcClientAuthPartnerTest);
					break;
				case "DeviceCreationTest":
					classes.add(deviceCreationTest);
					break;
				case "PolicyGroupTest":
					classes.add(policyGroupTest);
					break;

				// STEP 4
				case "CertificateTrustStoreTest":
					classes.add(certificateTrustStoreTest);
					break;
				case "ApiKeyAuthPartnerTest":
					classes.add(apiKeyAuthPartnerTest);
					break;
				case "DatasharePolicyTest":
					classes.add(datasharePolicyTest);
					break;
				case "SbiDeviceProviderTest":
					classes.add(sbiDeviceProviderTest);
					break;

				// STEP 5
				case "AuthPartnerWithoutCertificateTest":
					classes.add(authPartnerWithoutCertificateTest);
					break;
				case "PartnerDetailsTest":
					classes.add(partnerDetailsTest);
					break;
				case "AuthPolicyTest":
					classes.add(authPolicyTest);
					break;
				case "PartnerPolicyMappingTest":
					classes.add(partnerPolicyMappingTest);
					break;

				// Unknown test name
				default:
					logger.warn("Unknown test name: " + test);
				}
			}

			XmlTest test = new XmlTest(suite);
			test.setName("MyTest");
			test.setXmlClasses(classes);

			List<XmlSuite> suites = new ArrayList<>();
			suites.add(suite);

			runner.setXmlSuites(suites);

		} else {
			List<String> suitefiles = new ArrayList<>();
			String os = System.getProperty("os.name");
			if (checkRunType().contains("IDE") || os.toLowerCase().contains("windows")) {
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

		// DB cleanup
		DBManager.executeDBQueries(ConfigManager.getPMSDbUrl(), ConfigManager.getPMSDbUser(),
				ConfigManager.getPMSDbPass(), ConfigManager.getPMSDbSchema(),
				TestRunner.getResourcePath() + "/" + "config/partnerUiv2DataDeleteQueries.txt");

		DBManager.executeDBQueries(ConfigManager.getKMDbUrl(), ConfigManager.getMasterDbUser(),
				ConfigManager.getMasterDbPass(), ConfigManager.getMasterDbSchema(),
				TestRunner.getResourcePath() + "/" + "config/partnerUiv2DataDeleteQueriesForKeyMgr.txt");

		DBManager.executeDBQueries(ConfigManager.getIdaDbUrl(), ConfigManager.getMasterDbUser(),
				ConfigManager.getPMSDbPass(), ConfigManager.getIDADBSchema(),
				TestRunner.getResourcePath() + "/" + "config/partnerUiv2DataDeleteQueriesForIDA.txt");

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
