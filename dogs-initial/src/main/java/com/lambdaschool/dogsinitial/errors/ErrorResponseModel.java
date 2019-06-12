package com.lambdaschool.dogsinitial.errors;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
public class ErrorResponseModel {
    // brief title for error condition
    @Getter
    private final String title;
    // HTTP Status Field
    @Getter
    private final HttpStatus status;
    // short description of error
    @Getter
    private final String detail;
    @Getter
    private final LocalDateTime timestamp;
    // info for developers. Classes, stack traces
    @Getter
    private final String developerMessage;
    // a list of validation exceptions
    @Getter
    private final ArrayList<String> errors;
}
