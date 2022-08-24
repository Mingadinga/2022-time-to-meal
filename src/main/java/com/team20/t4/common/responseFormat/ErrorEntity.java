package com.team20.t4.common.responseFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorEntity {
    private String errorName;
    private String errorMessage;
}

