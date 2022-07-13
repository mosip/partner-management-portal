package io.mosip.test.pmptest.utility;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import io.mosip.test.pmptest.testcase.AuthPolicyTest;
import io.mosip.test.pmptest.testcase.DataSharePolicyTest;
import io.mosip.test.pmptest.testcase.DeviceDetailsTest;
import io.mosip.test.pmptest.testcase.FtmDetailsTest;
import io.mosip.test.pmptest.testcase.PartnerPolicyMappingTest;
import io.mosip.test.pmptest.testcase.PolicyGroupTest;
import io.mosip.test.pmptest.testcase.SbiDetailsTest;
import io.mosip.test.pmptest.testcase.UploadFtmCaCertTest;


public class TestRunner {
	static TestListenerAdapter tla = new TestListenerAdapter();

	
	static TestNG testNg;
	
	public static void main(String[] args) throws Exception {
	
		testNg=new TestNG();
		
		String listExcludedGroups=JsonUtil.JsonObjParsing(Commons.getTestData(),"setExcludedGroups");
		testNg.setExcludedGroups(listExcludedGroups);
		testNg.setTestClasses(new Class[] {
				//PartnerPolicyMappingTest.class
				
				SbiDetailsTest.class,
				DeviceDetailsTest.class, 
				FtmDetailsTest.class,
				PolicyGroupTest.class,
				AuthPolicyTest.class,
				DataSharePolicyTest.class,
				PartnerPolicyMappingTest.class
				//UploadFtmCaCertTest.class
				
		
		});
	//	testNg.addListener(tla);
		testNg.run();
		
	}
	

}
