package com.letter.server.controller;

import com.letter.server.dto.*;
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

    @GetMapping(value = "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<DetailedMessage>>> findAllByTwoUsersDetailed(@RequestParam(name = "firstUserId", defaultValue = "-1L") String firstUserId,
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
            List<DetailedMessage> messageList = messageService.findAllByTwoUsers(DetailedUser.detailedUserBuilder().id(firstUserIdLong).build(), DetailedUser.detailedUserBuilder().id(secondUserIdLong).build());

            if (messageList == null || messageList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(new ResponseWrapper<>(messageList));
        } catch (ServiceException ex) {
            log.error("Cannot find messages by two users, firstUserId={}, secondUserId={}", firstUserId, secondUserId, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<Message>>> findAllByTwoUsers(@RequestParam(name = "firstUserId", defaultValue = "-1L") String firstUserId,
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
            List<Message> messageList = messageService.findAllByTwoUsers(User.userBuilder().id(firstUserIdLong).build(), User.userBuilder().id(secondUserIdLong).build());

            if (messageList == null || messageList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(new ResponseWrapper<>(messageList));
        } catch (ServiceException ex) {
            log.error("Cannot find messages by two users, firstUserId={}, secondUserId={}", firstUserId, secondUserId, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/detailed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<DetailedMessage>> findById(@PathVariable Long id) {
        try {
            DetailedMessage responseDto = messageService.findById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(responseDto));
        } catch (ServiceException ex) {
            log.error("Cannot find message by its id, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/detailed/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<DetailedMessage>> sendDetailed(@RequestBody DetailedMessage detailedMessage) {
        try {
            DetailedMessage responseDto = messageService.sendDetailed(detailedMessage);
            return ResponseEntity.ok(new ResponseWrapper<>(responseDto));
        } catch (ServiceException ex) {
            log.error("Cannot send message, senderId={}, recipientId={}", detailedMessage.getSender(), detailedMessage.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Message>> send(@RequestBody Message message) {
        try {
            Message response = messageService.send(message);
            return ResponseEntity.ok(new ResponseWrapper<>(response));
        } catch (ServiceException ex) {
            log.error("Cannot send message, senderId={}, recipientId={}", message.getSenderId(), message.getRecipientId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedMessage> read(@RequestBody DetailedMessage detailedMessage) {
        try {
            DetailedMessage responseDto = messageService.read(detailedMessage);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot read message, sender={}, recipient={}", detailedMessage.getSender(), detailedMessage.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/read/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedMessage> readById(@PathVariable Long id) {
        try {
            DetailedMessage responseDto = messageService.read(DetailedMessage.builder().id(id).build());
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot read message, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedMessage> edit(@RequestBody DetailedMessage detailedMessage) {
        try {
            DetailedMessage responseDto = messageService.edit(detailedMessage);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, senderId={}, recipientId={}", detailedMessage.getSender(), detailedMessage.getRecipient(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedMessage> delete(@RequestBody DetailedMessage detailedMessage) {
        try {
            messageService.delete(detailedMessage);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, id={}", detailedMessage.getId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DetailedMessage> delete(@PathVariable Long id) {
        try {
            messageService.delete(DetailedMessage.builder().id(id).build());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServiceException ex) {
            log.error("Cannot edit message, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
