package org.afeka.project.util.http;

import com.google.inject.AbstractModule;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPConstant;
import org.afeka.project.model.http.HTTPHeaderLine;
import org.afeka.project.model.http.HTTPMessage;

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
      } else if (!data.startsWith(HTTPConstant.SEPERATOR)){
        headerLine = new HTTPRequestLineParser(reader.readLine()).parse();
      } else {
        throw new HTTPStructureException(String.format("Starts with whitespace %s", data));
      }

      headers = new HTTPHeadersParser(reader).getHeaders();

      return new HTTPMessage(
          headerLine, headers, reader.lines().collect(Collectors.joining(HTTPConstant.CRLF)));
    } catch (Exception ex) {
      throw new HTTPStructureException(
          String.format("Failed processing http request %s", data), ex);
    }
  }
}
