package com.letter.server.dao.repository;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> findAllBySenderAndRecipient(UserEntity sender, UserEntity recipient);

    List<MessageEntity> findAllByRecipientAndSender(UserEntity recipient, UserEntity sender);

    @Query( "SELECT message FROM MessageEntity message " +
            "WHERE (message.sender= ?1 AND message.recipient = ?2) OR (message.sender = ?2 AND message.recipient = ?1) " +
            "ORDER BY message.createTime")
    List<MessageEntity> findMessagesFromUsers(UserEntity firstUser, UserEntity secondUser);
}
