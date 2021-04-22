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
public class MessageDto implements Serializable {

    private Long id;

    private UserDto sender;

    private UserDto recipient;

    private String messageText;

    private OffsetDateTime createTime;

    private OffsetDateTime modifyTime;

    private Boolean isRead;

    private String status;
}
