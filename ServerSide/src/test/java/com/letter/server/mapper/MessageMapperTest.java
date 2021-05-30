package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.entity.Status;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.DetailedMessage;
import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.OnlineStatusDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageMapperTest {

    @Autowired
    private MessageMapper messageMapper;

    private static MessageEntity messageEntity;

    private static DetailedMessage detailedMessage;


    @BeforeAll
    private static void beforeAllTests() {
        UserEntity senderEntity = UserEntity.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        DetailedUser senderDto = DetailedUser.detailedUserBuilder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        OnlineStatusEntity senderOnlineStatusEntity = OnlineStatusEntity.builder()
                .id(1L)
                .user(senderEntity)
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        OnlineStatusDto senderOnlineStatusDto = OnlineStatusDto.builder()
                .id(1L)
                .userId(senderDto.getId())
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        senderEntity.setOnlineStatus(senderOnlineStatusEntity);
        senderDto.setOnlineStatus(senderOnlineStatusDto);

        UserEntity recipientEntity = UserEntity.builder()
                .id(2L)
                .login("login2")
                .email("log2@email.com")
                .password("pass")
                .build();

        DetailedUser recipientDto = DetailedUser.detailedUserBuilder()
                .id(2L)
                .login("login2")
                .email("log2@email.com")
                .password("pass")
                .build();

        OnlineStatusEntity recipientOnlineStatusEntity = OnlineStatusEntity.builder()
                .id(2L)
                .user(recipientEntity)
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        OnlineStatusDto recipientOnlineStatusDto = OnlineStatusDto.builder()
                .id(2L)
                .userId(recipientDto.getId())
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        recipientEntity.setOnlineStatus(recipientOnlineStatusEntity);
        recipientDto.setOnlineStatus(recipientOnlineStatusDto);

        messageEntity = MessageEntity.builder()
                .id(1L)
                .messageText("Hello!")
                .createTime(OffsetDateTime.now())
                .modifyTime(null)
                .status(Status.SENT)
                .sender(senderEntity)
                .recipient(recipientEntity)
                .isRead(false)
                .build();

        detailedMessage = DetailedMessage.builder()
                .id(1L)
                .messageText("Hello!")
                .createTime(OffsetDateTime.now())
                .modifyTime(null)
                .status(Status.SENT.getValue())
                .sender(senderDto)
                .recipient(recipientDto)
                .isRead(false)
                .build();
    }

    @Test
    public void messageEntityToDto_ShouldMapCorrectly() {
        MessageEntity actual = messageMapper.detailedMessageToMessageEntity(detailedMessage);

        assertEquals(detailedMessage.getId(), actual.getId());
        assertEquals(detailedMessage.getMessageText(), actual.getMessageText());
        assertEquals(detailedMessage.getCreateTime(), actual.getCreateTime());
        assertEquals(detailedMessage.getModifyTime(), actual.getModifyTime());
        assertEquals(detailedMessage.getStatus(), actual.getStatus().getValue());
        assertEquals(detailedMessage.getSender().getId(), actual.getSender().getId());
        assertEquals(detailedMessage.getSender().getLogin(), actual.getSender().getLogin());
        assertEquals(detailedMessage.getSender().getEmail(), actual.getSender().getEmail());
        assertEquals(detailedMessage.getSender().getPassword(), actual.getSender().getPassword());
        assertEquals(detailedMessage.getSender().getOnlineStatus().getId(), actual.getSender().getOnlineStatus().getId());
        assertEquals(detailedMessage.getSender().getOnlineStatus().getIsOnline(), actual.getSender().getOnlineStatus().getIsOnline());
        assertEquals(detailedMessage.getSender().getOnlineStatus().getUserId(), actual.getSender().getOnlineStatus().getUser().getId());
        assertEquals(detailedMessage.getSender().getOnlineStatus().getLastOnlineTime(), actual.getSender().getOnlineStatus().getLastOnlineTime());
        assertEquals(detailedMessage.getRecipient().getId(), actual.getRecipient().getId());
        assertEquals(detailedMessage.getRecipient().getLogin(), actual.getRecipient().getLogin());
        assertEquals(detailedMessage.getRecipient().getEmail(), actual.getRecipient().getEmail());
        assertEquals(detailedMessage.getRecipient().getPassword(), actual.getRecipient().getPassword());
        assertEquals(detailedMessage.getRecipient().getOnlineStatus().getId(), actual.getRecipient().getOnlineStatus().getId());
        assertEquals(detailedMessage.getRecipient().getOnlineStatus().getIsOnline(), actual.getRecipient().getOnlineStatus().getIsOnline());
        assertEquals(detailedMessage.getRecipient().getOnlineStatus().getUserId(), actual.getRecipient().getOnlineStatus().getUser().getId());
        assertEquals(detailedMessage.getRecipient().getOnlineStatus().getLastOnlineTime(), actual.getRecipient().getOnlineStatus().getLastOnlineTime());
    }

    @Test
    public void messageDtoToEntity_ShouldMapCorrectly() {
        DetailedMessage actual = messageMapper.messageEntityToDetailedMessage(messageEntity);

        assertEquals(messageEntity.getId(), actual.getId());
        assertEquals(messageEntity.getMessageText(), actual.getMessageText());
        assertEquals(messageEntity.getCreateTime(), actual.getCreateTime());
        assertEquals(messageEntity.getModifyTime(), actual.getModifyTime());
        assertEquals(messageEntity.getStatus().getValue(), actual.getStatus());
        assertEquals(messageEntity.getSender().getId(), actual.getSender().getId());
        assertEquals(messageEntity.getSender().getLogin(), actual.getSender().getLogin());
        assertEquals(messageEntity.getSender().getEmail(), actual.getSender().getEmail());
        assertEquals(messageEntity.getSender().getPassword(), actual.getSender().getPassword());
        assertEquals(messageEntity.getSender().getOnlineStatus().getId(), actual.getSender().getOnlineStatus().getId());
        assertEquals(messageEntity.getSender().getOnlineStatus().getIsOnline(), actual.getSender().getOnlineStatus().getIsOnline());
        assertEquals(messageEntity.getSender().getOnlineStatus().getUser().getId(), actual.getSender().getOnlineStatus().getUserId());
        assertEquals(messageEntity.getSender().getOnlineStatus().getLastOnlineTime(), actual.getSender().getOnlineStatus().getLastOnlineTime());
        assertEquals(messageEntity.getRecipient().getId(), actual.getRecipient().getId());
        assertEquals(messageEntity.getRecipient().getLogin(), actual.getRecipient().getLogin());
        assertEquals(messageEntity.getRecipient().getEmail(), actual.getRecipient().getEmail());
        assertEquals(messageEntity.getRecipient().getPassword(), actual.getRecipient().getPassword());
        assertEquals(messageEntity.getRecipient().getOnlineStatus().getId(), actual.getRecipient().getOnlineStatus().getId());
        assertEquals(messageEntity.getRecipient().getOnlineStatus().getIsOnline(), actual.getRecipient().getOnlineStatus().getIsOnline());
        assertEquals(messageEntity.getRecipient().getOnlineStatus().getUser().getId(), actual.getRecipient().getOnlineStatus().getUserId());
        assertEquals(messageEntity.getRecipient().getOnlineStatus().getLastOnlineTime(), actual.getRecipient().getOnlineStatus().getLastOnlineTime());
    }
}
