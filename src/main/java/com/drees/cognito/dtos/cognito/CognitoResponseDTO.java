package com.drees.cognito.dtos.cognito;

import com.amazonaws.services.cognitoidp.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * TODO Developer, please finish the auto-generated comment
 * 
 * @author Drees Homes copyright (c) 2018
 *
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CognitoResponseDTO {

  private String email = "";
  private String cognitoId = "";
  private AdminCreateUserResult cognitoCreateUserResult = null;
  private AdminDeleteUserResult cognitoDeleteUserResult = null;
  private AdminInitiateAuthResult cognitoInitiateAuthResult = null;
  private AdminRespondToAuthChallengeResult cognitoRespondToAuthChallengeResult = null;
  private AdminUpdateUserAttributesResult cognitoUpdateUserAttributesResult = null;
  private GetUserResult cognitoGetUserResult = null;

  public CognitoResponseDTO() {
    
  }
  
  public CognitoResponseDTO(RegisterDTO registerDTO, String cognitoId) {
    this.email = registerDTO.getEmail();
    this.cognitoId = cognitoId;
  }
  
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
   * @return the cognitoId
   */
  @JsonProperty("cognito-id")
  public String getCognitoId() {
    return cognitoId;
  }

  /**
   * @param cognitoId the cognitoId to set
   */
  public void setCognitoId(String cognitoId) {
    this.cognitoId = cognitoId;
  }

  /**
   * @return the adminCreateUserResult
   */
  public AdminCreateUserResult getCognitoCreateUserResult() {
    return cognitoCreateUserResult;
  }
  
  /**
   * @param cognitoCreateUserResult the adminCreateUserResult to set
   */
  public void setCognitoCreateUserResult(
      AdminCreateUserResult cognitoCreateUserResult) {
    this.cognitoCreateUserResult = cognitoCreateUserResult;
  }

  /**
   * @return the adminDeleteUserResult
   */
  public AdminDeleteUserResult getCognitoDeleteUserResult() {
    return cognitoDeleteUserResult;
  }

  /**
   * @param cognitoDeleteUserResult the adminDeleteUserResult to set
   */
  public void setCognitoDeleteUserResult(
      AdminDeleteUserResult cognitoDeleteUserResult) {
    this.cognitoDeleteUserResult = cognitoDeleteUserResult;
  }
  
  /**
   * @return the adminInitiateAuthResult
   */
  public AdminInitiateAuthResult getCognitoInitiateAuthResult() {
    return cognitoInitiateAuthResult;
  }

  /**
   * @param cognitoInitiateAuthResult the adminInitiateAuthResult to set
   */
  public void setCognitoInitiateAuthResult(
      AdminInitiateAuthResult cognitoInitiateAuthResult) {
    this.cognitoInitiateAuthResult = cognitoInitiateAuthResult;
  }

  /**
   * @return the adminRespondToAuthChallengeResult
   */
  public AdminRespondToAuthChallengeResult getCognitoRespondToAuthChallengeResult() {
    return cognitoRespondToAuthChallengeResult;
  }

  /**
   * @param adminRespondToAuthChallengeResult the adminRespondToAuthChallengeResult to set
   */
  public void setCognitoRespondToAuthChallengeResult(
      AdminRespondToAuthChallengeResult adminRespondToAuthChallengeResult) {
    this.cognitoRespondToAuthChallengeResult = adminRespondToAuthChallengeResult;
  }

  /**
   * @return the adminUpdateUserAttributesResult
   */
  public AdminUpdateUserAttributesResult getCognitoUpdateUserAttributesResult() {
    return cognitoUpdateUserAttributesResult;
  }

  /**
   * @param adminUpdateUserAttributesResult the adminUpdateUserAttributesResult to set
   */
  public void setCognitoUpdateUserAttributesResult(
      AdminUpdateUserAttributesResult adminUpdateUserAttributesResult) {
    this.cognitoUpdateUserAttributesResult = adminUpdateUserAttributesResult;
  }

  /**
   * @return the getUserResult
   */
  public GetUserResult getCognitoGetUserResult() {
    return cognitoGetUserResult;
  }

  /**
   * @param cognitoGetUserResult the getUserResult to set
   */
  public void setCognitoGetUserResult(GetUserResult cognitoGetUserResult) {
    this.cognitoGetUserResult = cognitoGetUserResult;
  }
}
