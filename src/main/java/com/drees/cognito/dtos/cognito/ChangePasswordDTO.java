package com.drees.cognito.dtos.cognito;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordDTO {

  private String password = "";
  private String newPassword = "";

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(final String password) {
    this.password = password;
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

}
