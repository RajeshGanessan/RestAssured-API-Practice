package com.RestAssuredPractice;

import com.RestAssuredPractice.Data.PayloadJson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.json.Json;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class restBasics {

    public static void main(String[] args) {
        JsonPath json;

        RestAssured.baseURI="https://rahulshettyacademy.com/";

      String AddPlaceResponse =  given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(PayloadJson.getAddPlace())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

        System.out.println(AddPlaceResponse);

         json = new JsonPath(AddPlaceResponse);  // for parsing String as a json
         String place_id = json.get("place_id");


         //Update Place API
       String UpdateResponse =  given().queryParam("key","qaclick123").header("Content-Type","w")
                .body("{\n" +
                        "\"place_id\":\""+ place_id+"\",\n" +
                        "\"address\":\"15th cross richmond road, Bangalore\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n" +
                        " \n")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().statusCode(200).extract().response().asString();
            JsonPath jsonUpdate = new JsonPath(UpdateResponse);
            String UpdateSucess = jsonUpdate.get("msg");
        System.out.println(UpdateSucess);


        //Get the Updated Place
       String getPlace =  given().queryParam("key","qaclick123").queryParam("place_id",place_id)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println("Get Place = " + getPlace);



    }
}
