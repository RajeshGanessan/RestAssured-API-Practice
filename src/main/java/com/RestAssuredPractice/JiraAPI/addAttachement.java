package com.RestAssuredPractice.JiraAPI;

import com.RestAssuredPractice.Data.JiraJsonPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;

public class addAttachement {

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
        System.out.println("<<<<----Adding Comment---->>>>");
        String issueID = "10103";
        String addCommentResponse =      given().pathParam("key","10103").log().all().header("Content-Type","application/json")
                .body(JiraJsonPayload.getCommentBody("Final attachment for IntelliJ - Rest API")).filter(sessionFilter )
                .when().post("rest/api/2/issue/{key}/comment").then().log().all().assertThat()
                .statusCode(201).extract().response().asString();
        JsonPath jsc  = new JsonPath(addCommentResponse);
        String commentid = jsc.getString("id");

        //Adding Attachment in the issue
        System.out.println("<<<<----Adding Attachment---->>>>");
      given().header("X-Atlassian-Token","no-check").filter(sessionFilter)
                .pathParam("key","10103")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("/Users/rajeshlv/Rajesh Files /My repos/My gitHub Repo/RestAssured-API/Files/jira.txt"))
                .when().post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200).extract().response().asString();


        //getting the issue
        System.out.println("<<<-Getting the issue--->");
        String getIssue= given().filter(sessionFilter).pathParam("key","10103")
                .queryParam("fields","comment").when()
                .get("/rest/api/2/issue/{key}").then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = new JsonPath(getIssue);
       int commentSize =  jsonPath.getInt("fields.comment.comments.size()");
        for(int i=0;i<commentSize;i++){

           String commentID =  jsonPath.get("fields.comment.comments["+ i +"].id");
            if(commentID.equals(commentid)){
                System.out.println("Comment ID ====> " +commentID);
               String body =  jsonPath.get("fields.comment.comments["+i+"].body");
                System.out.println(body);
            }
        }
    }

}
