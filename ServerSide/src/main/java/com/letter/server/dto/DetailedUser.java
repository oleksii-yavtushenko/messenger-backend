package com.letter.server.dto;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DetailedUser extends User implements Serializable {

    private OnlineStatusDto onlineStatus;

    private String password;

    private boolean enabled;

    @Builder(builderMethodName = "detailedUserBuilder")
    public DetailedUser(Long id, String login, String email, String authorizationToken, OnlineStatusDto onlineStatus, String password, boolean enabled) {
        super(id, login, email, authorizationToken);
        this.onlineStatus = onlineStatus;
        this.password = password;
        this.enabled = enabled;
    }
}
