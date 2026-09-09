package ecommerce.project.exception;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path
) {
}