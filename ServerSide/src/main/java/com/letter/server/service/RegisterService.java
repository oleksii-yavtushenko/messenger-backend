package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.UserDto;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.UserValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Data
@Slf4j
@AllArgsConstructor
public class RegisterService {

    private UserRepository userRepository;

    private UserValidator userValidator;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    public UserDto register(UserDto userDto) throws ServiceException {

        userValidator.validate(userDto);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = userMapper.userDtoToEntity(userDto);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (EntityNotFoundException ex) {
            throw new LoginException("User cannot be saved, login=" + userDto.getLogin(), ex);
        } catch (Exception ex) {
            throw new ServiceException("Exception while registering user, login=" + userDto.getLogin());
        }

        return userMapper.userEntityToDto(userEntity);
    }
}
