package com.letter.server.controller;

import com.letter.server.dto.MessageDto;
import com.letter.server.dto.UserDto;
import com.letter.server.service.MessageService;
import com.letter.server.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Slf4j
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageDto>> findAllByTwoUsers(@RequestParam(name = "firstUserId", defaultValue = "-1L") String firstUserId,
                                                              @RequestParam(name = "secondUserId", defaultValue = "-1L") String secondUserId) {
        if ("-1L".equals(firstUserId) || "-1".equals(secondUserId)) {
            return ResponseEntity.badRequest().build();
        }

        long firstUserIdLong;
        long secondUserIdLong;

        try {
            firstUserIdLong = Long.parseLong(firstUserId);
            secondUserIdLong = Long.parseLong(secondUserId);
        } catch (NumberFormatException ex) {
            log.error("Exception while parsing UserId, firstUserId={}, secondUserId={}", firstUserId, secondUserId, ex);
            return ResponseEntity.badRequest().build();
        }

        try {
            List<MessageDto> messageDtoList = messageService.findAllByTwoUsers(UserDto.builder().id(firstUserIdLong).build(), UserDto.builder().id(secondUserIdLong).build());

            if (messageDtoList == null || messageDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(messageDtoList);
        } catch (ServiceException ex) {
            log.error("Cannot find messages by two users, firstUserId={}, secondUserId={}", firstUserId, secondUserId, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> findById(@PathVariable Long id) {
        try {
            MessageDto responseDto = messageService.findById(id);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot find message by its id, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> send(@RequestBody MessageDto messageDto) {
        try {
            MessageDto responseDto = messageService.send(messageDto);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot send message, senderId={}, recipientId={}", messageDto.getSender(), messageDto.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> read(@RequestBody MessageDto messageDto) {
        try {
            MessageDto responseDto = messageService.read(messageDto);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot read message, sender={}, recipient={}", messageDto.getSender(), messageDto.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value ="/read/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> readById(@PathVariable Long id) {
        try {
            MessageDto responseDto = messageService.read(MessageDto.builder().id(id).build());
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot read message, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> edit(@RequestBody MessageDto messageDto) {
        try {
            MessageDto responseDto = messageService.edit(messageDto);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, senderId={}, recipientId={}", messageDto.getSender(), messageDto.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> delete(@RequestBody MessageDto messageDto) {
        try {
            messageService.delete(messageDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, id={}", messageDto.getId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long id) {
        try {
            messageService.delete(MessageDto.builder().id(id).build());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
