package io.mosip.testrig.pmpui.fw.util;
import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import io.mosip.testrig.pmpui.authentication.fw.precon.JsonPrecondtion;
import io.mosip.testrig.pmpui.authentication.fw.util.RestClient;
import io.mosip.testrig.pmpui.dbaccess.DBManager;
import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.kernel.util.KernelAuthentication;
import io.mosip.testrig.pmpui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpui.utility.BaseTestCaseFunc;
import io.mosip.testrig.pmpui.utility.TestRunner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AdminTestUtil extends BaseTestCaseFunc  {

	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(RestClient.class);
	public static String token;
	public static String user;
	public static String tokenRoleIdRepo = "idrepo";
	public static String tokenRoleAdmin = "admin";
	private static String serverComponentsCommitDetails;
	public static String propsHealthCheckURL = TestRunner.getResourcePath() + "/"
			+ "config/healthCheckEndpoint.properties";
	public static boolean initialized = false;
	
	public static String getUnUsedUIN(String role){
		
		return JsonPrecondtion
				.getValueFromJson(
						RestClient.getRequestWithCookie(ApplnURI + "/v1/idgenerator/uin", MediaType.APPLICATION_JSON,
								MediaType.APPLICATION_JSON, COOKIENAME,
								new KernelAuthentication().getTokenByRole(role)).asString(),
						"response.uin");
	}
	
	public static String getMasterDataSchema(String role){
		String url = ApplnURI + props.getProperty("masterSchemaURL");
		kernelAuthLib = new KernelAuthentication();
		String token = kernelAuthLib.getTokenByRole("admin");

		Response response = RestClient.getRequestWithCookie(url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
				"Authorization", token);

		return response.asString();
	}
	
	public static String generateCurrentUTCTimeStamp() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
	
	public static boolean activateUIN(String inputJson, String role) {
		Response response = null;
		
		token = kernelAuthLib.getTokenByRole(role);
		response = RestClient.postRequestWithCookie(ApplnURI + props.getProperty("addIdentityURL"), inputJson, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON, COOKIENAME, token);
		JSONObject responseJson = new JSONObject(response.asString());
		
		
		logger.info("responseJson = " + responseJson);
		
		return responseJson.getJSONObject("response").getString("status").equalsIgnoreCase("ACTIVATED");
	}
	
	public static String buildaddIdentityRequestBody(String schemaJson, String uin, String rid) {
    	org.json.JSONObject schemaresponseJson = new org.json.JSONObject(schemaJson);
    	
		org.json.JSONObject schemaData = (org.json.JSONObject) schemaresponseJson.get("response");
		Double schemaVersion = (Double) schemaData.get("idVersion");
		String schemaJsonData = schemaData.getString("schemaJson");
		String schemaFile = schemaJsonData.toString();
		
		JSONObject schemaFileJson = new JSONObject(schemaFile); // jObj
		JSONObject schemaPropsJson = schemaFileJson.getJSONObject("properties"); // objIDJson4
		JSONObject schemaIdentityJson = schemaPropsJson.getJSONObject("identity"); // objIDJson
		JSONObject identityPropsJson = schemaIdentityJson.getJSONObject("properties"); // objIDJson2
		JSONArray requiredPropsArray = schemaIdentityJson.getJSONArray("required"); // objIDJson1
		logger.info("requiredPropsArray = " + requiredPropsArray);
		
		JSONObject requestJson = new JSONObject();
		
		requestJson.put("id", propsMap.getProperty("id"));
		requestJson.put("request", new HashMap<>());
		requestJson.getJSONObject("request").put("registrationId", rid);		
		JSONObject identityJson = new JSONObject();
		identityJson.put("UIN", uin);
		
		for (int i = 0, size = requiredPropsArray.length(); i < size; i++) {
			String eachRequiredProp = requiredPropsArray.getString(i); // objIDJson3

			JSONObject eachPropDataJson = (JSONObject) identityPropsJson.get(eachRequiredProp); // rc1

			if (eachPropDataJson.has("$ref") && eachPropDataJson.get("$ref").toString().contains("simpleType")) {

				JSONArray eachPropDataArray = new JSONArray(); // jArray
				
				for (int j = 0; j < BaseTestCaseFunc.getLanguageList().size(); j++) {
					JSONObject eachValueJson = new JSONObject(); // studentJSON
					eachValueJson.put("language", BaseTestCaseFunc.getLanguageList().get(j));
					eachValueJson.put("value", propsMap.getProperty(eachRequiredProp) + BaseTestCaseFunc.getLanguageList().get(j));
					eachPropDataArray.put(eachValueJson);
				}
				identityJson.put(eachRequiredProp, eachPropDataArray);
	        }
			else {
				if (eachRequiredProp.equals("proofOfIdentity")) {
					identityJson.put(eachRequiredProp, new HashMap<>());
					identityJson.getJSONObject(eachRequiredProp).put("format", "txt");
					identityJson.getJSONObject(eachRequiredProp).put("type", "DOC001");
					identityJson.getJSONObject(eachRequiredProp).put("value", "fileReferenceID");
				}
				else if (eachRequiredProp.equals("individualBiometrics")) {
					identityJson.put(eachRequiredProp, new HashMap<>());
					identityJson.getJSONObject(eachRequiredProp).put("format", "cbeff");
					identityJson.getJSONObject(eachRequiredProp).put("version", 1);
					identityJson.getJSONObject(eachRequiredProp).put("value", "fileReferenceID");
				}

				else if (eachRequiredProp.equals("IDSchemaVersion")) {
					identityJson.put(eachRequiredProp, schemaVersion);
				}

				else {
					identityJson.put(eachRequiredProp, propsMap.getProperty(eachRequiredProp));
					if (eachRequiredProp.equals("email")) {
						uinEmail = propsMap.getProperty(eachRequiredProp);
					}
					if (eachRequiredProp.equals("phone")) {
						uinPhone = propsMap.getProperty(eachRequiredProp);
					}
				}				
			}
        }
		
		JSONArray requestDocArray = new JSONArray();
		JSONObject docJson = new JSONObject();
		docJson.put("value", propsBio.getProperty("BioValue"));
		docJson.put("category", "individualBiometrics");
		requestDocArray.put(docJson);
		
		requestJson.getJSONObject("request").put("documents", requestDocArray);
		requestJson.getJSONObject("request").put("identity", identityJson);
		requestJson.put("requesttime", generateCurrentUTCTimeStamp());
		requestJson.put("version", "v1");
		
		logger.info("requestJson="+requestJson);
		return requestJson.toString();
    }
	
	public static String getServerComponentsDetails() {
		if (serverComponentsCommitDetails != null && !serverComponentsCommitDetails.isEmpty())
			return serverComponentsCommitDetails;

		File file = new File(propsHealthCheckURL);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().equals("") || line.trim().startsWith("#"))
					continue;
				String[] parts = line.trim().split("=");
				if (parts.length > 1) {
					if (ConfigManager.isInServiceNotDeployedList(parts[1])) {
						continue;
					}
					stringBuilder.append("\n")
							.append(getCommitDetails(BaseTestCaseFunc.ApplnURI + parts[1].replace("health", "info")));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdminTestUtil.closeBufferedReader(bufferedReader);
			AdminTestUtil.closeFileReader(fileReader);
		}
		serverComponentsCommitDetails = stringBuilder.toString();
		return serverComponentsCommitDetails;
	}
	public static void closeBufferedReader(BufferedReader bufferedReader) {
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
	//			logger.error(GlobalConstants.EXCEPTION_STRING_2 + e.getMessage());
			}
		}
	}
	
	public static void closeFileReader(FileReader fileReader) {
		if (fileReader != null) {
			try {
				fileReader.close();
			} catch (IOException e) {
			//	logger.error(GlobalConstants.EXCEPTION_STRING_2 + e.getMessage());
			}
		}
	}

	public static String getCommitDetails(String path) {

		Response response = null;
		response = given().contentType(ContentType.JSON).get(path);
		if (response != null && response.getStatusCode() == 200) {
			logger.info(response.getBody().asString());
			JSONObject jsonResponse = new JSONObject(response.getBody().asString());
			return "Group: " + jsonResponse.getJSONObject("build").getString("group") + ", Artifact: "
					+ jsonResponse.getJSONObject("build").getString("artifact") + ", version: "
					+ jsonResponse.getJSONObject("build").getString("version") + ", Commit ID: "
					+ jsonResponse.getJSONObject("git").getJSONObject("commit").getString("id");
		}
		return path + "- No Response";
	}
	 public static String UserMapping() {
		return user;
		 
	 }
	
