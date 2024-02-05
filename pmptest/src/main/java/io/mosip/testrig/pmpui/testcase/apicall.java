package io.mosip.testrig.pmpui.testcase;


import static org.testng.Assert.assertEquals;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpui.utility.BaseClass;
import io.mosip.testrig.pmpui.utility.RealTimeReport;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Listeners(RealTimeReport.class)
public class apicall extends BaseClass{
public static void main(String[] args) {
	 getAuthForPartner();
}
    private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(apicall.class);

    
    @Test(groups = "API")
public static  String getAuthForPartner() {		
	String ci=null,cs = null,pwd = null,un = null,time = null;
	
	String requestBody="{\"id\":\"string\",\"version\":\"string\",\"requesttime\":\""+
time+"\",\"metadata\":{},\"request\":{\"userName\":\""+
			un+"\",\"password\":"+pwd+"\",\"appId\":\"partner\",\"clientId\":"+ci+"\",\"clientSecret\":"+cs+"\"}}";
	Response response = RestAssured.given().baseUri("https://pmp.dev1.mosip.net/"+"/v1/authmanager/authenticate/internal/useridPwd")
			.contentType(ContentType.JSON).and().body(requestBody).when().post().then().extract().response();
	String responseBody = response.getBody().asString();
	String token = new org.json.JSONObject(responseBody).getJSONObject("").getString("token");
	return token;	
//	
//		JSONObject request=new JSONObject();
//		request.put("appId", "partner");
//		request.put("password", partner_password);
//		request.put("userName", partner_userName);	
//		JSONObject actualInternalrequest = getRequestJson(authInternalRequest);
//		request.put("clientId", ConfigManager.getPmsClientId());
//		request.put("clientSecret", ConfigManager.getPmsClientSecret());
//		actualInternalrequest.put("request", request);
//		Response reponse=appl.postWithJson(authenticationInternalEndpoint, actualInternalrequest);
//		String responseBody = reponse.getBody().asString();
//		String token = new org.json.JSONObject(responseBody).getJSONObject(dataKey).getString("token");
//		return token;			
	}

	public void setMDSscore(String type, String qualityScore) {

		try {
			String requestBody = "{\"type\":\"" + type + "\",\"qualityScore\":\"" + qualityScore
					+ "\",\"fromIso\":false}";

			Response response = RestAssured.given().baseUri("http://127.0.0.1:4501/admin/score")
					.contentType(ContentType.JSON).and().body(requestBody).when().post().then().extract().response();


			assertEquals(200, response.statusCode());
			assertEquals("Success", response.jsonPath().getString("errorInfo"));
		} catch (Exception e) {
			logger.error("Issue with the Rest Assured MOCKMDS Score Request", e);
		}
	}


    public void setMDSprofile(String type,String profile) {
        try {
            String requestBody = "{\"type\":\""+type+"\",\"profileId\":\"" + profile + "\"}";

            Response response = RestAssured.given().baseUri("http://127.0.0.1:4501/admin/profile")
                    .contentType(ContentType.JSON).and().body(requestBody).when().post().then().extract().response();
            assertEquals(200, response.statusCode());
            assertEquals("Success", response.jsonPath().getString("errorInfo"));

        } catch (Exception e) {
            logger.error("Issue with the Rest Assured MOCKMDS Profile Request", e);
        }
    }
    
    
}