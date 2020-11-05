package com.sustech.gamercenter.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static com.sustech.gamercenter.security.UserPermission.*;

public enum UserRole {
    Admin(newHashSet(
            GAME_READ, GAME_WRITE,
            PLAYER_READ, PLAYER_WRITE,
            DEVELOPER_READ, DEVELOPER_WRITE,
            ADMIN_READ, ADMIN_WRITE
    )),

    Tester(newHashSet(
            GAME_READ, GAME_WRITE,
            PLAYER_READ, PLAYER_WRITE,
            DEVELOPER_READ, DEVELOPER_WRITE
    )),

    GUEST(newHashSet(GAME_READ, PLAYER_READ)),

    Player(newHashSet(GAME_READ, PLAYER_READ, PLAYER_WRITE)),

    Developer(newHashSet(
            GAME_READ, GAME_WRITE,
            PLAYER_READ,
            DEVELOPER_READ, DEVELOPER_WRITE
    ));


    private Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
