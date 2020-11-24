package com.sustech.gamercenter.util.deprecated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sustech.gamercenter.security.UserRole;

import java.util.*;


/**
 * items to store in memory cache
 * with redis implementation
 * at {@link TokenService}
 */
public class TokenModel {

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long user_id;

    private String token;

    //    @JsonIgnore
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private List roles;
    //    @JsonIgnore
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private List permissions;


    public TokenModel(Long user_id, String token, String roles) {
        this.user_id = user_id;
        this.token = token;
        Set<String> roleList = new HashSet<>();
        Set<String> permissionList = new HashSet<>();

        char[] array = roles.toLowerCase().toCharArray();
        for (char c : array) {
            switch (c) {
                case 'a': {
                    roleList.add(UserRole.Admin.name());
                    permissionList.addAll(UserRole.Admin.getStringPermissions());
                    break;
                }
                case 'd': {
                    roleList.add(UserRole.Developer.name());
                    permissionList.addAll(UserRole.Developer.getStringPermissions());
                    break;
                }
                case 'g': {
                    roleList.add(UserRole.Guest.name());
                    permissionList.addAll(UserRole.Guest.getStringPermissions());
                    break;
                }
                case 'p': {
                    roleList.add(UserRole.Player.name());
                    permissionList.addAll(UserRole.Player.getStringPermissions());
                    break;
                }
                case 't': {
                    roleList.add(UserRole.Tester.name());
                    permissionList.addAll(UserRole.Tester.getStringPermissions());
                    break;
                }
            }
        }

        this.roles = new ArrayList<>(roleList);
        this.permissions = new ArrayList<>(permissionList);
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

    public List getRoles() {
        return roles;
    }

    public List getPermissions() {
        return permissions;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }
}
