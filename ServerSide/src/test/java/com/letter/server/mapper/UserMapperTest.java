package com.letter.server.mapper;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private static UserEntity userEntity;

    private static UserDto userDto;

    @BeforeAll
    public static void beforeAllTests() {
        userEntity = UserEntity.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        OnlineStatusEntity onlineStatusEntity = OnlineStatusEntity.builder()
                .id(1L)
                .user(userEntity)
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

        OnlineStatusDto onlineStatusDto = OnlineStatusDto.builder()
                .id(1L)
                .userId(userDto.getId())
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

         userEntity.setOnlineStatus(onlineStatusEntity);
         userDto.setOnlineStatus(onlineStatusDto);
    }

    @Test
    public void userEntityToDto_ShouldMapCorrectly() {
        UserDto actual = userMapper.userEntityToDto(userEntity);

        assertEquals(userEntity.getId(), actual.getId());
        assertEquals(userEntity.getLogin(), actual.getLogin());
        assertEquals(userEntity.getEmail(), actual.getEmail());
        assertEquals(userEntity.getPassword(), actual.getPassword());
        assertEquals(userEntity.getOnlineStatus().getId(), actual.getOnlineStatus().getId());
        assertEquals(userEntity.getOnlineStatus().getUser().getId(), actual.getOnlineStatus().getUserId());
        assertEquals(userEntity.getOnlineStatus().getIsOnline(), actual.getOnlineStatus().getIsOnline());
        assertEquals(userEntity.getOnlineStatus().getLastOnlineTime(), actual.getOnlineStatus().getLastOnlineTime());
    }

    @Test
    public void userDtoToEntity_ShouldMapCorrectly() {
        UserEntity actual = userMapper.userDtoToEntity(userDto);

        assertEquals(userDto.getId(), actual.getId());
        assertEquals(userDto.getLogin(), actual.getLogin());
        assertEquals(userDto.getEmail(), actual.getEmail());
        assertEquals(userDto.getPassword(), actual.getPassword());
        assertEquals(userDto.getOnlineStatus().getId(), actual.getOnlineStatus().getId());
        assertEquals(userDto.getOnlineStatus().getId(), actual.getOnlineStatus().getUser().getId());
        assertEquals(userDto.getOnlineStatus().getIsOnline(), actual.getOnlineStatus().getIsOnline());
        assertEquals(userDto.getOnlineStatus().getLastOnlineTime(), actual.getOnlineStatus().getLastOnlineTime());
    }
}
