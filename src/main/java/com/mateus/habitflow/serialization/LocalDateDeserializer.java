package com.mateus.habitflow.serialization;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null && json.isJsonObject()) {
            JsonElement dateElement = json.getAsJsonObject().get("date");  // Get the "date" property

            if (dateElement != null) {
                return LocalDate.parse(dateElement.getAsString());  // Parse the string to LocalDate
            }
        }
        throw new JsonParseException("Invalid or missing 'date' property in JSON.");
    }
}