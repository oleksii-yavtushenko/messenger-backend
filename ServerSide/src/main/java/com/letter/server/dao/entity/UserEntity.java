package com.letter.server.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private OnlineStatusEntity onlineStatus;

    private String login;

    private String password;

    private String email;
}

