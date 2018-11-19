package com.drees.cognito.dtos.cognito;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateAttributesDTO {

  private String email = "";
  private String newEmail = "";

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
   * @return the newEmail
   */
  @JsonProperty("new-email")
  public String getNewEmail() {
    return newEmail;
  }

  /**
   * @param newEmail
   *          the newEmail to set
   */
  public void setNewEmail(final String newEmail) {
    this.newEmail = newEmail;
  }

}
