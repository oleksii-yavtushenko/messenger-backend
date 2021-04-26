package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.UserDto;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final Validator<UserDto> validator;

    public List<UserDto> findAll() {
        List<UserEntity> userEntities = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());

        return userEntities.stream().map(userMapper::userEntityToDto).collect(Collectors.toList());
    }

    public UserDto findById(UserDto userDto) throws ServiceException {

        validator.validateId(userDto);

        UserEntity userEntity;

        try {
            userEntity = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding userEntity with id=" + userDto.getId(), ex);
        }

        return userMapper.userEntityToDto(userEntity);
    }

    public UserDto save(UserDto userDto) throws ServiceException {

        validator.validate(userDto);

        UserDto response;

        try {
            UserEntity userEntity = userMapper.userDtoToEntity(userDto);

            response = userMapper.userEntityToDto(userRepository.save(userEntity));
        } catch (Exception ex) {
            throw new ServiceException("Exception while mapping and saving userEntity with login=" + userDto.getLogin(), ex);
        }

        return response;
    }

    public UserDto edit(UserDto userDto) throws ServiceException {

        validator.validateId(userDto);

        UserDto responseDto;

        try {
            UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);

            UserEntity responseEntity = userMapper.editUserDtoToEntity(userEntity, userDto);
            responseEntity = userRepository.save(responseEntity);

            responseDto = userMapper.userEntityToDto(responseEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while mapping and editing userEntity with id=" + userDto.getId(), ex);
        }

        return responseDto;
    }

    public void delete(UserDto userDto) throws ServiceException {

        validator.validateId(userDto);

        try {
            UserEntity userEntity = userMapper.userDtoToEntity(userDto);

            userRepository.delete(userEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while deleting userEntity with id=" + userDto.getId(), ex);
        }
    }


}
