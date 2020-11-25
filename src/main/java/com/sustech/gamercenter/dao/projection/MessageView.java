package com.sustech.gamercenter.dao.projection;

import java.sql.Timestamp;

public interface MessageView {
    Long getId();

    String getName();

    String getSource();

    String getType();

    String getMessage();

    Boolean getUnread();

    Timestamp getCreated_at();
}
