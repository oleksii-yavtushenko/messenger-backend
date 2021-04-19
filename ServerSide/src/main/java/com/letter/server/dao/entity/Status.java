package com.letter.server.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum Status implements Serializable {
    SENT("SENT"), DELAYED("DELAYED"), EDITED("EDITED"), DELETED("DELETED");

    private final String value;
}
