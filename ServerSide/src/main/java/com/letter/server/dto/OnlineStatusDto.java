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
public class OnlineStatusDto implements Serializable {

    private Long id;

    private Long userId;

    private OffsetDateTime lastOnlineTime;

    private Boolean isOnline;
}
