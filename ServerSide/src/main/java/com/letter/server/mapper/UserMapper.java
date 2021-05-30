package com.letter.server.mapper;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OnlineStatusMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userDtoDetailedToEntity(DetailedUser detailedUser);

    DetailedUser userEntityToDtoDetailed(UserEntity userEntity);

    UserEntity userDtoToEntity(User user);

    User userEntityToDto(UserEntity userEntity);

    default UserEntity editUserDtoToEntity(UserEntity userEntity, DetailedUser detailedUser) {
        UserEntity mappedEntity = new UserEntity();

        mappedEntity.setId(userEntity.getId());
        mappedEntity.setLogin(detailedUser.getLogin() != null ? detailedUser.getLogin() : userEntity.getLogin());
        mappedEntity.setPassword(detailedUser.getPassword() != null ? detailedUser.getPassword() : userEntity.getPassword());
        mappedEntity.setEmail(detailedUser.getEmail() != null ? detailedUser.getEmail() : userEntity.getEmail());
        mappedEntity.setEnabled(detailedUser.isEnabled());
        mappedEntity.setOnlineStatus(detailedUser.getOnlineStatus() != null ?
                OnlineStatusMapper.INSTANCE.onlineStatusDtoToEntity(detailedUser.getOnlineStatus()) : userEntity.getOnlineStatus());

        return mappedEntity;
    }
}
