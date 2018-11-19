package com.drees.cognito.config.response;

import com.drees.cognito.dtos.common.ErrorDTO;

public class ErrorResponse<T> {

  private ErrorDTO<T> error;

  public ErrorResponse(final T object, final String message) {
    error = new ErrorDTO<>(object, message);
  }

  public ErrorDTO<T> getError() {
    return error;
  }

  public void setError(final ErrorDTO<T> error) {
    this.error = error;
  }
}
