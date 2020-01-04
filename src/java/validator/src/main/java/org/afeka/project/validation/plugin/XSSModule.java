package org.afeka.project.validation.plugin;

import com.google.common.collect.ImmutableSet;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.model.http.HTTPRequestLine;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.AttributeType;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public class XSSModule implements ValidationModule {

  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    return Stream.concat(
            Arrays.stream(ValidatorFlag.values())
                .parallel()
                .map(x -> new Tokenizer(x, message.getBody())),
            Arrays.stream(ValidatorFlag.values())
                .flatMap(
                    x ->
                        ((HTTPRequestLine) message.getHeaderLine())
                            .getQueryParams()
                            .values()
                            .parallelStream()
                            .map(y -> new Tokenizer(x, y))))
        .parallel()
        .map(this::analyseTokenizer)
        .filter(AnalysisResultState.BLOCK::equals)
        .findAny()
        .orElse(AnalysisResultState.ALLOW);
  }

  private AnalysisResultState analyseTokenizer(Tokenizer tokenizer) {
    AttributeType type = AttributeType.TYPE_NONE;
    while (tokenizer.hasNext()) {
      Token token = tokenizer.next();

      if (!token.getType().equals(TokenType.ATTR_NAME)) {
        type = AttributeType.TYPE_NONE;
      }

      switch (token.getType()) {
        case TAG_NAME_OPEN:
          break;
        case ATTR_NAME:
          break;
        case ATTR_VALUE:
          break;
        case TAG_COMMENT:
          if (token.getToken().contains("`")
              || token.getToken().equalsIgnoreCase("IMPORT")
              || token.getToken().equalsIgnoreCase("ENTITY")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "[if")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "XML")) {
            return AnalysisResultState.BLOCK;
          }
          break;
        case DOCTYPE:
          return AnalysisResultState.BLOCK;
      }
    }

    return AnalysisResultState.ALLOW;
  }

  @Override
  public AnalysisResultState analyseWithContext(HTTPMessage message, HTTPMessage fromStorage) {
    return AnalysisResultState.ALLOW;
  }

  @Override
  public Set<HTTPMessageType> messsageTypes() {
    return ImmutableSet.of(HTTPMessageType.Request);
  }
}
