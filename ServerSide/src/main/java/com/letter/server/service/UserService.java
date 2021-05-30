package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.dto.User;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final Validator<DetailedUser> validator;

    private final OnlineStatusService onlineStatusService;

    public List<DetailedUser> findAll() {
        List<UserEntity> userEntities = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());

        return userEntities.stream().map(userMapper::userEntityToDtoDetailed).collect(Collectors.toList());
    }

    public DetailedUser findByIdDetailed(Long id) throws ServiceException {

        validator.validateId(DetailedUser.detailedUserBuilder().id(id).build());

        UserEntity userEntity;

        try {
            userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding userEntity with id=" + id, ex);
        }

        return userMapper.userEntityToDtoDetailed(userEntity);
    }

    public User findById(Long id) throws ServiceException {
        validator.validateId(DetailedUser.detailedUserBuilder().id(id).build());

        UserEntity userEntity;

        try {
            userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding userEntity with id=" + id, ex);
        }

        return userMapper.userEntityToDto(userEntity);
    }
    public DetailedUser save(DetailedUser detailedUser) throws ServiceException {

        validator.validate(detailedUser);

        DetailedUser response;
        boolean autoOnlineStatus = detailedUser.getOnlineStatus() == null;

        try {
            UserEntity userEntity = userMapper.userDtoDetailedToEntity(detailedUser);
            OnlineStatusDto userOnlineStatus = detailedUser.getOnlineStatus();

            if (autoOnlineStatus) {
                userOnlineStatus = OnlineStatusDto.builder()
                        .isOnline(false)
                        .lastOnlineTime(OffsetDateTime.now())
                        .build();
            }

            userEntity.setOnlineStatus(null);

            response = userMapper.userEntityToDtoDetailed(userRepository.save(userEntity));

            userOnlineStatus.setUserId(response.getId());
            response.setOnlineStatus(onlineStatusService.save(userOnlineStatus));

        } catch (Exception ex) {
            throw new ServiceException("Exception while mapping and saving userEntity with login=" + detailedUser.getLogin(), ex);
        }

        return response;
    }

    public DetailedUser edit(DetailedUser detailedUser) throws ServiceException {

        validator.validateId(detailedUser);

        DetailedUser responseDto;

        try {
            UserEntity userEntity = userRepository.findById(detailedUser.getId()).orElseThrow(EntityNotFoundException::new);

            UserEntity responseEntity = userMapper.editUserDtoToEntity(userEntity, detailedUser);
            responseEntity = userRepository.save(responseEntity);

            responseDto = userMapper.userEntityToDtoDetailed(responseEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while mapping and editing userEntity with id=" + detailedUser.getId(), ex);
        }

        return responseDto;
    }

    public void delete(DetailedUser detailedUser) throws ServiceException {

        validator.validateId(detailedUser);

        try {
            UserEntity userEntity = userRepository.findById(detailedUser.getId()).orElseThrow(EntityNotFoundException::new);

            userRepository.delete(userEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while deleting userEntity with id=" + detailedUser.getId(), ex);
        }
    }


}
