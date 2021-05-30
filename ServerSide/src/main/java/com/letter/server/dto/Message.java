package com.letter.server.dto;

import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private Long id;

    private Long senderId;

    private Long recipientId;

    private String messageText;

    private OffsetDateTime createTime;

    private OffsetDateTime modifyTime;

    private Boolean isRead;

    private String status;
}
