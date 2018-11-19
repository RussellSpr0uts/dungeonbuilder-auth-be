package com.drees.cognito.dtos.cognito;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshDTO {

  private String email = "";
  private String refreshToken = "";

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(final String email) {
    this.email = email;
  }
  
  /**
   * @return the refreshToken
   */
  @JsonProperty("refresh-token")
  public String getRefreshToken() {
    return refreshToken;
  }
  
  /**
   * @param refreshToken the refreshToken to set
   */
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
