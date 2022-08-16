package io.mosip.test.pmptest.utility;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import io.mosip.test.pmptest.testcase.PartnerLoginAuthCredTest;
import io.mosip.test.pmptest.testcase.PartnerRegisterAuthCredTest;
import io.mosip.test.pmptest.testcase.PartnerRegisterFTMTest;
import io.mosip.test.pmptest.testcase.PartnerRegisterSbiDeviceTest;
import io.mosip.test.pmptest.testcase.AdminAuthPolicyTest;
import io.mosip.test.pmptest.testcase.AdminDataSharePolicyTest;
import io.mosip.test.pmptest.testcase.AdminDeviceDetailsTest;
import io.mosip.test.pmptest.testcase.AdminFtmDetailsTest;
import io.mosip.test.pmptest.testcase.AdminPartnerPolicyMappingTest;
import io.mosip.test.pmptest.testcase.AdminPolicyGroupTest;
import io.mosip.test.pmptest.testcase.TBD;
import io.mosip.test.pmptest.testcase.AdminSbiDetailsTest;
import io.mosip.test.pmptest.testcase.AdminUploadCaCertTest;


public class TestRunner {
	static TestListenerAdapter tla = new TestListenerAdapter();

	
	static TestNG testNg;
	
	public static void main(String[] args) throws Exception {
	
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
				PartnerRegisterSbiDeviceTest.class
				
		});
		testNg.run();
		
	}
	

}
