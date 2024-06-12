package io.mosip.testrig.pmpui.kernel.util;

import java.util.Map;

import org.json.simple.JSONObject;

import io.mosip.testrig.pmpui.kernel.service.ApplicationLibrary;
import io.mosip.testrig.pmpui.utility.BaseTestCaseFunc;
import io.restassured.response.Response;

public class KernelAuthentication extends io.mosip.testrig.pmpui.utility.BaseTestCaseFunc {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(KernelAuthentication.class);
	private String authRequest="/config/Authorization/request.json";
	private String authInternalRequest="/config/Authorization/internalAuthRequest.json";
	public String zonemapCookie = null;
	String cookie;
	static String dataKey = "response";
	CommonLibrary clib= new CommonLibrary();
	public final Map<String, String> props = clib.readProperty("Kernel");
	private String admin_password = props.get("admin_password");
	private String admin_userName=props.get("admin_userName");
	private String authenticationInternalEndpoint = props.get("authenticationInternal");
	private ApplicationLibrary appl=new ApplicationLibrary();
	
	
	
	
	public String getTokenByRole(String role) {
		return getTokenByRole(role, null);
	}
	
	public String getTokenByRole(String role, String tokenType)
	{
		String insensitiveRole = null;
		if(role!=null)
			insensitiveRole = role.toLowerCase();
		else return "";
		
		switch(insensitiveRole) {
		
		case "idrepo":
			if(!kernelCmnLib.isValidToken(idrepoCookie))
				idrepoCookie = kernelAuthLib.getAuthForIDREPO();
			return idrepoCookie;
		case "admin":
			if(!kernelCmnLib.isValidToken(adminCookie))
				adminCookie = kernelAuthLib.getAuthForAdmin();
			return adminCookie;
		case "globaladmin":
			if(!kernelCmnLib.isValidToken(zonemapCookie))
				zonemapCookie = kernelAuthLib.getAuthForzoneMap();
			return zonemapCookie;
		default:
			if(!kernelCmnLib.isValidToken(adminCookie))
				adminCookie = kernelAuthLib.getAuthForAdmin();
			return adminCookie;			
		}
		 
	}
	
	@SuppressWarnings("unchecked")
	public String getAuthForIDREPO() {
		JSONObject actualrequest = getRequestJson(authRequest);
		
		JSONObject request=new JSONObject();
		request.put("appId", ConfigManager.getidRepoAppId());
		request.put("clientId", ConfigManager.getidRepoClientId());
		request.put("secretKey", ConfigManager.getIdRepoClientSecret());
		actualrequest.put("request", request);
		
		Response reponse=appl.postWithJson(props.get("authclientidsecretkeyURL"), actualrequest);
		cookie=reponse.getCookie("Authorization");
		return cookie;
	}
	
	@SuppressWarnings("unchecked")
	public String getAuthForAdmin() {

		JSONObject actualrequest = getRequestJson(authInternalRequest);

		JSONObject request = new JSONObject();
		request.put("appId", ConfigManager.getAdminAppId());
		request.put("password", admin_password);
		
		//if(BaseTestCase.currentModule==null) admin_userName=
		request.put("userName", BaseTestCaseFunc.currentModule +"-"+ admin_userName);
		
		request.put("clientId", ConfigManager.getAdminClientId());
		request.put("clientSecret", ConfigManager.getAdminClientSecret());
		actualrequest.put("request", request);

		Response reponse = appl.postWithJson(authenticationInternalEndpoint, actualrequest);
		String responseBody = reponse.getBody().asString();
		String token = new org.json.JSONObject(responseBody).getJSONObject(dataKey).getString("token");
		return token;
	}
	@SuppressWarnings("unchecked")
	public String getAuthForzoneMap() {

		JSONObject actualrequest = getRequestJson(authInternalRequest);

		JSONObject request = new JSONObject();
		request.put("appId", ConfigManager.getAdminAppId());
		request.put("password", props.get("admin_zone_password"));
		request.put("userName", props.get("admin_zone_userName"));
		request.put("clientId", ConfigManager.getAdminClientId());
		request.put("clientSecret", ConfigManager.getAdminClientSecret());
		actualrequest.put("request", request);

		Response reponse = appl.postWithJson(authenticationInternalEndpoint, actualrequest);
		String responseBody = reponse.getBody().asString();
		String token = new org.json.JSONObject(responseBody).getJSONObject(dataKey).getString("token");
		return token;
	}
}
