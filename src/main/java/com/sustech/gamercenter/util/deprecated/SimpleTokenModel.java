package com.sustech.gamercenter.util.deprecated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;

import java.util.List;

public class SimpleTokenModel {


    private Long user_id;

    private String token;

    @JsonDeserialize(using = CollectionDeserializer.class)
    private List<String> books;

    public SimpleTokenModel(Long user_id, String token, List<String> books) {
        this.user_id = user_id;
        this.token = token;
        this.books = books;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }
}
