package com.letter.server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    SENT("SENT"), DELAYED("DELAYED"), EDITED("EDITED"), DELETED("DELETED");

    private final String value;
}
