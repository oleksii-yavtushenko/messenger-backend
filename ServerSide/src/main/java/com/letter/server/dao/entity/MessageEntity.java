package com.letter.server.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "message")
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "status", typeClass = Status.class)
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private UserEntity recipient;

    private String message;

    @CreationTimestamp
    private OffsetDateTime createTime;

    private OffsetDateTime modifyTime;

    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Type(type = "status")
    private Status status;
}
