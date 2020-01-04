package org.afeka.project.validation.plugin.xss.model;

import java.util.Objects;

public class Token {
  private String token;
  private TokenType type;

  public Token(String token, TokenType type) {
    this.token = token;
    this.type = type;
  }

  public String getToken() {
    return token;
  }

  public TokenType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Token{" +
            "token='" + token + '\'' +
            ", type=" + type +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Token token1 = (Token) o;
    return token.trim().equals(token1.token.trim()) &&
            type == token1.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, type);
  }
}
