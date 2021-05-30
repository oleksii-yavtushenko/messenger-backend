package com.letter.server.mapper;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.OnlineStatusDto;
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

    private static DetailedUser detailedUser;

    @BeforeAll
    public static void beforeAllTests() {
        userEntity = UserEntity.builder()
                .id(1L)
                .login("login")
                .email("log@email.com")
                .password("pass")
                .build();

        detailedUser = DetailedUser.detailedUserBuilder()
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
                .userId(detailedUser.getId())
                .isOnline(false)
                .lastOnlineTime(OffsetDateTime.now())
                .build();

         userEntity.setOnlineStatus(onlineStatusEntity);
         detailedUser.setOnlineStatus(onlineStatusDto);
    }

    @Test
    public void userEntityToDto_ShouldMapCorrectly() {
        DetailedUser actual = userMapper.userEntityToDtoDetailed(userEntity);

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
        UserEntity actual = userMapper.userDtoDetailedToEntity(detailedUser);

        assertEquals(detailedUser.getId(), actual.getId());
        assertEquals(detailedUser.getLogin(), actual.getLogin());
        assertEquals(detailedUser.getEmail(), actual.getEmail());
        assertEquals(detailedUser.getPassword(), actual.getPassword());
        assertEquals(detailedUser.getOnlineStatus().getId(), actual.getOnlineStatus().getId());
        assertEquals(detailedUser.getOnlineStatus().getId(), actual.getOnlineStatus().getUser().getId());
        assertEquals(detailedUser.getOnlineStatus().getIsOnline(), actual.getOnlineStatus().getIsOnline());
        assertEquals(detailedUser.getOnlineStatus().getLastOnlineTime(), actual.getOnlineStatus().getLastOnlineTime());
    }
}
