package com.tripandship.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tripandship.patterns.singleton.DatabaseManager;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws IOException {
        return mapper.readValue(json, valueType);
    }

    public static String toJson(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}