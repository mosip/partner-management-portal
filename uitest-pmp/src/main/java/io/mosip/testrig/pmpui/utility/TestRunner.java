package io.mosip.testrig.pmpui.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


//import io.mosip.testrig.pmpui.dbaccess.DBManager;
import io.mosip.testrig.pmpui.fw.util.AdminTestUtil;
import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.testcase.*;







public class TestRunner {
	private static final Logger logger = Logger.getLogger(TestRunner.class);
	static TestListenerAdapter tla = new TestListenerAdapter();

	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static String uin="";
	public static String perpetualVid="";
	public static String onetimeuseVid="";
	public static String temporaryVid="";

	static TestNG testNg;

	public static void main(String[] args) throws Exception {

		//		if (checkRunType().equalsIgnoreCase("JAR")) {
		//			ExtractResource.removeOldMosipTestTestResource();
		//			ExtractResource.extractResourceFromJar();
		//		}
		AdminTestUtil.initialize();
		//DBManager.clearMasterDbData();
		//ConfigManager.getMasterDbPass();
		/*	testNg=new TestNG();

		String listExcludedGroups=JsonUtil.JsonObjParsing(Commons.getTestData(),"setExcludedGroups");
		testNg.setExcludedGroups(listExcludedGroups);
		//testNg.setPreserveOrder(true);
		testNg.setTestClasses(new Class[] {
				AdminAuthPolicyTest.class,
				AdminDataSharePolicyTest.class,
				AdminDeviceDetailsTest.class,
				AdminFtmDetailsTest.class,
				AdminPartnerPolicyMappingTest.class,
				AdminPolicyGroupTest.class,
				AdminSbiDetailsTest.class,
				AdminUploadCaCertTest.class,
				PartnerLoginAuthCredTest.class,
				PartnerRegisterAuthCredTest.class,
				PartnerRegisterFTMTest.class,
				PartnerRegisterSbiDeviceTest.class,apicall.class

		});*/
		//		testNg.run();
		startTestRunner();
	}

	public static void startTestRunner() throws Exception {
		File homeDir = null;
		TestNG runner = new TestNG();
		if(!ConfigManager.gettestcases().equals("")) {
			XmlSuite suite = new XmlSuite();
			suite.setName("MySuite");
			suite.addListener("io.mosip.testrig.pmpui.utility.EmailableReport");
			XmlClass AdminAuthPolicyTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminAuthPolicyTest");
			XmlClass AdminDataSharePolicyTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminDataSharePolicyTest");
			XmlClass AdminDeviceDetailsTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminDeviceDetailsTest");
			XmlClass AdminFtmDetailsTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminFtmDetailsTest");
			XmlClass AdminPartnerPolicyMappingTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminPartnerPolicyMappingTest");
			XmlClass AdminPolicyGroupTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminPolicyGroupTest");
			XmlClass AdminSbiDetailsTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminSbiDetailsTest");
			XmlClass AdminUploadCaCertTest = new XmlClass("io.mosip.testrig.pmpui.testcase.AdminUploadCaCertTest");
			XmlClass PartnerLoginAuthCredTest = new XmlClass("io.mosip.testrig.pmpui.testcase.PartnerLoginAuthCredTest");
			XmlClass PartnerRegisterAuthCredTest = new XmlClass("io.mosip.testrig.pmpui.testcase.PartnerRegisterAuthCredTest");
			XmlClass PartnerRegisterFTMTest = new XmlClass("io.mosip.testrig.pmpui.testcase.PartnerRegisterFTMTest");
			XmlClass PartnerRegisterSbiDeviceTest= new XmlClass("io.mosip.testrig.pmpui.testcase.PartnerRegisterSbiDeviceTest");

			List<XmlClass> classes = new ArrayList<>();
			String[] Scenarioname=ConfigManager.gettestcases().split(",");
			for(String test:Scenarioname) {
				if(test.equals("adminAuthPolicyTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
				}

				if(test.equals("adminDataSharePolicyTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
				}

				if(test.equals("adminDeviceDetailsTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDeviceDetailsTest);
					classes.add(AdminSbiDetailsTest);
					classes.add(PartnerRegisterSbiDeviceTest);
					classes.add(AdminDataSharePolicyTest);
				}

				if(test.equals("adminFtmDetailsTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(PartnerRegisterFTMTest);
					classes.add(AdminFtmDetailsTest);
				}

				if(test.equals("adminPartnerPolicyMappingTest")) {
					classes.add(PartnerRegisterAuthCredTest);
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
					classes.add(AdminPartnerPolicyMappingTest);
				}
					

				if(test.equals("adminPolicyGroupTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
				}

				if(test.equals("adminSbiDetailsTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
					classes.add(PartnerRegisterSbiDeviceTest);
					classes.add(AdminSbiDetailsTest);
				}

				if(test.equals("adminUploadCaCertTest"))
					classes.add(AdminUploadCaCertTest);


				if(test.equals("partnerLoginAuthCredTest")) {
					classes.add(PartnerRegisterAuthCredTest);
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
					classes.add(AdminPartnerPolicyMappingTest);
					classes.add(PartnerLoginAuthCredTest);
				}
				if(test.equals("partnerRegisterAuthCredTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
					classes.add(PartnerRegisterAuthCredTest);
				}
				if(test.equals("partnerRegisterFTMTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(PartnerRegisterFTMTest);
				}
				if(test.equals("partnerRegisterSbiDeviceTest")) {
					classes.add(AdminUploadCaCertTest);
					classes.add(AdminPolicyGroupTest);
					classes.add(AdminAuthPolicyTest);
					classes.add(AdminDataSharePolicyTest);
					classes.add(PartnerRegisterSbiDeviceTest);
				}



			}


			XmlTest test = new XmlTest(suite);
			test.setName("MyTest");
			test.setXmlClasses(classes);

			List<XmlSuite> suites = new ArrayList<>();
			suites.add(suite);

			runner.setXmlSuites(suites);

		}else {
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
		// Set other properties and run TestNG
		System.getProperties().setProperty("testng.outpur.dir", "testng-report");
		runner.setOutputDirectory("testng-report");
		System.getProperties().setProperty("emailable.report2.name", "PMPUI-" + BaseTestCaseFunc.environment 
				+ "-run-" + System.currentTimeMillis() + "-report.html");

		runner.run();

		//	DBManager.clearMasterDbData();
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
			return new File(jarUrl).getParentFile().getAbsolutePath().toString()+"/resources/";
		} else if (checkRunType().equalsIgnoreCase("IDE")) {
			String path = System.getProperty("user.dir") + System.getProperty("path.config");

			//	String path = new File(TestRunner.class.getClassLoader().getResource("").getPath()).getAbsolutePath()
			//				.toString();
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
	public static String GetKernalFilename(){
		String path = System.getProperty("env.user");
		String kernalpath=null;
		if(System.getProperty("env.user")==null || System.getProperty("env.user").equals("")) {
			kernalpath="Kernel.properties";

		}else {
			kernalpath="Kernel_"+path+".properties";
		}
		return kernalpath;
	}


}
