package com.lazyhippos.todolistapp.exception.handler;

import com.lazyhippos.todolistapp.exception.EntityNotFoundException;
import com.lazyhippos.todolistapp.exception.InvalidFormRequestException;
import com.lazyhippos.todolistapp.exception.MissingRequestParamException;
import com.lazyhippos.todolistapp.exception.NotPermittedRequestException;
import com.lazyhippos.todolistapp.exception.apierror.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidFormRequestException.class)
    protected ResponseEntity<Object> handleInvalidRequest() {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MissingRequestParamException.class)
    protected ResponseEntity<Object> handleMissingRequestParam() {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Required parameter is missing.");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NotPermittedRequestException.class)
    protected ResponseEntity<Object> handleNotPermittedRequest() {
        ApiError apiError = new ApiError(FORBIDDEN);
        apiError.setMessage("No permission to proceed the request.");
        return buildResponseEntity(apiError);
    }
}
