package com.drees.cognito.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Annotation to be used in conjunction with CognitoAuthorizedAspect.java
 * Add this annotation to any method that needs to be authorized by cognito.
 * Expects the parameter accessToken to be added to the method for validation.
 *
 * @author Drees Homes copyright (c) 2018
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // can use in method only.
public @interface CognitoAuthorized {

  boolean enabled() default true;

}