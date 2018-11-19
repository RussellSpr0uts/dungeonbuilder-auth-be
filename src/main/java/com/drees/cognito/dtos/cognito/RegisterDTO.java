package com.drees.cognito.dtos.cognito;

public class RegisterDTO {

  private String email = "";
  private String password = "";

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

}
