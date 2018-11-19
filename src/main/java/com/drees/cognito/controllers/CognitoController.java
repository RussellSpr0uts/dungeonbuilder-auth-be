package com.drees.cognito.controllers;

import com.amazonaws.services.cognitoidp.model.*;
import com.drees.cognito.annotations.CognitoAuthorized;
import com.drees.cognito.bean.CognitoBean;
import com.drees.cognito.dtos.cognito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping(path = "/", produces = { APPLICATION_JSON_VALUE,
    APPLICATION_XML_VALUE })
public class CognitoController {

  @Autowired
  private CognitoBean cognitoBean;

  @RequestMapping(path = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_XML_VALUE)
  public String alive() {

    return "<xml>Alive!</xml>";
  }

  @RequestMapping(path = "/register", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public CognitoResponseDTO registerUser(
      @RequestBody final RegisterDTO registerDTO) {

    return cognitoBean.registerNewUser(registerDTO);
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public CognitoResponseDTO login(
      @RequestBody final LoginDTO loginDTO)  {

    return cognitoBean.login(loginDTO);
  }

  @RequestMapping(path = "/logout", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public GlobalSignOutResult logout(
      @RequestHeader("Authorization") final String accessToken) {

    return cognitoBean.logout(accessToken);

  }

  @RequestMapping(path = "/new-password-challenge", method = RequestMethod.PUT, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public CognitoResponseDTO newPasswordChallenge(
      @RequestBody final NewPasswordChallengeDTO newPasswordChallengeDTO) {

    return cognitoBean.newPasswordChallenge(newPasswordChallengeDTO);
  }

  @RequestMapping(path = "/forgot-password", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public ForgotPasswordResult forgotPassword(
      @RequestParam("email") final String email) {

    return cognitoBean.forgotPassword(email);

  }

  @RequestMapping(path = "/confirm-forgot-password", method = RequestMethod.PUT, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public ConfirmForgotPasswordResult confirmForgotPassword(
      @RequestBody final ConfirmForgotPasswordDTO confirmForgotPasswordDTO) {

    return cognitoBean.confirmForgotPassword(confirmForgotPasswordDTO);
  }

  @RequestMapping(path = "/change-password", method = RequestMethod.PUT, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @CognitoAuthorized
  public ChangePasswordResult changePassword(
      @RequestHeader("Authorization") final String accessToken,
      @RequestBody final ChangePasswordDTO changePasswordDTO) {

    return cognitoBean.changePassword(accessToken, changePasswordDTO);

  }

  @RequestMapping(path = "/update-attributes", method = RequestMethod.PUT, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @CognitoAuthorized
  public CognitoResponseDTO updateAttributes(
      @RequestHeader("Authorization") final String accessToken,
      @RequestBody final UpdateAttributesDTO updateAttributesDTO) {

    return cognitoBean.updateAttributes(updateAttributesDTO);
  }
  
  @RequestMapping(path = "/delete", method = RequestMethod.DELETE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @CognitoAuthorized
  public AdminDeleteUserResult deleteUser(
      @RequestHeader("Authorization") final String accessToken,
      @RequestBody final DeleteDTO deleteDTO) {

    return cognitoBean.deleteUser(deleteDTO);
  }
  
  @RequestMapping(path = "/refresh", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
      consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public CognitoResponseDTO refreshSession(@RequestBody final RefreshDTO deleteDTO) {

    return cognitoBean.refreshSession(deleteDTO);
  }
}
