package com.RestAssuredPractice.Data;

public class JiraJsonPayload {

    public static String getCommentBody(String comment){

        return "{\n" +
                "    \"body\": \""+ comment + ".\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }

    public static String getLoginJson(){
        return "{ \"username\": \"rajeshg\", \n" +
                "\"password\": \"rajesh241327\" }";
    }
}
