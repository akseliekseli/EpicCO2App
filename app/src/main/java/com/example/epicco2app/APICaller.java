package com.example.epicco2app;

import java.util.ArrayList;

public class APICaller {

    private static APICaller caller = new APICaller();
    ReturnData returnData = null;

    private APICaller(){
    }
    public static APICaller getInstance(){
        return caller;

    }




    private static class ReturnData {

    }

}
