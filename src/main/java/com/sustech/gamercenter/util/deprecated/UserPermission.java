package com.sustech.gamercenter.util.deprecated;

public enum UserPermission {

    PLAYER_READ("player:read"),
    PLAYER_WRITE("player:write"),

    DEVELOPER_READ("developer:read"),
    DEVELOPER_WRITE("developer:write"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),

    GAME_READ("game:read"),
    GAME_WRITE("game:write"),

    // and public resource

    ;

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
