package com.mateus.habitflow.serialization;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();  
        jsonObject.addProperty("date", src.toString());  
        return jsonObject;  
    }
}
