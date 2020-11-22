package com.sustech.gamercenter.util;

import com.sustech.gamercenter.util.model.JsonResponse;
import org.junit.jupiter.api.Test;

public class JsonResponseTest {

    @Test
    public void JsonBuilderTest() {
        JsonResponse js = new JsonResponse
                .builder()
                .code(4)
                .msg("abundant")
                .build();

        System.out.println(js);
    }
}