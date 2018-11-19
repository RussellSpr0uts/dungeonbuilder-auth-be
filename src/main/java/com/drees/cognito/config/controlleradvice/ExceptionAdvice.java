package com.drees.cognito.config.controlleradvice;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.PasswordResetRequiredException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.drees.cognito.config.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse<Object> processValidationError(final Exception ex) {
    final ErrorResponse<Object> response = new ErrorResponse<>(
        ex.getStackTrace(), ex.getMessage());
    return response;
  }

  @ExceptionHandler({ NotAuthorizedException.class, UserNotFoundException.class,
      InvalidParameterException.class, PasswordResetRequiredException.class })
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ErrorResponse<Object> processAuthorizationError(final Exception ex) {
    final ErrorResponse<Object> response = new ErrorResponse<>(
        ex.getStackTrace(), ex.getMessage());
    return response;
  }

}
