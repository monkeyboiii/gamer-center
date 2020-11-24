package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByUserIdAndUnread(Long id, Boolean unread);

    List<Message> findAllByUnreadIsFalse();

}
