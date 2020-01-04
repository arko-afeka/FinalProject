package org.afeka.project.model.http;

import java.util.Map;

public class HTTPRequestLine implements HTTPHeaderLine {
  private HTTPMethod method;
  private String uri;
  private int majorVersion;
  private int minorVersion;
  private Map<String, String> queryParams;

  public HTTPRequestLine(
      HTTPMethod method,
      String uri,
      Map<String, String> queryParams,
      int majorVersion,
      int minorVersion) {
    this.method = method;
    this.uri = uri;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.queryParams = queryParams;
  }

  public HTTPMethod getMethod() {
    return method;
  }

  public String getUri() {
    return uri;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  @Override
  public int getHTTPMajorVersion() {
    return majorVersion;
  }

  @Override
  public int getHTTPMinorVersion() {
    return minorVersion;
  }

  @Override
  public HTTPMessageType getType() {
    return HTTPMessageType.Request;
  }

  @Override
  public String toString() {
    return "HTTPRequestLine{"
        + "method="
        + method
        + ", uri='"
        + uri
        + '\''
        + ", majorVersion="
        + majorVersion
        + ", minorVersion="
        + minorVersion
        + ", queryParams="
        + queryParams
        + '}';
  }
}
