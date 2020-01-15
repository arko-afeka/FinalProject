package org.afeka.project.validation.plugin.sqli.libinjection.util;

import org.afeka.project.validation.plugin.sqli.libinjection.model.TokenType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenToTokenType {
  public Map<Character, TokenType> mapping;

  public TokenToTokenType() {
    mapping =
        Arrays.stream(TokenType.values())
            .collect(Collectors.toMap(TokenType::getTypeName, (x) -> x));
  }

  public TokenType convert(char type) {
      return mapping.getOrDefault(type, TokenType.TYPE_NONE);
  }
}
