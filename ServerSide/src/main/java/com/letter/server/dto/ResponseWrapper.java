package com.letter.server.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseWrapper<T> {

    private T content;
}
