package at.ac.tgm.linguinsspringboot.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Helper to build ErrorResponse
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path, List<String> details, Throwable ex) {
        ErrorResponse body = new ErrorResponse(
                Instant.now().toEpochMilli(),
                status.value(),
                status.getReasonPhrase(),
                message != null ? message : (ex != null ? ex.getMessage() : null),
                path,
                ex != null ? ex.getClass().getSimpleName() : null,
                details
        );
        return new ResponseEntity<>(body, status);
    }

    // 400 - Validation errors from @Valid on @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.toList());
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), details, ex);
    }

    // 400 - JSON parse errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request", request.getRequestURI(), details, ex);
    }

    // 400 - Missing request parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        List<String> details = List.of(ex.getParameterName() + " parameter is missing");
        return buildResponse(HttpStatus.BAD_REQUEST, "Missing request parameter", request.getRequestURI(), details, ex);
    }

    // 400 - Type mismatch for request parameters
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String msg = String.format("Parameter '%s' should be of type %s", ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        List<String> details = List.of(msg);
        return buildResponse(HttpStatus.BAD_REQUEST, "Type mismatch", request.getRequestURI(), details, ex);
    }

    // 404 - No handler found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandler(NoHandlerFoundException ex, HttpServletRequest request) {
        List<String> details = List.of("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", request.getRequestURI(), details, ex);
    }

    // 409 - Database / Data access exceptions
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex, HttpServletRequest request) {
        List<String> details = List.of(ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Database error", request.getRequestURI(), details, ex);
    }

    // 400 - Illegal args, missing elements
    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        List<String> details = List.of(ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad request", request.getRequestURI(), details, ex);
    }

    // Fallback for all other exceptions - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
        List<String> details = List.of(ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request.getRequestURI(), details, ex);
    }

    // Strukturierte Fehlerantwort
    public static class ErrorResponse {
        private long timestamp;         // epoch millis
        private int status;             // http status code
        private String error;           // reason phrase
        private String message;         // short message
        private String path;            // request path
        private String exception;       // exception simple class name
        private List<String> details;   // optional detailed messages

        public ErrorResponse(long timestamp, int status, String error, String message, String path, String exception, List<String> details) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.exception = exception;
            this.details = details;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getException() {
            return exception;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        public List<String> getDetails() {
            return details;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }
    }
}