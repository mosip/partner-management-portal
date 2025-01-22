package io.mosip.testrig.pmprevampui.kernel.service;

import javax.ws.rs.core.MediaType;

import io.mosip.testrig.pmprevampui.kernel.util.CommonLibrary;
import io.mosip.testrig.pmprevampui.utility.BaseTestCaseFunc;
import io.restassured.response.Response;

public class ApplicationLibrary extends BaseTestCaseFunc {
	private static CommonLibrary commonLibrary = new CommonLibrary();
	
	
	// get requests
	public Response getWithoutParams(String endpoint, String cookie) {
		return commonLibrary.getWithoutParams(ApplnURI + endpoint, cookie);
	}
	
	public Response postWithJson(String endpoint, Object body) {
		return commonLibrary.postWithJson(ApplnURI + endpoint, body, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON);
	}
}