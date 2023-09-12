package io.mosip.testrig.pmpui.utility;

import java.io.File;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import io.mosip.testrig.pmpui.fw.util.AdminTestUtil;
import io.mosip.testrig.pmpui.testcase.AdminAuthPolicyTest;
import io.mosip.testrig.pmpui.testcase.AdminDataSharePolicyTest;
import io.mosip.testrig.pmpui.testcase.AdminDeviceDetailsTest;
import io.mosip.testrig.pmpui.testcase.AdminFtmDetailsTest;
import io.mosip.testrig.pmpui.testcase.AdminPartnerPolicyMappingTest;
import io.mosip.testrig.pmpui.testcase.AdminPolicyGroupTest;
import io.mosip.testrig.pmpui.testcase.AdminSbiDetailsTest;
import io.mosip.testrig.pmpui.testcase.AdminUploadCaCertTest;
import io.mosip.testrig.pmpui.testcase.PartnerLoginAuthCredTest;
import io.mosip.testrig.pmpui.testcase.PartnerRegisterAuthCredTest;
import io.mosip.testrig.pmpui.testcase.PartnerRegisterFTMTest;
import io.mosip.testrig.pmpui.testcase.PartnerRegisterSbiDeviceTest;
import io.mosip.testrig.pmpui.testcase.apicall;


public class TestRunner {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(TestRunner.class);
	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static TestListenerAdapter tla = new TestListenerAdapter();

	
	static TestNG testNg;
	
	public static void main(String[] args) throws Exception {
		
//		if (checkRunType().equalsIgnoreCase("JAR")) {
//			ExtractResource.removeOldMosipTestTestResource();
//			ExtractResource.extractResourceFromJar();
//		}
		AdminTestUtil.initialize();
		testNg=new TestNG();
		
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
				
		});
		testNg.run();
		
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
			return new File(jarUrl).getParentFile().getAbsolutePath()+"/resources/";
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

}
