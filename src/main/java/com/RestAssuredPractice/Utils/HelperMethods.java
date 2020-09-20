package com.RestAssuredPractice.Utils;

import io.restassured.path.json.JsonPath;

public class HelperMethods {

    public static JsonPath rawToJson(String response){
        JsonPath js = new JsonPath(response);
        return js;
    }
}
