package org.afeka.project.validation.plugin.sqli.libinjection.model;

public class Keyword {
    private String token;
    private TokenType type;

    public Keyword(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public Keyword() {
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }
}
