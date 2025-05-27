package io.mosip.testrig.pmpuiv2.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.ws.rs.core.MediaType;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;

import io.mosip.testrig.pmpuiv2.authentication.fw.util.RestClient;
import io.mosip.testrig.pmpuiv2.kernel.util.CommonLibrary;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.kernel.util.KernelAuthentication;
import io.restassured.response.Response;

//import org.apache.log4j.Logger;

public class BaseTestCaseFunc {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(BaseTestCaseFunc.class);
	private static String zoneMappingRequest = "/config/Authorization/zoneMappingRequest.json";
	private static String partnerCreationRequest = "/config/Authorization/PartnerCreationRequest.json";
	private static String definePolicyGroupRequest = "/config/Authorization/DefinePolicyGroupRequest.json";
	private static String definePolicyRequest = "/config/Authorization/DefinePolicyRequet.json";
	private static String uploadCaCert = "/ca_cert/caCertApi.json";
	private static String uploadSubCaCert = "/ca_cert/caCertApi.json";
	
	
	public static String environment;
	public static List<String> languageList = new ArrayList<>();
	public static String ApplnURI;
	public static String ApplnURIForKeyCloak;
	public static String testLevel;
	public static Properties props = getproperty(
			TestRunner.getResourcePath() + "/config/application.properties");
	public static Properties propsKernel = getproperty(
			TestRunner.getResourcePath() + "/config/"+TestRunner.GetKernalFilename());
	public static Properties propsMap = getproperty(
			TestRunner.getResourcePath() + "/config/valueMapping.properties");
	public static Properties propsBio = getproperty(
			TestRunner.getResourcePath() + "/config/bioValue.properties"); 
			

	public static String SEPRATOR = "";
	public static String currentModule = "pmpui";
	public final static String COOKIENAME = "Authorization";
	public static CommonLibrary kernelCmnLib = null;
	public static KernelAuthentication kernelAuthLib = null;
	public String adminCookie = null;
	public String idrepoCookie = null;
	public static Map<?, ?> queries;
	public static Map<?, ?> residentQueries;
	public static Map<?, ?> partnerQueries;
	public static String uinEmail;
	public static String uinPhone;
	
	
    
	public static String getOSType() {
		String type = System.getProperty("os.name");
		if (type.toLowerCase().contains("windows")) {
			SEPRATOR = "\\\\";
			return "WINDOWS";
		} else if (type.toLowerCase().contains("linux") || type.toLowerCase().contains("unix")) {
			SEPRATOR = "/";
			return "OTHERS";
		}
		return null;
	}
    
