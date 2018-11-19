package com.drees.cognito.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.drees.cognito.bean.CognitoBean;

/**
 *
 *
 * Aspect class used to intercept calls that should be authorized by Cogntio
 * user pool
 * Throws an exception if user is not authenticated to stop any further access.
 *
 * @author Drees Homes copyright (c) 2018
 *
 *
 */
@Aspect
@Configuration
public class CognitoAuthenticatedAspect {

  @Autowired
  CognitoBean cognitoBean;

  @Before("@annotation(com.drees.cognito.annotations.CognitoAuthorized) && args(accessToken,..)")
  public void before(@SuppressWarnings("unused") final JoinPoint joinPoint,
      final String accessToken) {

    if (accessToken.isEmpty()) {
      throw new InvalidParameterException(
          "Token not provided, cannot validate user.");
    }
    cognitoBean.authenticateUserWithToken(accessToken);
  }
}
