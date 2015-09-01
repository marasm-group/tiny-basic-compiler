package com.grahamedgecombe.tinybasic.tokenizer;

import java.util.Objects;
import java.util.Optional;

public final class Token {

    public enum Type {
        EOF,
        LF,
        VAR,
        KEYWORD,
        NUMBER,
        STRING,
        PLUS,
        MINUS,
        MULT,
        DIV,
        LPAREN,
        RPAREN,
        EQ,
        NE,
        GT,
        GTE,
        LT,
        LTE,
        COMMA
    }
    public long line=0;
    private final Type type;
    private final Optional<String> value;

    public Token(long _line,Type type) {
        this.type = type;
        this.value = Optional.empty();
        this.line=_line;
    }

    public Token(long _line,Type type, String value) {
        this.type = type;
        this.value = Optional.of(value);
        this.line=_line;
    }

    public Type getType() {
        return type;
    }

    public Optional<String> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        if (!value.equals(token.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return Token.class.getSimpleName() + " [type=" + type + ", value=" + value + "]";
    }

}
