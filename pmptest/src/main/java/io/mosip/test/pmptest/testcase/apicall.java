package io.mosip.test.pmptest.testcase;


import static org.testng.Assert.assertEquals;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class apicall {

    private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(apicall.class);

	

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