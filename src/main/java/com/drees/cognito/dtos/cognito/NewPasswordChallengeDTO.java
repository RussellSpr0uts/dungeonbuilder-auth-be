package com.drees.cognito.dtos.cognito;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewPasswordChallengeDTO {

  private String email = "";
  private String newPassword = "";
  private String session = "";

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param username
   *          the email to set
   */
  public void setEmail(final String email) {
    this.email = email;
  }

  /**
   * @return the newPassword
   */
  @JsonProperty("new-password")
  public String getNewPassword() {
    return newPassword;
  }

  /**
   * @param newPassword
   *          the newPassword to set
   */
  public void setNewPassword(final String newPassword) {
    this.newPassword = newPassword;
  }

  /**
   * @return the session
   */
  public String getSession() {
    return session;
  }

  /**
   * @param session
   *          the session to set
   */
  public void setSession(final String session) {
    this.session = session;
  }  

}
