package com.grahamedgecombe.tinybasic.ast;

import com.grahamedgecombe.tinybasic.stackir.Instruction;
import com.grahamedgecombe.tinybasic.stackir.InstructionSequence;
import com.grahamedgecombe.tinybasic.stackir.Opcode;

public final class ImmediateString extends StringExpression {

    private final String value;

    public ImmediateString(String value) {
        value = value.replaceAll("\\\\n","\n");
        value = value.replaceAll("\\\\r", "\r");
        value = value.replaceAll("\\\\'", "\'");
        value= value.replaceAll("\\\\{2}","\\\\");
        this.value=value;
        System.out.println("'"+this.value+"'");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmediateString that = (ImmediateString) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(Opcode.PUSHS, value));
    }

}
