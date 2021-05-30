package com.letter.server.controller;

import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.User;
import com.letter.server.service.UserService;
import com.letter.server.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<DetailedUser>> findAll() {

        List<DetailedUser> responseList = userService.findAll();

        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedUser> findById(@PathVariable Long id) {
        try {
            DetailedUser responseDto = userService.findByIdDetailed(id);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot find user by its id, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<DetailedUser> register(@RequestBody DetailedUser detailedUser) {
        try {
            DetailedUser responseDto = userService.save(detailedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot register user {}", detailedUser, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping
    public ResponseEntity<DetailedUser> edit(@RequestBody DetailedUser detailedUser) {
        try {
            DetailedUser responseDto = userService.edit(detailedUser);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot edit user {}", detailedUser, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<DetailedUser> delete(@RequestBody DetailedUser detailedUser) {
        try {
            userService.delete(detailedUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot delete user with id={}", detailedUser.getId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetailedUser> deleteById(@PathVariable Long id) {
        try {
            userService.delete(DetailedUser.detailedUserBuilder().id(id).build());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot delete user with id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
