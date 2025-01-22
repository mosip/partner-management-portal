package io.mosip.testrig.pmpui.authentication.fw.precon;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.testrig.pmpui.utility.BaseTestCaseFunc;

public class JsonPrecondtion {
	private static final org.slf4j.Logger JSONPRECONDATION_LOGGER= org.slf4j.LoggerFactory.getLogger(JsonPrecondtion.class);
	
	public static String getValueFromJson(String jsonContent, String fieldMapper) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object jsonObj = mapper.readValue(jsonContent, Object.class);
			return PropertyUtils.getProperty(jsonObj, fieldMapper).toString();
		} catch (Exception expection) {
			JSONPRECONDATION_LOGGER
					.error("Exception Occured in retrieving the value from json file: " + expection.getMessage());
			return "Cannot retrieve data or content for the object mapper from  JSON";
		}
	}
}