package org.afeka.project.model.algorithm.ahocorasick;

import java.util.Objects;

public class Token<V> {
    private String token;
    private int begin;
    private int end;
    private V type;

    public Token(String token, int begin, int end, V type) {
        this.token = token;
        this.begin = begin;
        this.end = end;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public V getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token<?> token1 = (Token<?>) o;
        return begin == token1.begin &&
                end == token1.end &&
                Objects.equals(token, token1.token) &&
                Objects.equals(type, token1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, begin, end, type);
    }
}
