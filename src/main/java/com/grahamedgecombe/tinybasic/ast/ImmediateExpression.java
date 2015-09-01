package com.grahamedgecombe.tinybasic.ast;

import com.grahamedgecombe.tinybasic.stackir.Instruction;
import com.grahamedgecombe.tinybasic.stackir.InstructionSequence;
import com.grahamedgecombe.tinybasic.stackir.Opcode;

import java.math.BigDecimal;

public final class ImmediateExpression extends Expression {

    private final BigDecimal value;

    public ImmediateExpression(BigDecimal value) {
        this.value = value;
    }
    public ImmediateExpression(int value) {
        this(new BigDecimal(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmediateExpression that = (ImmediateExpression) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.intValue();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(Opcode.PUSHI, value));
    }

}
