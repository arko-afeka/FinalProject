package org.afeka.project.server;

import java.util.Map;

public interface HTTPDataFactory {
  HTTPRequest createRequest(
      String method, String uri, String version, Map<String, String> headers, String body);

  HTTPResponse createResponse(
      String version, String statusCode, Map<String, String> headers, String body);
}
