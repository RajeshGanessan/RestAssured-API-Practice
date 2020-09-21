package com.RestAssuredPractice.LibraryAPI;


import com.RestAssuredPractice.Data.PayloadJson;
import com.RestAssuredPractice.Utils.HelperMethods;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;
import java.util.Random;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    Faker faker;


    @Test(dataProvider="BookData")
    public void addBook(String isbn,String Id){

        RestAssured.baseURI="https://rahulshettyacademy.com";
        faker = new Faker();
//        String isbn = faker.name().firstName();
//        String Id = faker.number().digits(3);
       String addBookResponse =  given().header("Content-Type","application/json")
                .body(PayloadJson.getAddBook(isbn,Id))
                .when().log().all().post("/Library/Addbook.php").then().assertThat()
                .statusCode(200).extract().response().asString();
        System.out.println(addBookResponse);

        JsonPath js = HelperMethods.rawToJson(addBookResponse);
        var bookID = js.get("ID");
        System.out.println(bookID);

    }
//
//    @Test(priority = 2,description = "deleting the AddedBooks")
//    public void deleteBook(){
//        String deleteBookResponse = given().header("Content-Type","application/json")
//                .body()
//    }
    @DataProvider(name = "BookData")
    public Object[][] getBookData(){
        return new Object[][] {{"Ram kishore","345"},{"sabari","456"},{"prithvi","980"}};
    }

}
