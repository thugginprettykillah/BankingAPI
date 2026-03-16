package sanchez.bankingapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sanchez.bankingapi.dto.exception.ErrorResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception)
    {
        log.error("Handle exception", exception);

        var errorDto = new ErrorResponseDto(
                "Internal Server Error :(",
                "We working on this problem. Please try again later.",
                LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(EntityNotFoundException exception)
    {
        log.error("Handle EntityNotFoundException", exception);

        var errorDto = new ErrorResponseDto(
                "Entity not found",
                exception.getMessage(),
                LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException exception)
    {
        log.error("Handle MethodArgumentNotValidException", exception);

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        var errorDto = new ErrorResponseDto(
                "Validation Error",
                errors.toString(),
                LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }



    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleException(EmailAlreadyExistsException exception)
    {
        log.error("Handle EmailAlreadyExistsException", exception);

        var errorDto = new ErrorResponseDto(
                "This Email Already Exists Error",
                exception.getMessage(),
                LocalDateTime.now());

        return  ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDto);
    }



    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleException(AuthorizationDeniedException exception)
    {
        log.error("Handle AuthorizationDeniedException", exception);

        var errorDto = new ErrorResponseDto("You are not authorized to perform this operation", exception.getMessage(), LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorDto);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(UsernameNotFoundException exception)
    {
        log.error("Handle UsernameNotFoundException", exception);

        var errorDto = new ErrorResponseDto(
                "Username Not Found",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(TransferAmountException.class)
    public ResponseEntity<ErrorResponseDto> handleException(TransferAmountException exception)
    {
        log.error("Handle Transfer Amount Error", exception);

        var errorDto = new ErrorResponseDto(
                "Transfer Amount Error",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(TransferDirectionException.class)
    public ResponseEntity<ErrorResponseDto> handleException(TransferDirectionException exception)
    {
        log.error("Handle Transfer Direction Error", exception);

        var errorDto = new ErrorResponseDto(
                "Transfer Direction Error",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

}
