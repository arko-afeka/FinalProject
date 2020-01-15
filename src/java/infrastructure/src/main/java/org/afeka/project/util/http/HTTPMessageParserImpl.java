package org.afeka.project.util.http;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HTTPMessageParserImpl extends AbstractModule implements HTTPMessageParser {
  public HTTPMessageParserImpl() {}

  public HTTPMessage getMessage(String data) throws HTTPStructureException {
    try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
      HTTPRequestLine headerLine;
      Map<String, String> headers;

      headerLine = new HTTPRequestLineParser(reader.readLine()).parse();

      headers = new HTTPHeadersParser(reader).getHeaders();

      String cookieHeader = headers.get(HTTPHeader.COOKIE.getName());

      Map<String, String> cookies = Maps.newHashMap();

      if (Objects.nonNull(cookieHeader)) {
        cookies.putAll(
            Arrays.stream(";".split(cookieHeader))
                .map(String::trim)
                .filter(cookie -> cookie.contains("="))
                .map("="::split)
                .collect(Collectors.toMap(cookie -> cookie[0], cookie -> cookie[1])));
      }

      String body;
      if (headers.containsKey("Content-Type")
          && headers.get("Content-Type").equals("application/x-www-form-urlencoded")
          && headerLine.getType().equals(HTTPMessageType.Request)) {
        body = "";
        ((HTTPRequestLine) headerLine)
            .getQueryParams()
            .putAll(
                HTTPRequestLineParser.getParams(
                    reader.lines().collect(Collectors.joining("\r\n"))));
      } else {
        body = reader.lines().collect(Collectors.joining(HTTPConstant.CRLF));
      }

      return new HTTPMessage(headerLine, headers, cookies, body);
    } catch (Exception ex) {
      throw new HTTPStructureException(
          String.format("Failed processing http request %s", data), ex);
    }
  }
}
