package org.afeka.project.util.http;

import com.google.inject.AbstractModule;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPMessageParserImpl extends AbstractModule implements HTTPMessageParser {
  public HTTPMessageParserImpl() {}

  public HTTPMessage getMessage(String data) throws HTTPStructureException {
    try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
      HTTPHeaderLine headerLine;
      Map<String, String> headers;

      if (data.startsWith(HTTPConstant.HTTP_VERSION_PREFIX + HTTPConstant.HTTP_VERSION_SEPRATOR)) {
        headerLine = new HTTPResponseLineParser(reader.readLine()).parse();
      } else if (!data.startsWith(HTTPConstant.SEPERATOR)) {
        headerLine = new HTTPRequestLineParser(reader.readLine()).parse();
      } else {
        throw new HTTPStructureException(String.format("Starts with whitespace %s", data));
      }

      headers = new HTTPHeadersParser(reader).getHeaders();

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

      return new HTTPMessage(headerLine, headers, body);
    } catch (Exception ex) {
      throw new HTTPStructureException(
          String.format("Failed processing http request %s", data), ex);
    }
  }
}
