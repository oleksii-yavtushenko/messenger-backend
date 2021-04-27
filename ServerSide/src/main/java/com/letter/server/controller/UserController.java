package com.letter.server.controller;

import com.letter.server.dto.UserDto;
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
    public ResponseEntity<List<UserDto>> findAll() {

        List<UserDto> responseList = userService.findAll();

        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        try {
            UserDto responseDto = userService.findById(UserDto.builder().id(id).build());
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot find user by its id, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        try {
            UserDto responseDto = userService.save(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot register user {}", userDto, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> edit(@RequestBody UserDto userDto) {
        try {
            UserDto responseDto = userService.edit(userDto);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot edit user {}", userDto, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDto> delete(@RequestBody UserDto userDto) {
        try {
            userService.delete(userDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot delete user with id={}", userDto.getId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteById(@PathVariable Long id) {
        try {
            userService.delete(UserDto.builder().id(id).build());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot delete user with id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
