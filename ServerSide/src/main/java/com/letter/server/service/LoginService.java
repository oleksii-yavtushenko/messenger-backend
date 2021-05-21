package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.UserDto;
import com.letter.server.jwt.JwtTokenUtil;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.UserValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Data
@Slf4j
@AllArgsConstructor
public class LoginService {

    private UserRepository userRepository;

    private UserValidator userValidator;

    private UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public UserEntity logIn(UserDto userDto) throws ServiceException {

        userValidator.validateLogin(userDto);

        UserEntity userEntity;

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    userDto.getLogin(), userDto.getPassword()
                            )
                    );

            userEntity = (UserEntity) authenticate.getPrincipal();
        } catch (EntityNotFoundException ex) {
            throw new LoginException("User cannot be found, login=" + userDto.getLogin(), ex);
        } catch (Exception ex) {
            throw new ServiceException("Exception while logging in user, login=" + userDto.getLogin());
        }

        return userEntity;
    }


}
