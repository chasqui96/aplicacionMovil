package com.example.clinica.Data.Api;

public class Api {

    //private static final String BASE_URL = "http://fundacionnuevafiladelphia.org/ApiAndroid/public/api/";
    private static final String BASE_URL = "http://app-webservice-clinica.herokuapp.com/api/";

    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiRoutes.class);
    }
}
