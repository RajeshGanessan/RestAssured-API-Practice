package com.RestAssuredPractice;

import com.RestAssuredPractice.Data.PayloadJson;
import io.restassured.path.json.JsonPath;

public class NestedJsonhandle {
    //1. Print No of courses returned by API
    //2.Print Purchase Amount
    //3. Print Title of the first course
    //4. Print All course titles and their respective Prices
    //5. Print no of copies sold by RPA Course
    //6. Verify if Sum of all Course prices matches with Purchase Amount
    public static void main(String[] args) {

        JsonPath js = new JsonPath(PayloadJson.getCoursePrices());
        String course = "RestAssured";
       var courses =  js.getInt("courses.size()");
        System.out.println(courses);
        for(int i=0;i<courses;i++){
            var courseTitle = js.get("courses["+i+"].title");
            if(courseTitle.equals(course)){
                var price = js.get("courses["+i+"].price");
                var copy = js.get("courses["+i+"].copies");
                System.out.println("Total price of" + course + " is  =  " + Integer.parseInt(price.toString()) * Integer.parseInt(copy.toString()));
                break;
            }

        }

    }
}
