package com.letter.server.controller;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.UserDto;
import com.letter.server.jwt.JwtTokenUtil;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.LoginService;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
@Slf4j
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> logIn(@RequestBody UserDto userDto) {
        log.info("Logging in user, login={}", userDto.getLogin());

        UserEntity user;

        try {
            user = loginService.logIn(userDto);

            UserDto userResponse = userMapper.userEntityToDto(user);

            userResponse.setAuthorizationToken(jwtTokenUtil.generateAccessToken(user));

            log.info("Successful user login, login={}", user.getLogin());

            return ResponseEntity
                    .ok().header(HttpHeaders.AUTHORIZATION, userResponse.getAuthorizationToken())
                    .body(userResponse);

        } catch (ServiceException ex) {
            if (ex instanceof LoginException || ex instanceof ValidationException) {
                log.error("User cannot be logged in, login={}, cause={}", userDto.getLogin(), ex.getMessage(), ex.getCause());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDto);
            }
            log.error("Exception while logging in user, login={}", userDto.getLogin(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }
    }
}

