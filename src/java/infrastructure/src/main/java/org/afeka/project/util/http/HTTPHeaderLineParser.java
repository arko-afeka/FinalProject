package org.afeka.project.util.http;

import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPConstant;
import org.afeka.project.model.http.HTTPHeaderLine;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.tuple.Pair;

public abstract class HTTPHeaderLineParser {
  public abstract HTTPHeaderLine parse() throws HTTPStructureException;

  protected void validateVersion(String versionPart) throws HTTPStructureException {
    if (versionPart.length() != HTTPConstant.HTTP_VERSION_LENGTH) {
      throw new HTTPStructureException(
          String.format("Length of http version is incorrect %s", versionPart));
    }

    if (!versionPart.startsWith(
        HTTPConstant.HTTP_VERSION_PREFIX + HTTPConstant.HTTP_VERSION_SEPRATOR)) {
      throw new HTTPStructureException(
          String.format("Version prefix is incorrect %s", versionPart));
    }

    if ((!CharUtils.isAsciiNumeric(
            versionPart.charAt(
                HTTPConstant.HTTP_VERSION_PREFIX.length()
                    + HTTPConstant.HTTP_VERSION_SEPRATOR.length())))
        || (versionPart.charAt(
                HTTPConstant.HTTP_VERSION_PREFIX.length()
                    + HTTPConstant.HTTP_VERSION_SEPRATOR.length()
                    + 1)
            != HTTPConstant.HTTP_VERSION_NUMBER_SEPERATOR)
        || (!CharUtils.isAsciiNumeric(versionPart.charAt(HTTPConstant.HTTP_VERSION_LENGTH - 1)))) {
      throw new HTTPStructureException(
          String.format("Numeric version structure is incorrect %s", versionPart));
    }
  }

  protected Pair<Integer, Integer> getNumericVersion(String versionData) {
    String[] versionParts =
        versionData.split(HTTPConstant.HTTP_VERSION_SEPRATOR)[1].split(
            "\\" + HTTPConstant.HTTP_VERSION_NUMBER_SEPERATOR);

    return Pair.of(Integer.parseInt(versionParts[0]), Integer.parseInt(versionParts[1]));
  }
}
