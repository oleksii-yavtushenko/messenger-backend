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
public class DetailedMessage implements Serializable {

    private Long id;

    private DetailedUser sender;

    private DetailedUser recipient;

    private String messageText;

    private OffsetDateTime createTime;

    private OffsetDateTime modifyTime;

    private Boolean isRead;

    private String status;
}
