package org.afeka.project.model.http;

import java.util.Map;

public class HTTPMessage {
  private HTTPRequestLine firstLine;

  private Map<String, String> headers;
  private Map<String, String> cookies;
  private String body;

  public HTTPMessage(HTTPRequestLine firstLine, Map<String, String> headers, Map<String, String> cookies, String body) {
    this.firstLine = firstLine;
    this.headers = headers;
    this.body = body;
  }

  public HTTPRequestLine getHeaderLine() {
    return firstLine;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public HTTPMessageType getType() {
    return firstLine.getType();
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getCookies() {
    return cookies;
  }

  @Override
  public String toString() {
    return "HTTPMessage{"
        + "firstLine="
        + firstLine
        + ", headers="
        + headers
        + ", body='"
        + body
        + '\''
        + '}';
  }
}
