package com.team20.t4.common.responseFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasicResponse<T> {
    private boolean success;
    private T response;
    private ErrorEntity error;
}

