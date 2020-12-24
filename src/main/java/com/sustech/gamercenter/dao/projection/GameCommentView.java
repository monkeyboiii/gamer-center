package com.sustech.gamercenter.dao.projection;

import java.sql.Timestamp;

public interface GameCommentView {
    Long getId();

    Long getUser_id();

    String getUser_name();

    String getAvatar();

    Long getGame_id();

    String getContent();

    Double getGrade();

    Timestamp getCreatedAt();


}
