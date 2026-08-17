package io.mosip.testrig.pmpuiv2.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	public static List<String> knownIssues = new ArrayList<>();
	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static String uin = "";
	public static String perpetualVid = "";
	public static String onetimeuseVid = "";
	public static String temporaryVid = "";

	static TestNG testNg;
	private static final Logger logger = Logger.getLogger(TestRunner.class);

	public static void main(String[] args) throws Exception {
		AdminTestUtil.initialize();
		try (BufferedReader br = new BufferedReader(new FileReader(getResourcePath() + "/config/knownIssues.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					knownIssues.add(line.trim());
				}
			}
			logger.info("Known Issues Loaded: " + knownIssues);
		} catch (Exception e) {
			logger.warn("knownIssues.txt not found or unreadable: " + e.getMessage());
		}
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
			XmlClass sbiCreationTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.SbiCreationTest");
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
			XmlClass partnerDeactivateOptionTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PartnerDeactivateOptionTest");
			XmlClass partnerDeactivatedPortalTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PartnerDeactivatedPortalTest");
			XmlClass deactivatePartner2Creation = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivatePartner2Creation");
			XmlClass partnerDeactivateNavigationTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PartnerDeactivateNavigationTest");
			XmlClass deactivateDevicePartnerCreation = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivateDevicePartnerCreation");
			XmlClass deactivatedDeviceProviderTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivatedDeviceProviderTest");
			XmlClass deactivateFtmPartnerCreation = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivateFtmPartnerCreation");
			XmlClass deactivatedFtmProviderTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.DeactivatedFtmProviderTest");
			XmlClass authPolicyTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.AuthPolicyTest");
			XmlClass partnerPolicyMappingTest = new XmlClass(
					"io.mosip.testrig.pmpuiv2.testcase.PartnerPolicyMappingTest");
			XmlClass mispPartnerTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.MispPartnerTest");
			XmlClass mispPolicyTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.MispPolicyTest");
			XmlClass abisPartnerTest = new XmlClass("io.mosip.testrig.pmpuiv2.testcase.AbisPartnerTest");

			List<XmlClass> classes = new ArrayList<>();
			String[] scenarioNames = ConfigManager.gettestcases().split(",");

			for (String test : scenarioNames) {
				switch (test.trim()) {

				// STEP 1
				case "PartnerAdminCreation":
					addClassIfAbsent(classes, partnerAdminCreation);
					break;
				case "AuthPartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation);
					break;
				case "DevicePartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, devicePartnerCreation);
					break;
				case "FtmPartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, ftmPartnerCreation);
					break;
				case "PolicyAdminAndPartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, policyAdminAndPartnerCreation);
					break;
				case "DeactivatePartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, deactivatePartnerCreation);
					break;

				// STEP 2
				case "FtmDeviceTest":
					addClassIfAbsent(classes, partnerAdminCreation, ftmPartnerCreation, ftmDeviceTest);
					break;
				case "SbiCreationTest":
					addClassIfAbsent(classes, partnerAdminCreation, devicePartnerCreation, sbiCreationTest);
					break;
				case "PolicyCreationForAuthPartner":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation, policyCreationForAuthPartner);
					break;

				// STEP 3
				case "OidcClientAuthPartnerTest":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation, policyCreationForAuthPartner,
							oidcClientAuthPartnerTest);
					break;
				case "DeviceCreationTest":
					addClassIfAbsent(classes, partnerAdminCreation, devicePartnerCreation, sbiCreationTest,
							deviceCreationTest);
					break;
				case "PolicyGroupTest":
					addClassIfAbsent(classes, partnerAdminCreation, policyAdminAndPartnerCreation, policyGroupTest);
					break;

				// STEP 4
				case "CertificateTrustStoreTest":
					addClassIfAbsent(classes, partnerAdminCreation, certificateTrustStoreTest);
					break;
				case "ApiKeyAuthPartnerTest":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation, policyCreationForAuthPartner,
							apiKeyAuthPartnerTest);
					break;
				case "DatasharePolicyTest":
					addClassIfAbsent(classes, partnerAdminCreation, policyAdminAndPartnerCreation, policyGroupTest,
							datasharePolicyTest);
					break;
				case "SbiDeviceProviderTest":
					addClassIfAbsent(classes, partnerAdminCreation, devicePartnerCreation, sbiCreationTest,
							deviceCreationTest, sbiDeviceProviderTest);
					break;

				// STEP 5
				case "AuthPartnerWithoutCertificateTest":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation,
							authPartnerWithoutCertificateTest);
					break;
				case "PartnerDetailsTest":
					addClassIfAbsent(classes, partnerAdminCreation, deactivatePartnerCreation, partnerDetailsTest);
					break;
				case "PartnerDeactivateOptionTest":
					addClassIfAbsent(classes, partnerAdminCreation, deactivatePartnerCreation, partnerDetailsTest,
							partnerDeactivateOptionTest);
					break;
				case "DeactivatePartner2Creation":
					addClassIfAbsent(classes, partnerAdminCreation, deactivatePartnerCreation,
							deactivatePartner2Creation);
					break;
				case "PartnerDeactivateNavigationTest":
					addClassIfAbsent(classes, partnerAdminCreation, deactivatePartnerCreation,
							deactivatePartner2Creation, partnerDeactivateNavigationTest);
					break;
				case "DeactivateDevicePartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, deactivateDevicePartnerCreation);
					break;
				case "DeactivatedDeviceProviderTest":
					addClassIfAbsent(classes, partnerAdminCreation, deactivateDevicePartnerCreation,
							deactivatedDeviceProviderTest);
					break;
				case "DeactivateFtmPartnerCreation":
					addClassIfAbsent(classes, partnerAdminCreation, deactivateFtmPartnerCreation);
					break;
				case "DeactivatedFtmProviderTest":
					addClassIfAbsent(classes, partnerAdminCreation, deactivateFtmPartnerCreation,
							deactivatedFtmProviderTest);
					break;
				// policyCreationForAuthPartner is needed for the policy-request scenario to reach Submit.
				case "PartnerDeactivatedPortalTest":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation, policyCreationForAuthPartner,
							deactivatePartnerCreation, partnerDetailsTest, partnerDeactivateOptionTest,
							partnerDeactivatedPortalTest);
					break;
				case "AuthPolicyTest":
					addClassIfAbsent(classes, partnerAdminCreation, policyAdminAndPartnerCreation, policyGroupTest,
							authPolicyTest);
					break;
				case "PartnerPolicyMappingTest":
					addClassIfAbsent(classes, partnerAdminCreation, authPartnerCreation, partnerPolicyMappingTest);
					break;
				case "MispPartnerTest":
					addClassIfAbsent(classes, partnerAdminCreation, mispPartnerTest);
					break;
				case "MispPolicyTest":
					addClassIfAbsent(classes, partnerAdminCreation, mispPartnerTest, mispPolicyTest);
					break;
				case "AbisPartnerTest":
					addClassIfAbsent(classes, partnerAdminCreation, abisPartnerTest);
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
			homeDir = new File(getResourcePath() + "/testngFile");

			File[] files = homeDir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.getName() != null) {
						suitefiles.add(file.getAbsolutePath());
					}
				}
			} else {
				logger.warn("No test suite files found in: " + homeDir.getAbsolutePath());
			}

			runner.setTestSuites(suitefiles);
		}

		System.getProperties().setProperty("testng.output.dir", "testng-report");
		runner.setOutputDirectory("testng-report");
		System.getProperties().setProperty("emailable.report2.name",
				"PMPUI-" + BaseTestCaseFunc.environment + "-run-" + BaseClass.Date() + "-report.html");

		runner.run();

		// DB cleanup
		DBManager.cleanUpPartnerUiV2Data();
		System.exit(0);
	}

	private static void addClassIfAbsent(List<XmlClass> classes, XmlClass... xmlClasses) {
		for (XmlClass xmlClass : xmlClasses) {
			if (!classes.contains(xmlClass)) {
				classes.add(xmlClass);
			}
		}
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
			return new File(jarUrl).getParentFile().getAbsolutePath().toString() + "/resources";
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
