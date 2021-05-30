package com.letter.server.dao.repository;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnlineStatusRepository onlineStatusRepository;

    @Test
    public void findMessagesFromUsers_ShouldFindSomeUsersAndThen_ShouldFindTheirMessageHistory() {
        UserEntity firstUser = userRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        UserEntity secondUser = userRepository.findById(2L).orElseThrow(IllegalArgumentException::new);

        List<MessageEntity> messages = messageRepository.findAllByUsers(firstUser, secondUser);

        Assertions.assertNotNull(messages);
        Assertions.assertNotEquals(messages.size(), 0);
    }
}
