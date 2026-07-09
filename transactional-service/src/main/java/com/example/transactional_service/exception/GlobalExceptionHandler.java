package com.example.transactional_service.exception;



import com.example.transactional_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiResponse> handleTransactionNotFound(TransactionNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TransferFailedException.class)
    public ResponseEntity<ApiResponse> handleTransferFailed(TransferFailedException ex) {

        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse> handleInvalidAmount(InvalidAmountException ex) {

        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {

        return new ResponseEntity<>(
                new ApiResponse("Something went wrong"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}