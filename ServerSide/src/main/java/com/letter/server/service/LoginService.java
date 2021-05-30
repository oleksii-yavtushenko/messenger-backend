package com.letter.server.service;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.UserRepository;
import com.letter.server.dto.DetailedUser;
import com.letter.server.jwt.JwtTokenUtil;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.UserDetailedValidator;
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

    private UserDetailedValidator userDetailedValidator;

    private UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public UserEntity logIn(DetailedUser detailedUser) throws ServiceException {


        userDetailedValidator.validateLogin(detailedUser);

        UserEntity userEntity;

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    detailedUser.getLogin(), detailedUser.getPassword()
                            )
                    );

            userEntity = (UserEntity) authenticate.getPrincipal();
        } catch (EntityNotFoundException ex) {
            throw new LoginException("User cannot be found, login=" + detailedUser.getLogin(), ex);
        } catch (Exception ex) {
            throw new ServiceException("Exception while logging in user, login=" + detailedUser.getLogin());
        }

        return userEntity;
    }


}
