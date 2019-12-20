package org.afeka.project.model.http;

import java.util.Arrays;

public enum HTTPMethod {
  GET("GET"),
  POST("POST"),
  DELETE("DELETE"),
  PUT("PUT"),
  HEAD("HEAD"),
  CONNECT("CONNECT"),
  OPTIONS("OPTIONS"),
  TRACE("TRACE"),
  PATCH("PATCH"),
  UNKNOWN("");

  private String method;

  HTTPMethod(String method) {
    this.method = method;
  }

  public String getMethod() {
    return method;
  }

  public static HTTPMethod forName(String method) {
    return Arrays.stream(HTTPMethod.values())
        .filter(HTTPMethodEnum -> HTTPMethodEnum.getMethod().equals(method.toUpperCase()))
        .findFirst().orElse(UNKNOWN);
  }
}
