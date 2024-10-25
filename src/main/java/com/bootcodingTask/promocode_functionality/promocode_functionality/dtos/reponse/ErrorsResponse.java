package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorsResponse {
    private final int status;
    private final String message;
    private final Map<String, String> errors;
    private final LocalDateTime timestamp;
}
