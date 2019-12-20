package org.afeka.project.util.http;

import com.google.common.collect.Maps;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPConstant;

import java.io.BufferedReader;
import java.util.Map;

public class HTTPHeadersParser {
  private BufferedReader reader;

  HTTPHeadersParser(BufferedReader reader) {
    this.reader = reader;
  }

  public Map<String, String> getHeaders() throws HTTPStructureException {
    try {
      Map<String, String> headers = Maps.newHashMap();
      String line = reader.readLine();

      while (!line.equals("")) {
        int sepIndex = line.indexOf(HTTPConstant.HEADER_SEP);

        if (sepIndex == -1 || sepIndex == (line.length() - 1)) {
          throw new HTTPStructureException(
              String.format("No sperator exists on line %s exists", line));
        }

        String name = line.substring(0, sepIndex);
        String value = line.substring(sepIndex + 1).trim();

        if (headers.containsKey(name)) {
          throw new HTTPStructureException(String.format("Duplicate header found %s", name));
        }

        headers.put(name, value);

        line = reader.readLine();
      }

      return headers;
    } catch (HTTPStructureException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new HTTPStructureException("Failed Reading http headers", ex);
    }
  }
}
