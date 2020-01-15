package org.afeka.project.validation.plugin;

import com.google.inject.Inject;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.plugin.xss.MessageTokenizer;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class XSSModule implements ValidationModule {
  private Set<MessageTokenizer> tokenizers;

  @Inject
  public XSSModule(Set<MessageTokenizer> tokenizers) {
    this.tokenizers = tokenizers;
  }

  @Override
  public AnalysisResultState analyse(HTTPMessage message) {

    return tokenizers
        .parallelStream()
        .map(messageTokenizer -> messageTokenizer.get(message))
        .flatMap(x -> x)
        .parallel()
        .map(this::analyseTokenizer)
        .filter(AnalysisResultState.BLOCK::equals)
        .findAny()
        .orElse(AnalysisResultState.ALLOW);
  }

  private boolean isBlackTag(Token token) {
    final String data = token.getToken().replaceAll("[\\s\t\r\n\f\\cK]", "");
    if (data.length() < 3) {
      return false;
    }

    return Arrays.stream(BlackTag.values())
        .parallel()
        .filter(x -> StringUtils.startsWithIgnoreCase(data, x.getName()))
        .map(x -> true)
        .findAny()
        .orElseGet(
            () ->
                StringUtils.startsWithIgnoreCase(data, "svg")
                    || StringUtils.startsWithIgnoreCase(data, "xsl"));
  }

  private AttributeType isBlackAttr(Token token) {
    final String data = token.getToken().replace("[ \t\r\n\f\\cK]", "");
    if (data.length() < 2) {
      return AttributeType.TYPE_NONE;
    }

    if (data.length() >= 5) {
      if (StringUtils.startsWithIgnoreCase(data, "on")) {
        return AttributeType.TYPE_BLACK;
      }

      if (StringUtils.startsWithIgnoreCase(data, "XMLNS")
          || StringUtils.startsWithIgnoreCase(data, "XLINK")) {
        return AttributeType.TYPE_BLACK;
      }
    }

    return Arrays.stream(BlackAttr.values())
        .parallel()
        .filter(x -> StringUtils.startsWithIgnoreCase(data, x.getName()))
        .map(BlackAttr::getType)
        .findAny()
        .orElse(AttributeType.TYPE_NONE);
  }

  private boolean isBlackURL(Token token) {
    int pos = 0;

    while (pos < token.getToken().length()
        && (token.getToken().charAt(pos) <= 32 || token.getToken().charAt(pos) >= 127)) {
      pos++;
    }

    String data = token.getToken().substring(pos);

    return StringUtils.startsWithIgnoreCase(data, "DATA")
        || StringUtils.startsWithIgnoreCase(data, "VIEW-SOURCE")
        || StringUtils.startsWithIgnoreCase(data, "VBSCRIPT")
        || StringUtils.startsWithIgnoreCase(data, "JAVA");
  }

  private AnalysisResultState analyseTokenizer(Tokenizer tokenizer) {
    AttributeType attrType = AttributeType.TYPE_NONE;
    while (tokenizer.hasNext()) {
      Token token = tokenizer.next();

      if (Objects.isNull(token)) {
        continue;
      }
      if (!token.getType().equals(TokenType.ATTR_VALUE)) {
        attrType = AttributeType.TYPE_NONE;
      }

      switch (token.getType()) {
        case TAG_NAME_OPEN:
          if (isBlackTag(token)) {
            return AnalysisResultState.BLOCK;
          }
          break;
        case ATTR_NAME:
          attrType = isBlackAttr(token);
          break;
        case ATTR_VALUE:
          switch (attrType) {
            case TYPE_NONE:
              break;
            case TYPE_BLACK:
              return AnalysisResultState.BLOCK;
            case TYPE_ATTR_URL:
              if (isBlackURL(token)) {
                return AnalysisResultState.BLOCK;
              }
              break;
            case TYPE_STYLE:
              return AnalysisResultState.BLOCK;
            case TYPE_ATTR_INDIRECT:
              if (!isBlackAttr(token).equals(AttributeType.TYPE_NONE)) {
                return AnalysisResultState.BLOCK;
              }
              break;
          }

          if (token.getToken().matches(".*![!+ ]\\[\\].*")) {
            return AnalysisResultState.BLOCK;
          }
          attrType = AttributeType.TYPE_NONE;

          break;
        case TAG_COMMENT:
          if (token.getToken().contains("`")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "IMPORT")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "ENTITY")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "[if")
              || StringUtils.startsWithIgnoreCase(token.getToken(), "XML")) {
            return AnalysisResultState.BLOCK;
          }
          break;
        case DOCTYPE:
          return AnalysisResultState.BLOCK;
      }
    }

    if (attrType == AttributeType.TYPE_BLACK) {
      return AnalysisResultState.BLOCK;
    }

    return AnalysisResultState.ALLOW;
  }

}
