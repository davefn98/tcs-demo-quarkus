package com.tcs.dto;

import java.util.List;

public record GenericResponse<T>(
        String status,
        String message,
        List<T> data
) {
}
