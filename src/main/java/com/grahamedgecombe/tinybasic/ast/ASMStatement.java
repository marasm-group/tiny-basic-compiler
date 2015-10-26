package com.grahamedgecombe.tinybasic.ast;

import com.grahamedgecombe.tinybasic.stackir.Instruction;
import com.grahamedgecombe.tinybasic.stackir.InstructionSequence;
import com.grahamedgecombe.tinybasic.stackir.Opcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by vhq473 on 26.10.15.
 */
public class ASMStatement extends Statement
{

    private final List<String> asm;

    public ASMStatement(String... code) {
        this.asm = Collections.unmodifiableList(Arrays.asList(code));
    }

    public ASMStatement(List<String> code) {
        this.asm = Collections.unmodifiableList(new ArrayList<>(code));
    }
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("ASM ");
        for (int i = 0; i < asm.size(); i++) {
            buf.append(asm.get(i));
            if (i != (asm.size() - 1))
                buf.append(", ");
        }
        return buf.toString();
    }

    @Override
    public void compile(InstructionSequence seq) {
        for ( String cmd : asm) {
            seq.append(new Instruction(Opcode.ASM,cmd));
        }
    }
}
