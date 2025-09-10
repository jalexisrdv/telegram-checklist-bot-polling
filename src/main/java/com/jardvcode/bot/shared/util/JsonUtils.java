package com.jardvcode.bot.shared.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String encode(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T decode(String value, Class<T> toClass) throws JsonProcessingException {
        return mapper.readValue(value, toClass);
    }

}
