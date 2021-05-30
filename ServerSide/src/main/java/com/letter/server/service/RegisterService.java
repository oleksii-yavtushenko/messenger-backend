package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.DetailedUser;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.UserDetailedValidator;
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

    private UserDetailedValidator userDetailedValidator;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    public DetailedUser register(DetailedUser detailedUser) throws ServiceException {

        userDetailedValidator.validate(detailedUser);

        detailedUser.setPassword(passwordEncoder.encode(detailedUser.getPassword()));

        UserEntity userEntity = userMapper.userDtoDetailedToEntity(detailedUser);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (EntityNotFoundException ex) {
            throw new LoginException("User cannot be saved, login=" + detailedUser.getLogin(), ex);
        } catch (Exception ex) {
            throw new ServiceException("Exception while registering user, login=" + detailedUser.getLogin());
        }

        return userMapper.userEntityToDtoDetailed(userEntity);
    }
}
