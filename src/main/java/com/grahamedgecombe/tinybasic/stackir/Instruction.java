package com.grahamedgecombe.tinybasic.stackir;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public final class Instruction {

    private final Opcode opcode;
    private final Optional<String> stringOperand;
    private final Optional<BigDecimal> numberOperand;

    public Instruction(Opcode opcode) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.numberOperand = Optional.empty();
    }

    public Instruction(Opcode opcode, String operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.numberOperand = Optional.empty();
    }
    public Instruction(Opcode opcode, int operand) {
        this(opcode,new BigDecimal(operand));
    }
    public Instruction(Opcode opcode, BigDecimal operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.numberOperand = Optional.of(operand);
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Optional<String> getStringOperand() {
        return stringOperand;
    }

    public Optional<BigDecimal> getNumberOperand() {
        return numberOperand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (!numberOperand.equals(that.numberOperand)) return false;
        if (opcode != that.opcode) return false;
        if (!stringOperand.equals(that.stringOperand)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(opcode, stringOperand, numberOperand);
    }

    @Override
    public String toString() {
        if (stringOperand.isPresent())
            return (opcode + " " + stringOperand.get()).trim();
        else if (numberOperand.isPresent())
            return opcode + " " + numberOperand.get();
        else
            return opcode.toString();
    }

}
