package com.letter.server.dao.repository;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> findAllBySenderAndRecipient(UserEntity sender, UserEntity recipient);

    List<MessageEntity> findAllBySenderInAndRecipientIn(UserEntity sender, UserEntity recipient);
}
