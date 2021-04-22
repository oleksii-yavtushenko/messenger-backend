package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.entity.Status;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.MessageDto;
import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.dto.UserDto;
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

    private static MessageDto messageDto;


    @BeforeAll
    private static void beforeAllTests() {
        UserEntity senderEntity = UserEntity.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        UserDto senderDto = UserDto.builder()
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

        UserDto recipientDto = UserDto.builder()
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

        messageDto = MessageDto.builder()
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
        MessageEntity actual = messageMapper.messageDtoToEntity(messageDto);

        assertEquals(messageDto.getId(), actual.getId());
        assertEquals(messageDto.getMessageText(), actual.getMessageText());
        assertEquals(messageDto.getCreateTime(), actual.getCreateTime());
        assertEquals(messageDto.getModifyTime(), actual.getModifyTime());
        assertEquals(messageDto.getStatus(), actual.getStatus().getValue());
        assertEquals(messageDto.getSender().getId(), actual.getSender().getId());
        assertEquals(messageDto.getSender().getLogin(), actual.getSender().getLogin());
        assertEquals(messageDto.getSender().getEmail(), actual.getSender().getEmail());
        assertEquals(messageDto.getSender().getPassword(), actual.getSender().getPassword());
        assertEquals(messageDto.getSender().getOnlineStatus().getId(), actual.getSender().getOnlineStatus().getId());
        assertEquals(messageDto.getSender().getOnlineStatus().getIsOnline(), actual.getSender().getOnlineStatus().getIsOnline());
        assertEquals(messageDto.getSender().getOnlineStatus().getUserId(), actual.getSender().getOnlineStatus().getUser().getId());
        assertEquals(messageDto.getSender().getOnlineStatus().getLastOnlineTime(), actual.getSender().getOnlineStatus().getLastOnlineTime());
        assertEquals(messageDto.getRecipient().getId(), actual.getRecipient().getId());
        assertEquals(messageDto.getRecipient().getLogin(), actual.getRecipient().getLogin());
        assertEquals(messageDto.getRecipient().getEmail(), actual.getRecipient().getEmail());
        assertEquals(messageDto.getRecipient().getPassword(), actual.getRecipient().getPassword());
        assertEquals(messageDto.getRecipient().getOnlineStatus().getId(), actual.getRecipient().getOnlineStatus().getId());
        assertEquals(messageDto.getRecipient().getOnlineStatus().getIsOnline(), actual.getRecipient().getOnlineStatus().getIsOnline());
        assertEquals(messageDto.getRecipient().getOnlineStatus().getUserId(), actual.getRecipient().getOnlineStatus().getUser().getId());
        assertEquals(messageDto.getRecipient().getOnlineStatus().getLastOnlineTime(), actual.getRecipient().getOnlineStatus().getLastOnlineTime());
    }

    @Test
    public void messageDtoToEntity_ShouldMapCorrectly() {
        MessageDto actual = messageMapper.messageEntityToDto(messageEntity);

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
