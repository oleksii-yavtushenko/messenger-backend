package com.letter.server.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    private OnlineStatusDto onlineStatus;

    private String login;

    private String password;

    private String email;
}
