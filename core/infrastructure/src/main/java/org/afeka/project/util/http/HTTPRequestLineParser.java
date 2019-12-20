package org.afeka.project.util.http;

import com.google.common.collect.Maps;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPRequestLineParser extends HTTPHeaderLineParser {
  private List<String> lineParts;

  HTTPRequestLineParser(String line) {
    this.lineParts =
        Arrays.stream(line.split(HTTPConstant.SEPERATOR))
            .filter(part -> part.length() > 0)
            .collect(Collectors.toList());
  }

  Map<String, String> getParams(String paramsStr) {
    String[] paramsData = paramsStr.split("&");
    Map<String, String> values = Maps.newHashMap();

    Arrays.stream(paramsData)
        .forEach(
            param -> {
              int equaliLoc = param.indexOf("=");

              String value = null;

              if (equaliLoc != -1 && equaliLoc != (param.length() - 1)) {
                value = param.substring(equaliLoc + 1);
              } else {
                equaliLoc = param.length();
              }

              values.put(param.substring(0, equaliLoc), value);
            });

    return values;
  }

  @Override
  public HTTPHeaderLine parse() throws HTTPStructureException {
    try {
      if (lineParts.size() != 3) {
        throw new HTTPStructureException(
            String.format(
                "Request header line should contain 3 parts %s",
                StringUtils.join(lineParts, HTTPConstant.SEPERATOR)));
      }

      HTTPMethod method = HTTPMethod.forName(lineParts.get(0));

      String uri = lineParts.get(1);

      Map<String, String> queryParams = Maps.newHashMap();
      int paramsStart = uri.indexOf("?");
      if (paramsStart != -1 && paramsStart != (uri.length() - 1)) {
        queryParams = getParams(uri.substring(paramsStart + 1));
      }

      String version = lineParts.get(2);

      validateVersion(version);

      Pair<Integer, Integer> versionData = this.getNumericVersion(version);

      return new HTTPRequestLine(
          method, uri, queryParams, versionData.getLeft(), versionData.getRight());

    } catch (Exception ex) {
      throw new HTTPStructureException(
          String.format(
              "Non parsable http request header %s",
              StringUtils.join(lineParts, HTTPConstant.SEPERATOR)),
          ex);
    }
  }
}
