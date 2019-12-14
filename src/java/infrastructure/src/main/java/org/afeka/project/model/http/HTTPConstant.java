package org.afeka.project.model.http;

public class HTTPConstant {
  public static final String SEPERATOR = " ";
  public static final String CRLF = "\r\n";
  public static final String HEADER_SEP = ":";

  public static final String HTTP_VERSION_PREFIX = "HTTP";
  public static final String HTTP_VERSION_SEPRATOR = "/";
  public static final char HTTP_VERSION_NUMBER_SEPERATOR = '.';
  public static final int HTTP_VERSION_LENGTH =
      HTTP_VERSION_PREFIX.length()
          + HTTP_VERSION_SEPRATOR.length()
          + HTTP_VERSION_SEPRATOR.length()
          + 2;
}
