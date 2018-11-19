package com.drees.cognito.dtos.cognito;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmForgotPasswordDTO {

  private String email = "";
  private String newPassword = "";
  private String resetPasswordVerificationCode = "";

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
   * @return the resetPasswordVerificationCode
   */
  @JsonProperty("reset-password-verification-code")
  public String getResetPasswordVerificationCode() {
    return resetPasswordVerificationCode;
  }

  /**
   * @param resetPasswordVerificationCode
   *          the resetPasswordVerificationCode to set
   */
  public void setResetPasswordVerificationCode(
      final String resetPasswordVerificationCode) {
    this.resetPasswordVerificationCode = resetPasswordVerificationCode;
  }

}
