package com.letter.server.controller;

import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.service.OnlineStatusService;
import com.letter.server.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online-status")
@Slf4j
@AllArgsConstructor
public class OnlineStatusController {

    private final OnlineStatusService onlineStatusService;

    @GetMapping
    public ResponseEntity<List<OnlineStatusDto>> findAllOnlineStatuses() {

        List<OnlineStatusDto> responseList = onlineStatusService.findAll();

        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OnlineStatusDto> findById(@PathVariable Long id) {
        try {
            OnlineStatusDto responseDto = onlineStatusService.findById(OnlineStatusDto.builder().id(id).build());
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot find online status by id, id={}", id, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<OnlineStatusDto> findByUserId(@PathVariable Long userId) {
        try {
            OnlineStatusDto responseDto = onlineStatusService.findByUserId(OnlineStatusDto.builder().userId(userId).build());
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot find OnlineStatus by userId, userId={}", userId, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<OnlineStatusDto> saveOnlineStatus(@RequestBody OnlineStatusDto onlineStatusDto) {
        try {
            OnlineStatusDto responseDto = onlineStatusService.save(onlineStatusDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot save OnlineStatus with userId={}", onlineStatusDto.getUserId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping
    public ResponseEntity<OnlineStatusDto> editOnlineStatus(@RequestBody OnlineStatusDto onlineStatusDto) {
        try {
            OnlineStatusDto responseDto = onlineStatusService.edit(onlineStatusDto);
            return ResponseEntity.ok(responseDto);
        } catch (ServiceException ex) {
            log.error("Cannot edit OnlineStatus with userId={}", onlineStatusDto.getUserId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteOnlineStatus(@RequestBody OnlineStatusDto onlineStatusDto) {
        try {
            onlineStatusService.delete(onlineStatusDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException ex) {
            log.error("Cannot edit OnlineStatus with userId={}", onlineStatusDto.getUserId(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
