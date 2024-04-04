package org.example.moviesearchservice.exceptions;

public record ErrorResponse(String message) {
    public String getMessage() {
        return message;
    }
}
