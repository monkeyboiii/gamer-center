package com.sustech.gamercenter.dao.projection;

public interface CommentStat {
    Long getComment_id();

    String getContent();

    Long getReportee_id();

    Long getGame_id();

    Integer getTotal();
}
