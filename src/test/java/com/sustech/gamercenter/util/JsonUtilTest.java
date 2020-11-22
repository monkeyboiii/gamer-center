package com.sustech.gamercenter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.model.TokenModel;
import com.sustech.gamercenter.util.model.SimpleTokenModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonUtilTest {

    private User user;
    private TokenModel tokenModel;
    private SimpleTokenModel simpleTokenModel;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @DisplayName("before class test")
    void setUp() {
        this.user = new User("name", "email", "password", "adp");
        this.user.setId(Long.MAX_VALUE);
        this.tokenModel = new TokenModel(user.getId(), UUID.randomUUID().toString(), user.getRole());

        List<String> books = new ArrayList<>();
        books.add("A thousand kisses deep");
        books.add("Shakespeare's huge bio");
        books.add("No silver bullet");
        this.simpleTokenModel = new SimpleTokenModel(user.getId(), UUID.randomUUID().toString(), books);
    }

    @Test
    public void UserGenerate() {
        System.out.println(JsonUtil.toJsonString(tokenModel));
    }

    @Test
    @DisplayName("deserialization test")
    public void JsonDeserializationTest() {

        String normal = JsonUtil.toJsonString(this.tokenModel);
        String pretty = JsonUtil.toJsonWithViewPrettyString(this.tokenModel, TokenModel.class);

//        System.out.println(normal);
        System.out.println(pretty);
        System.out.println();

        try {
            TokenModel tm = JsonUtil.fromJsonString(normal, TokenModel.class);
            System.out.println(tm.getRoles());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TokenModelSimpleTest() throws JsonProcessingException {
        String normal = objectMapper.writer().writeValueAsString(this.simpleTokenModel);

        System.out.println(normal);

        try {
            SimpleTokenModel tm = objectMapper.reader().readValue(normal, SimpleTokenModel.class);
            System.out.println(tm.getUser_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
