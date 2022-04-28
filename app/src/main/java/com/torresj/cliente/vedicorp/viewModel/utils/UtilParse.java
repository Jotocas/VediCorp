package com.torresj.cliente.vedicorp.viewModel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UtilParse {
    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
