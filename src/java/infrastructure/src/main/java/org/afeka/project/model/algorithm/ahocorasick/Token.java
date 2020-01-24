package org.afeka.project.model.algorithm.ahocorasick;

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
}