//	 public static String generateUIN() {
//	    	String uin = "";
//	    	
//	    	initialize();
//	    	
//			DateFormat dateFormatter = new SimpleDateFormat("YYYYMMddHHmmss");
//			Calendar cal = Calendar.getInstance();
//			String timestampValue = dateFormatter.format(cal.getTime());
//			String rid = "27847" + RandomStringUtils.randomNumeric(10) + timestampValue;
//	    	
//	    	// Make Unused UIN Api call to get the UIN Number
//	    	uin = AdminTestUtil.getUnUsedUIN(tokenRoleIdRepo);
//	    	
//	    	// Call Masterdata Schema API To get the Schema Data Of the Env
//	    	String responseString = AdminTestUtil.getMasterDataSchema(tokenRoleAdmin);
//			
//			// Build request body for add identity API
//			String requestjson = AdminTestUtil.buildaddIdentityRequestBody(responseString, uin, rid);
//			
//			
//	    	// Make Add Identity API Call and activate the UIN
//			if (AdminTestUtil.activateUIN(requestjson, tokenRoleIdRepo) == false) {
//				// UIN activation failed
//				return "";
//			}		
//	    	
//	    	return uin;
//	    }
	 
	 
//	 public static String generateVID(String uin, String vidType) {
//	    	if (uin.isEmpty() || vidType.isEmpty()) {
//	    		return "";
//	    	}
//	    	
//	    	initialize();
//	    	Response response = null;
//			
//			String token = BaseTestCase.kernelAuthLib.getTokenByRole(tokenRoleIdRepo);
//	    	JSONObject requestJson = new JSONObject();
//	    	
//	    	requestJson.put("id", "mosip.vid.create");
//	    	requestJson.put("metadata", new HashMap<>());
//	    	requestJson.put("requesttime", AdminTestUtil.generateCurrentUTCTimeStamp());
//	    	requestJson.put("version", "v1");
//	    	requestJson.put("request", new HashMap<>());
//	    	requestJson.getJSONObject("request").put("UIN", uin);
//	    	requestJson.getJSONObject("request").put("vidType", vidType);
//	    	
//	    	response = RestClient.postRequestWithCookie(BaseTestCase.ApplnURI + BaseTestCase.props.getProperty("idRepoGenVidURL"), requestJson.toString(), MediaType.APPLICATION_JSON,
//					MediaType.APPLICATION_JSON, BaseTestCase.COOKIENAME, token);
//	    	JSONObject responseJson = new JSONObject(response.asString());
//			
//			
//			logger.info("responseJson = " + responseJson);
//			
//			if (responseJson.getJSONObject("response").getString("vidStatus").equalsIgnoreCase("ACTIVE")) {
//				return responseJson.getJSONObject("response").getString("VID");
//			}
//			
//	    	return "";
//	    }
	 
	 public static void initialize() {
		 String adminuser="";
	    	if (initialized == false) {
	    		ConfigManager.init();
	    		
	    		
	        	BaseTestCaseFunc.initialize();
	    		// Initializing or setting up execution
	    		 //Langauge Independent
	    		
	        	// Generate Keycloak Users
	        	HashMap<String, List<String>> attrmap=new HashMap<String, List<String>>();
				List<String> list=new ArrayList<String>();
				String val= "11000000";
				list.add(val);
				attrmap.put("individualId",list);
	        	KeycloakUserManager.createUsers();
	        	//String user=BaseTestCaseFunc.currentModule+"-"+propsKernel.getProperty("admin_userName");
	        //	KeycloakUserManager.createUsers(user,"mosip123","roles", attrmap);
	        	BaseTestCaseFunc.mapUserToZone(BaseTestCaseFunc.currentModule+"-"+propsKernel.getProperty("admin_userName"),"CSB");
	    		BaseTestCaseFunc.mapZone(BaseTestCaseFunc.currentModule+"-"+propsKernel.getProperty("admin_userName"));	
	    		initialized = true;
	    	}
	    }
	
}