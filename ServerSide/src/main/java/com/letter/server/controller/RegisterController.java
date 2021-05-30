package com.letter.server.controller;

import com.letter.server.dto.DetailedUser;
import com.letter.server.service.RegisterService;
import com.letter.server.service.exception.LoginException;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@Slf4j
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<DetailedUser> register(@RequestBody DetailedUser detailedUser) {
        log.info("Registering user, login={}, email={}", detailedUser.getLogin(), detailedUser.getEmail());

        DetailedUser repositoryUser;

        try {
            repositoryUser = registerService.register(detailedUser);

            log.info("Successful user registration, login={}", repositoryUser.getLogin());
            return ResponseEntity.ok(repositoryUser);
        } catch (ServiceException ex) {
            if (ex instanceof LoginException || ex instanceof ValidationException) {
                log.error("User cannot be registered, login={}, cause={}", detailedUser.getLogin(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detailedUser);
            }
            log.error("Exception while registering user, login={}", detailedUser.getLogin(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailedUser);
        }
    }
}
