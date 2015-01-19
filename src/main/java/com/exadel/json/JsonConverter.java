package com.exadel.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Sergey Koval
 */
public class JsonConverter {
    private Gson gson;

    public JsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <C> C fromJson(String json, Class<C> classType) {
        return gson.fromJson(json, classType);
    }
}