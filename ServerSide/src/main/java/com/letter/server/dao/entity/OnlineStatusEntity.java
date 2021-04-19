package com.letter.server.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "online_status")
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OnlineStatusEntity implements Serializable {

    @Id
    private Long id;

    private OffsetDateTime lastOnlineTime;

    private Boolean isOnline;
}
