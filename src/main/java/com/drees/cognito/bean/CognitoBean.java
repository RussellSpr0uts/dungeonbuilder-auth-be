package com.drees.cognito.bean;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.drees.cognito.dtos.cognito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CognitoBean {

  @Value("${aws.accesskey}")
  private String awsAccessKey;
  @Value("${aws.secretkey}")
  private String awsSecretKey;
  @Value("${aws.poolid}")
  private String cognitoPoolId;
  @Value("${aws.clientid}")
  private String cognitoClientId;
  @Value("${aws.clientSecret}")
  private String cognitoClientSecret;
  @Value("${spring.profiles.active}")
  private String environment;
  
  private AWSCognitoIdentityProvider identityProvider;


  public CognitoBean() {

  }

  @PostConstruct
  public void init() {
    identityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
        .withRegion(Regions.US_EAST_1).build();
  }

  public CognitoResponseDTO registerNewUser(final RegisterDTO registerDTO) {
    
    if(registerDTO.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null!");
    } else if(registerDTO.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null!");
    }

    final ArrayList<AttributeType> attributeList = new ArrayList<>();

    attributeList.add(new AttributeType().withName("email").withValue(registerDTO.getEmail()));
    attributeList
        .add(new AttributeType().withName("email_verified").withValue("true"));


    final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest()
        .withUserPoolId(cognitoPoolId).withTemporaryPassword(registerDTO.getPassword())
        .withUsername(registerDTO.getEmail()).withUserAttributes(attributeList)
        .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
        .withForceAliasCreation(Boolean.FALSE);

    AdminCreateUserResult adminCreateUserResult = identityProvider.adminCreateUser(adminCreateUserRequest);
    String cognitoId = "";
    
    // If user creation did not fail add the user to the database
    if(adminCreateUserResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
      
      cognitoId = adminCreateUserResult.getUser().getUsername();
      
    }
    
    CognitoResponseDTO outgoingDTO = new CognitoResponseDTO(registerDTO, cognitoId);
    outgoingDTO.setCognitoCreateUserResult(adminCreateUserResult);

    return outgoingDTO;
  }

  public CognitoResponseDTO login(final LoginDTO loginDTO) {

    CognitoResponseDTO outgoingDTO = new CognitoResponseDTO();
    
    final Map<String, String> authParams = new HashMap<>();
    authParams.put("USERNAME", loginDTO.getEmail());
    authParams.put("PASSWORD", loginDTO.getPassword());
    authParams.put("SECRET_HASH",
        calculateSecretHash(cognitoClientId, cognitoClientSecret, loginDTO.getEmail()));

    final AdminInitiateAuthRequest adminInitiateAuthRequest = new AdminInitiateAuthRequest()
        .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
        .withAuthParameters(authParams).withClientId(cognitoClientId)
        .withUserPoolId(cognitoPoolId);
    
    AdminInitiateAuthResult authResult = identityProvider.adminInitiateAuth(adminInitiateAuthRequest);
    
    if(authResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {

      outgoingDTO.setCognitoInitiateAuthResult(authResult);

    } else {
      throw new NotAuthorizedException("Could not login user: " + loginDTO.getEmail());
    }
    
    return outgoingDTO;
  }

  public CognitoResponseDTO newPasswordChallenge(final NewPasswordChallengeDTO newPasswordChallengeDTO) {

    CognitoResponseDTO outgoingDTO = new CognitoResponseDTO();
    
    final Map<String, String> challengeResponse = new HashMap<>();
    challengeResponse.put("USERNAME", newPasswordChallengeDTO.getEmail());
    challengeResponse.put("NEW_PASSWORD", newPasswordChallengeDTO.getNewPassword());
    challengeResponse.put("SECRET_HASH",
        calculateSecretHash(cognitoClientId, cognitoClientSecret, newPasswordChallengeDTO.getEmail()));

    final AdminRespondToAuthChallengeRequest adminRespondToAuthChallengeRequest = 
        new AdminRespondToAuthChallengeRequest()
        .withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
        .withClientId(cognitoClientId).withSession(newPasswordChallengeDTO.getSession())
        .withUserPoolId(cognitoPoolId)
        .withChallengeResponses(challengeResponse);

    AdminRespondToAuthChallengeResult authResult = identityProvider
        .adminRespondToAuthChallenge(adminRespondToAuthChallengeRequest);
    
    if(authResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {

      outgoingDTO.setCognitoRespondToAuthChallengeResult(authResult);
      
    } else {
      throw new RuntimeException("Could not update user's password: " + newPasswordChallengeDTO.getEmail());
    }
    
    return outgoingDTO;
  }

  public void authenticateUserWithToken(final String token) {

    final GetUserRequest authRequest = new GetUserRequest()
        .withAccessToken(token);

    identityProvider.getUser(authRequest);
  }

  public ForgotPasswordResult forgotPassword(final String email) {

    final ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest()
        .withClientId(cognitoClientId).withUsername(email).withSecretHash(
            calculateSecretHash(cognitoClientId, cognitoClientSecret, email));

    return identityProvider.forgotPassword(forgotPasswordRequest);
  }

  public ConfirmForgotPasswordResult confirmForgotPassword(final ConfirmForgotPasswordDTO confirmForgotPasswordDTO) {

    final ConfirmForgotPasswordRequest confirmForgotPasswordRequest = new ConfirmForgotPasswordRequest()
        .withClientId(cognitoClientId).withUsername(confirmForgotPasswordDTO.getEmail())
        .withPassword(confirmForgotPasswordDTO.getNewPassword())
        .withSecretHash(
            calculateSecretHash(cognitoClientId, cognitoClientSecret, confirmForgotPasswordDTO.getEmail()))
        .withConfirmationCode(confirmForgotPasswordDTO.getResetPasswordVerificationCode());

    return identityProvider.confirmForgotPassword(confirmForgotPasswordRequest);
  }

  public ChangePasswordResult changePassword(final String accessToken,
      final ChangePasswordDTO changePasswordDTO) {

    final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest()
        .withAccessToken(accessToken).withPreviousPassword(changePasswordDTO.getPassword())
        .withProposedPassword(changePasswordDTO.getNewPassword());

    return identityProvider
            .changePassword(changePasswordRequest);
  }

  public GlobalSignOutResult logout(final String accessToken) {

    final GlobalSignOutRequest globalSignOutRequest = new GlobalSignOutRequest()
        .withAccessToken(accessToken);

    return identityProvider.globalSignOut(globalSignOutRequest);
  }

  public CognitoResponseDTO updateAttributes(final UpdateAttributesDTO updateAttributesDTO) {

    boolean cognitoUpdateRequired = false;
    CognitoResponseDTO outgoingDTO = new CognitoResponseDTO();
    
    final String userCognitoId = getCognitoIdByEmail(updateAttributesDTO.getEmail());

    final ArrayList<AttributeType> attributeList = new ArrayList<>();

    if (!updateAttributesDTO.getNewEmail().isEmpty()) {
      final AttributeType emailAttribute = new AttributeType().withName("email")
          .withValue(updateAttributesDTO.getNewEmail());
      attributeList.add(emailAttribute);

      attributeList.add(
          new AttributeType().withName("email_verified").withValue("true"));
      
      cognitoUpdateRequired = true;
    }
    
    if (cognitoUpdateRequired) {
      final AdminUpdateUserAttributesRequest adminUpdateUserAttributesRequest = new AdminUpdateUserAttributesRequest()
          .withUsername(userCognitoId).withUserPoolId(cognitoPoolId)
          .withUserAttributes(attributeList);
  
      final AdminUpdateUserAttributesResult adminUpdateUserAttributesResult = identityProvider
          .adminUpdateUserAttributes(adminUpdateUserAttributesRequest);
      
      // If Email was updated send a confirmation email
      if (adminUpdateUserAttributesResult.getSdkHttpMetadata()
          .getHttpStatusCode() == 200) {
        
        outgoingDTO.setCognitoUpdateUserAttributesResult(adminUpdateUserAttributesResult);
      }
    }
    
    return outgoingDTO;
  }
  
  public CognitoResponseDTO refreshSession(final RefreshDTO refreshDTO) {
    
    CognitoResponseDTO outgoingDTO;
    
    final String cognitoId = getCognitoIdByEmail(refreshDTO.getEmail());
    
    final Map<String, String> authParams = new HashMap<>();
    authParams.put("REFRESH_TOKEN", refreshDTO.getRefreshToken());
    authParams.put("SECRET_HASH",
        calculateSecretHash(cognitoClientId, cognitoClientSecret, cognitoId));
    
    final AdminInitiateAuthRequest adminInitiateAuthRequest = new AdminInitiateAuthRequest()
        .withAuthFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
        .withAuthParameters(authParams).withClientId(cognitoClientId)
        .withUserPoolId(cognitoPoolId);
    
    AdminInitiateAuthResult authResult = 
        identityProvider.adminInitiateAuth(adminInitiateAuthRequest);
    
    if(authResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
      
      outgoingDTO = buildCognitoOutgoingDTOByCognitoId(cognitoId);
      outgoingDTO.setCognitoInitiateAuthResult(authResult);
      
    } else {
      throw new NotAuthorizedException("Could not refresh session for user: " + cognitoId);
    }
    
    return outgoingDTO;
    
  }
  
  public AdminDeleteUserResult deleteUser(final DeleteDTO deleteDTO) {

    AdminDeleteUserResult adminDeleteUserResult;
    
    final String userToBeDeletedCognitoId = getCognitoIdByEmail(deleteDTO.getEmail());
    
    AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest()
        .withUsername(userToBeDeletedCognitoId)
        .withUserPoolId(cognitoPoolId);
    
    adminDeleteUserResult = identityProvider.adminDeleteUser(adminDeleteUserRequest);
    
    if(adminDeleteUserResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
      throw new RuntimeException("Failed to delete user with Cognito ID: " + userToBeDeletedCognitoId + " from user pool.");
    }   
    
    return adminDeleteUserResult;
  }

  private static String calculateSecretHash(final String userPoolClientId,
      final String userPoolClientSecret, final String userName) {
    final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    final SecretKeySpec signingKey = new SecretKeySpec(
        userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
        HMAC_SHA256_ALGORITHM);
    try {
      final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
      mac.init(signingKey);
      mac.update(userName.getBytes(StandardCharsets.UTF_8));
      final byte[] rawHmac = mac
          .doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(rawHmac);
    } catch (final Exception e) {
      throw new RuntimeException("Error while calculating ");
    }
  }
  
  private CognitoResponseDTO buildCognitoOutgoingDTOByCognitoId(final String cognitoId) {
    
    CognitoResponseDTO outgoingDTO = new CognitoResponseDTO();
    
    outgoingDTO.setCognitoId(cognitoId);
    
    return outgoingDTO;
  }
  
  private String getCognitoIdByEmail(final String email) {
    ListUsersRequest listUsersRequest = new ListUsersRequest()
        .withFilter("email = \"" + email + "\"")
        .withUserPoolId(cognitoPoolId);
    ListUsersResult userResult = identityProvider.listUsers(listUsersRequest);
    
    if(userResult.getUsers().size() != 1) {
      throw new IllegalArgumentException("User does not exist with email: " 
          + email);
    }
    
    return userResult.getUsers().get(0).getUsername();
  }

}
