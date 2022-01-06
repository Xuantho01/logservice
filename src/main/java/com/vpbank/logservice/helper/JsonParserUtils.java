package com.vpbank.logservice.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class JsonParserUtils {

    private static ObjectMapper objectMapper;

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonParserUtils.objectMapper = objectMapper;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParserUtils.class);


    public static String objectToString(Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return new String(objectMapper.writeValueAsBytes(object), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("objectToStringJson", e);
            return "";
        }
    }

    public static <T> T jSonToObject(Class<T> object, String value) {
        if (value == null) {
            return null;
        }
        T install = null;
        try {
            install = objectMapper.readValue(value, object);
        } catch (JsonProcessingException e) {
            LOGGER.error("jSonToObject ", e);
        }
        return install;
    }

}