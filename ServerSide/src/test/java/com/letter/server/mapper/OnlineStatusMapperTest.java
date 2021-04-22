package com.letter.server.mapper;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.OnlineStatusDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

@SpringBootTest
public class OnlineStatusMapperTest {

    @Autowired
    private OnlineStatusMapper onlineStatusMapper;

    private static OnlineStatusEntity onlineStatusEntity;

    private static OnlineStatusDto onlineStatusDto;

    @BeforeAll
    public static void beforeAllTests() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        onlineStatusDto = OnlineStatusDto.builder()
                .id(1L)
                .userId(userEntity.getId())
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        onlineStatusEntity = OnlineStatusEntity.builder()
                .id(1L)
                .user(userEntity)
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();
    }

    @Test
    public void onlineStatusEntityToDto_ShouldMapCorrectly() {
        OnlineStatusDto actual = onlineStatusMapper.onlineStatusEntityToDto(onlineStatusEntity);

        Assertions.assertEquals(onlineStatusEntity.getId(), actual.getId());
        Assertions.assertEquals(onlineStatusEntity.getUser().getId(), actual.getId());
        Assertions.assertEquals(onlineStatusEntity.getIsOnline(), actual.getIsOnline());
        Assertions.assertEquals(onlineStatusEntity.getLastOnlineTime(), actual.getLastOnlineTime());
    }

    @Test
    public void onlineStatusDtoToEntity_ShouldMapCorrectly() {
        OnlineStatusEntity actual = onlineStatusMapper.onlineStatusDtoToEntity(onlineStatusDto);

        Assertions.assertEquals(onlineStatusDto.getId(), actual.getId());
        Assertions.assertEquals(onlineStatusDto.getUserId(), actual.getUser().getId());
        Assertions.assertEquals(onlineStatusDto.getIsOnline(), actual.getIsOnline());
        Assertions.assertEquals(onlineStatusDto.getLastOnlineTime(), actual.getLastOnlineTime());
    }
}