	public static List<String> getLanguageList() {
		logger.info("We have created a Config Manager. Beginning to read properties!");

		environment = ConfigManager.getiam_apienvuser();
		logger.info("Environemnt is  ==== :" + environment);
		ApplnURI = ConfigManager.getiam_apiinternalendpoint();
		logger.info("Application URI ======" + ApplnURI);

		logger.info("Configs from properties file are set.");
		if (!languageList.isEmpty()) {
			return languageList;
		}
		
		String url = ApplnURI + props.getProperty("preregLoginConfigUrl");
		logger.info("preregLoginConfigUrl" + url);
		Response response = RestClient.getRequest(url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
		org.json.JSONObject responseJson = new org.json.JSONObject(response.asString());
		org.json.JSONObject responseValue = (org.json.JSONObject) responseJson.get("response");
		String mandatoryLanguage = (String) responseValue.get("mosip.mandatory-languages");

		languageList.add(mandatoryLanguage);
		languageList.addAll(Arrays.asList(((String) responseValue.get("mosip.optional-languages")).split(",")));

		return languageList;
	}
	
	public static Properties getproperty(String path) {
		Properties prop = new Properties();

		try {
			File file = new File(path);
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			logger.error("Exception " + e.getMessage());
		}
		return prop;
	}
	
	public static void initialize() {
		//PropertyConfigurator.configure(getLoggerPropertyConfig());
		kernelAuthLib = new KernelAuthentication();
		kernelCmnLib = new CommonLibrary();
		queries = kernelCmnLib.readProperty("adminQueries");
		partnerQueries = kernelCmnLib.readProperty("partnerQueries");
		residentQueries = kernelCmnLib.readProperty("residentServicesQueries");
		/**
		 * Make sure test-output is there
		 */

		getOSType();
		logger.info("We have created a Config Manager. Beginning to read properties!");

		environment = ConfigManager.getiam_apienvuser();
		logger.info("Environemnt is from baseTestCase ==== :" + environment);
		ApplnURI = ConfigManager.getiam_apiinternalendpoint();
		logger.info("Application URI end point ======" + ApplnURI);
		ApplnURIForKeyCloak = ConfigManager.getIAMUrl();
		logger.info("Application URI  key clock======" + ApplnURIForKeyCloak);
		testLevel = System.getProperty("env.testLevel");
		logger.info("Test Level ======" + testLevel);
		// languageList =Arrays.asList(System.getProperty("env.langcode").split(","));

		// langcode = System.getProperty("env.langcode");
		logger.info("Test Level ======" + languageList);

		logger.info("Configs from properties file are set.");

	}
	
	private static Properties getLoggerPropertyConfig() {
		Properties logProp = new Properties();
		logProp.setProperty("log4j.rootLogger", "INFO, Appender1,Appender2");
		logProp.setProperty("log4j.appender.Appender1", "org.apache.log4j.ConsoleAppender");
		logProp.setProperty("log4j.appender.Appender1.layout", "org.apache.log4j.PatternLayout");
		logProp.setProperty("log4j.appender.Appender1.layout.ConversionPattern", "%-7p %d [%t] %c %x - %m%n");
		logProp.setProperty("log4j.appender.Appender2", "org.apache.log4j.FileAppender");
		logProp.setProperty("log4j.appender.Appender2.File", "src/logs/mosip-api-test.log");
		logProp.setProperty("log4j.appender.Appender2.layout", "org.apache.log4j.PatternLayout");
		logProp.setProperty("log4j.appender.Appender2.layout.ConversionPattern", "%-7p %d [%t] %c %x - %m%n");
		return logProp;
	}
	
	public static JSONObject getRequestJson(String filepath) {
		return kernelCmnLib.readJsonData(filepath, true);

	}
	@SuppressWarnings("unchecked")
	public static void mapUserToZone(String user, String zone) {
		String token = kernelAuthLib.getTokenByRole("globalAdmin");
		String url = ApplnURI + propsKernel.getProperty("zoneMappingUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(zoneMappingRequest);
		JSONObject request = new JSONObject();
		request.put("zoneCode", zone);
		request.put("userId", user);
		request.put("langCode", BaseTestCaseFunc.getLanguageList().get(0));
		request.put("isActive","true");
		actualrequest.put("request", request);
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(user + "Mapped to" + zone + "Zone");
		logger.info(response.toString());
	}
	public static void mapZone(String user) {
		String token = kernelAuthLib.getTokenByRole("globalAdmin");
		String url = ApplnURI + propsKernel.getProperty("zoneMappingActivateUrl");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("isActive","true");
		map.put("userId", user);
		Response response = RestClient.patchRequestWithCookieAndQueryParm(url, map, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public static void DefinePolicyGroup() {
		String token = kernelAuthLib.getTokenByRole("revampadmin");
		String url = ApplnURI + propsKernel.getProperty("definePolicyGroupUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(definePolicyGroupRequest);
		JSONObject request = new JSONObject();
		request.put("desc", "desc automationui policy group");
		request.put("name", "automationui policy group");		
		actualrequest.put("request", request);
		actualrequest.put("requesttime", generateCurrentUTCTimeStamp());
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static void DefinePolicy() {
		String token = kernelAuthLib.getTokenByRole("revampadmin");
		String url = ApplnURI + propsKernel.getProperty("definePolicyUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(definePolicyRequest);
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static void CreateAdminPartner() {
		String token = kernelAuthLib.getTokenByRole("revampadmin");
		String url = ApplnURI + propsKernel.getProperty("revampCreatePartnerUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(partnerCreationRequest);
		JSONObject request = new JSONObject();
		request.put("address", "Bangalore");
		request.put("contactNumber", "8553967572");
		request.put("emailId", "automationpmpui-revamp" + BaseClass.data + "@automationlabs.com");
		request.put("organizationName","AABBCC");
		request.put("partnerId","pmpui-revamp");
		request.put("partnerType","Auth_Partner");
		request.put("policyGroup","automationui policy group");
		
		actualrequest.put("request", request);
		actualrequest.put("requesttime", generateCurrentUTCTimeStamp());
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	public static void CreateAuthPartner() {
		String token = kernelAuthLib.getTokenByRole("revampAuth");
		String url = ApplnURI + propsKernel.getProperty("revampCreatePartnerUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(partnerCreationRequest);
		JSONObject request = new JSONObject();
		request.put("address", "Bangalore");
		request.put("contactNumber", "8553967571");
		request.put("emailId", "automationpmpui-auth001317281@automationlabs.com");
		request.put("organizationName","AABBCC");
		request.put("partnerId","pmpui-auth");
		request.put("partnerType","Auth_Partner");
		request.put("policyGroup","automationui policy group");
		
		actualrequest.put("request", request);
		actualrequest.put("requesttime", generateCurrentUTCTimeStamp());
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	public static void UploadCaCertPartner() {
		String token = kernelAuthLib.getTokenByRole("revampadmin");
		String url = ApplnURI + propsKernel.getProperty("uploadCaCertUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(uploadCaCert);
		
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	public static void UploadSubCaCertPartner() {
		String token = kernelAuthLib.getTokenByRole("revampadmin");
		String url = ApplnURI + propsKernel.getProperty("uploadCaCertUrl");
		org.json.simple.JSONObject actualrequest = getRequestJson(uploadSubCaCert);
		
		logger.info(actualrequest.toJSONString());
		Response response = RestClient.postRequestWithCookie(url, actualrequest, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, "Authorization", token);
		logger.info(response.toString());
	}
	
	public static String generateCurrentUTCTimeStamp() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
	

}