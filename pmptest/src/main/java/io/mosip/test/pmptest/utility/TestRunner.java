package io.mosip.test.pmptest.utility;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import io.mosip.test.pmptest.testcase.AuthPolicyTest;
import io.mosip.test.pmptest.testcase.DataSharePolicyTest;
import io.mosip.test.pmptest.testcase.ZDeviceDetailsTest;
import io.mosip.test.pmptest.testcase.FtmDetailsTest;
import io.mosip.test.pmptest.testcase.PartnerPolicyMappingTest;
import io.mosip.test.pmptest.testcase.PolicyGroupTest;
import io.mosip.test.pmptest.testcase.RegisterTest;
import io.mosip.test.pmptest.testcase.SbiDetailsTest;
import io.mosip.test.pmptest.testcase.UploadFtmCaCertTest;


public class TestRunner {
	static TestListenerAdapter tla = new TestListenerAdapter();

	
	static TestNG testNg;
	
	public static void main(String[] args) throws Exception {
	
		testNg=new TestNG();
		
		String listExcludedGroups=JsonUtil.JsonObjParsing(Commons.getTestData(),"setExcludedGroups");
		testNg.setExcludedGroups(listExcludedGroups);
		testNg.setPreserveOrder(true);
		testNg.setTestClasses(new Class[] {
				AuthPolicyTest.class,
				DataSharePolicyTest.class,
				PartnerPolicyMappingTest.class,
				PolicyGroupTest.class,
				UploadFtmCaCertTest.class,	RegisterTest.class,FtmDetailsTest.class,
				
				SbiDetailsTest.class,ZDeviceDetailsTest.class
		});
		testNg.run();
		
	}
	

}
