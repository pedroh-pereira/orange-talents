package br.com.zup.orange.talents.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumStatus {
    ACTIVE(1),
    BLOCKED(2),
    INACTIVE(3),
    DELETED(4);

    @JsonValue
    private final Integer status;
}

