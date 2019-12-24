package org.afeka.project.model.http;

public class HTTPResponseLine implements HTTPHeaderLine {
  private int majorVersion;
  private int minorVersion;
  private int statusCode;
  private String statusPhrase;

  public HTTPResponseLine(int majorVersion, int minorVersion, int statusCode, String statusPhrase) {
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.statusCode = statusCode;
    this.statusPhrase = statusPhrase;
  }

  @Override
  public int getHTTPMajorVersion() {
    return majorVersion;
  }

  @Override
  public int getHTTPMinorVersion() {
    return minorVersion;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getStatusPhrase() {
    return statusPhrase;
  }

  @Override
  public HTTPMessageType getType() {
    return HTTPMessageType.Response;
  }

  @Override
  public String toString() {
    return "HTTPResponseLine{" +
            "majorVersion=" + majorVersion +
            ", minorVersion=" + minorVersion +
            ", statusCode=" + statusCode +
            ", statusPhrase='" + statusPhrase + '\'' +
            '}';
  }
}
