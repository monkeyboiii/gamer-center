package com.sustech.gamercenter.util.deprecated;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StringToLongDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getCodec().readValue(p, String.class);
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
