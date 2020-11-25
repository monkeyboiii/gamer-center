package com.sustech.gamercenter.dao.projection;

import java.sql.Timestamp;

public interface MessageView {
    String getName();

    String getMessage();

    Boolean getUnread();

    Timestamp getCreatedAt();
}
