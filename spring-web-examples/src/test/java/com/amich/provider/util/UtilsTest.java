package com.amich.provider.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class UtilsTest {

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserAndPwd(String userName, String pwd) {
        return "Basic " + Base64.getEncoder()
                .encodeToString( (userName + ":" + pwd) .getBytes());
    }
}
