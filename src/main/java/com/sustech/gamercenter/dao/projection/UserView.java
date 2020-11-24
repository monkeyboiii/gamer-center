package com.sustech.gamercenter.dao.projection;

import java.util.List;

/**
 * usage at
 * full user info
 * with friends and games
 */
public interface UserView {

    Double getId();

    String getName();

    String getAvatar();

    Boolean getOnline();

    List<FriendView> getUser();


}
