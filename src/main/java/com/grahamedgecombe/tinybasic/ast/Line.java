package com.grahamedgecombe.tinybasic.ast;

import java.util.Objects;

public final class Line {

    private final int number;
    private final Statement statement;

    public Line(int number, Statement statement) {
        this.number = number;
        this.statement = statement;
    }

    public int getNumber() {
        return number;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (number != line.number) return false;
        if (!statement.equals(line.statement)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, statement);
    }

}
