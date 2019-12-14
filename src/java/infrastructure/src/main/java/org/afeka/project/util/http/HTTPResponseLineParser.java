package org.afeka.project.util.http;

import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPConstant;
import org.afeka.project.model.http.HTTPHeaderLine;
import org.afeka.project.model.http.HTTPResponseLine;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPResponseLineParser extends HTTPHeaderLineParser {
  private List<String> lineParts;

  HTTPResponseLineParser(String line) {
    this.lineParts =
        Arrays.stream(line.split(HTTPConstant.SEPERATOR))
            .filter(part -> part.length() > 0)
            .collect(Collectors.toList());
  }

  @Override
  public HTTPHeaderLine parse() throws HTTPStructureException {
    try {

      if (this.lineParts.size() < 3) {
        throw new HTTPStructureException(
            String.format(
                "Response line missing parts %s",
                StringUtils.join(lineParts, HTTPConstant.SEPERATOR)));
      }

      String version = this.lineParts.get(0);

      this.validateVersion(version);

      String statusCodeStr = this.lineParts.get(1);

      if (!StringUtils.isNumeric(statusCodeStr)) {
        throw new HTTPStructureException(
            String.format("Status code is not numeric %s", statusCodeStr));
      }

      int statusCode = 0;
      try {
        statusCode = Integer.parseInt(statusCodeStr);
      } catch (Exception ex) {
        throw new HTTPStructureException(
            String.format("Status code is not numeric %s", statusCodeStr));
      }

      Pair<Integer, Integer> numericVersion = getNumericVersion(version);

      return new HTTPResponseLine(
          numericVersion.getLeft(),
          numericVersion.getRight(),
          statusCode,
          StringUtils.join(this.lineParts.subList(2, this.lineParts.size()), HTTPConstant.SEPERATOR));
    } catch (Exception ex) {
      throw new HTTPStructureException(
          String.format(
              "Problematic response fomrat %s",
              StringUtils.join(this.lineParts, HTTPConstant.SEPERATOR)));
    }
  }
}
