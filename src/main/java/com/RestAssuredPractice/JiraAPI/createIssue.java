package com.RestAssuredPractice.JiraAPI;

import com.RestAssuredPractice.Data.JiraJsonPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class createIssue {


    public static void main(String[] args) {

        RestAssured.baseURI="http://localhost:8080";

        SessionFilter sessionFilter = new SessionFilter();
        //Logging in and getting SessionID
      String loginResponse = given().header("Content-Type","application/json")
                .body(JiraJsonPayload.getLoginJson()).filter(sessionFilter)
                .when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(loginResponse);
       String sessionID =  js.get("session.value");
        System.out.println(sessionID);

//
        //Adding a new comment
        String issueID = "10103";
        given().pathParam("key","10103").log().all().header("Content-Type","application/json")
                .body(JiraJsonPayload.getCommentBody("Commenting for IntelliJ - Rest API")).filter(sessionFilter )
                .when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
                .statusCode(201).extract().response().asString();




    }
}


