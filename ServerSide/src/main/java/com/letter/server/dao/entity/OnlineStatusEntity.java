package com.letter.server.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "online_status")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OnlineStatusEntity implements Serializable {

    @Id
    private Long id;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private OffsetDateTime lastOnlineTime;

    private Boolean isOnline;

    @Override
    public String toString() {
        return "OnlineStatusEntity{" +
                "id=" + id +
                ", user.id=" + user.getId() +
                ", lastOnlineTime=" + lastOnlineTime +
                ", isOnline=" + isOnline +
                '}';
    }
}
