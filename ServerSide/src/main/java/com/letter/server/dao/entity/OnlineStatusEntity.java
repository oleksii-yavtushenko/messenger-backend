package com.letter.server.dao.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "online")
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OnlineStatusEntity {

    @Id
    private Long userId;

    private OffsetDateTime lastOnlineTime;

    private Boolean isOnline;
}
