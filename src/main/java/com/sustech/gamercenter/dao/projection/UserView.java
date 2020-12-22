package com.sustech.gamercenter.dao.projection;

/**
 * usage at guest peak
 */
public interface UserView {

    Double getId();

    String getName();

    String getEmail();

    String getBio();

    String getAvatar();

    Boolean getOnline();

}
