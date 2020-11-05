package com.sustech.gamercenter.model;

import java.sql.Timestamp;
import java.util.List;

public class Admin extends User{
    List<String> tasks;

    public Admin(Long id, String name, String email, String password, String role, boolean is_online, boolean is_locked, Timestamp created_at) {
        super(id, name, email, password, role, is_online, is_locked, created_at);
    }
}
