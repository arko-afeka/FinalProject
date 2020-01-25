package org.afeka.project.validation.plugin.sqli.model;


import com.fasterxml.jackson.annotation.JsonValue;

public enum TokenType {
    TYPE_KEYWORD('k'),
    TYPE_UNION('U'),
    TYPE_GROUP('B'),
    TYPE_EXPRESSION('E'),
    TYPE_SQLTYPE('t'),
    TYPE_FUNCTION('f'),
    TYPE_BAREWORD('n'),
    TYPE_NUMBER('1'),
    TYPE_VARIABLE('v'),
    TYPE_STRING('s'),
    TYPE_OPERATOR('o'),
    TYPE_LOGIC_OPERATOR('&'),
    TYPE_COMMENT('c'),
    TYPE_COLLATE('A'),
    TYPE_LEFTPARENS('('),
    TYPE_RIGHTPARENS(')'),
    TYPE_LEFTBRACE('{'),
    TYPE_RIGHTBRACE('}'),
    TYPE_DOT('.'),
    TYPE_COMMA(','),
    TYPE_COLON(':'),
    TYPE_SEMICOLON(';'),
    TYPE_TSQL('T'),
    TYPE_UNKNOWN('?'),
    TYPE_EVIL('X'),
    TYPE_FINGERPRINT('F'),
    TYPE_BACKSLASH('\\');

    private char typeName;

    TokenType(char typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public char getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return String.valueOf(typeName);
    }
}
