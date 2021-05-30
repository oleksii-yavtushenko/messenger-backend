package com.letter.server.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder(builderMethodName = "userBuilder")
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Long id;

    private String login;

    private String email;

    private String authorizationToken;
}
