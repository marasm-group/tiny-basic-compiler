package com.marasm.codegen.marasm;

/**
 * Created by sr3u on 31.08.15.
 */

import com.grahamedgecombe.tinybasic.codegen.CodeGenerator;
import com.grahamedgecombe.tinybasic.stackir.Instruction;
import com.grahamedgecombe.tinybasic.stackir.InstructionSequence;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class MarasmCodeGenerator extends CodeGenerator {

    private final Writer writer;
    private final String tagPrefix="tinybasic_";
    private final String funPrefix="tinybasic_";
    public MarasmCodeGenerator(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void generate(InstructionSequence seq) throws IOException {
        writer.append("#json\n");
        writer.append("{\n");
        writer.append("\"author\":\"tinyBASIC\",\n");
        writer.append("\"dependencies\":[\"conio\"],\n");
        writer.append("\"init\":\"$__tinybasic_init\"\n");
        writer.append("}\n");
        writer.append("#end\n");
        Map<String, String> strings = new HashMap<>();
        for (Instruction instruction : seq.getInstructions()) {
            writer.append("; "+instruction.toString()+"\n");
            switch (instruction.getOpcode()) {
                case LABEL:
                    writer.append("@"+tagPrefix+instruction.getStringOperand().get() + "\n");
                    writer.append("$"+funPrefix+instruction.getStringOperand().get() + "\n");
                    break;

                case PUSHI:
                    writer.append("push "+instruction.getNumberOperand().get().toString()+"\n");
                    break;

                case PUSHS:
                    writer.append("push 0\n");
                    String str= instruction.getStringOperand().get();
                    char[] chrs=str.toString().toCharArray();
                    for(int i=chrs.length-1;i>=0;i--)
                    {
                        writer.append("push '"+charStr(chrs[i])+"'\n");
                    }
                    break;

                case LOAD:
                    writer.append("push "+instruction.getStringOperand().get()+"\n");
                    break;

                case STORE:
                    allocVar(instruction.getStringOperand().get());
                    writer.append("pop "+instruction.getStringOperand().get()+"\n");
                    break;

                case ADD:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("add __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("push __tinybasic_Result\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case SUB:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("push __tinybasic_Result\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case MUL:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("mul __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("push __tinybasic_Result\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case DIV:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("div __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("push __tinybasic_Result\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case CALL:
                    writer.append("call "+"$"+funPrefix+instruction.getStringOperand().get()+"\n");
                    break;

                case RET:
                    writer.append("ret\n");
                    break;

                case JMP:
                    writer.append("jmp @"+tagPrefix+instruction.getStringOperand().get() + "\n");
                    break;

                case JMPGT:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jlz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case JMPGTE:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jlz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    writer.append("jz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case JMPLT:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jmz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case JMPLTE:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jmz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    writer.append("jz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case JMPEQ:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case JMPNE:
                    allocVar("__tinybasic_Result");
                    allocVar("__tinybasic_Op1");
                    allocVar("__tinybasic_Op2");
                    writer.append("pop __tinybasic_Op1\n");
                    writer.append("pop __tinybasic_Op2\n");
                    writer.append("sub __tinybasic_Result __tinybasic_Op1 __tinybasic_Op2\n");
                    writer.append("jnz "+"__tinybasic_Result @"+tagPrefix+instruction.getStringOperand().get()+"\n");
                    deallocVar("__tinybasic_Result");
                    deallocVar("__tinybasic_Op1");
                    deallocVar("__tinybasic_Op2");
                    break;

                case HLT:
                    writer.append("halt 0\n");
                    break;

                case IN:
                    writer.append("call $__tinybasic_readnum\n");
                    break;

                case OUTS:
                    writer.append("call $__tinybasic_printstr\n");
                    break;

                case OUTI:
                    writer.append("call $__tinybasic_printnum\n");
                    break;
                case ASM:
                    writer.append(instruction.getStringOperand().get()+"\n");
                    break;
            }
        }
        writer.append("halt 0 ; prevent further execution\n");
        writer.append("; tinyBASIC stuff\n");
        writer.append("$__tinybasic_init ;allocate all variables used\n");
        for(String var : allocatedVars)
        {
            writer.append("gvar "+var+"\n");
        }
        writer.append("ret\n");
        writer.append("$__tinybasic_printstr\ncall $debIO_printStackStr\nret\n");
        writer.append("$__tinybasic_printnum\ncall $debIO_printStackNum\nret\n");
        writer.append("$__tinybasic_readnum\ncall $debIO_readStackNum\nret\n");
        writer.append("; END of tinyBASIC generated code\n");
    }

    private String escape(String value) {
        value = value.replace("\n", "\", 10, \"");
        return value;
    }
    ArrayList<String> allocatedVars=new ArrayList<>();
    private void allocVar(String var) throws IOException {
        if(allocatedVars.contains(var)){return;}
        allocatedVars.add(var);
        //writer.append("var " + var + "\n");
    }
    private void deallocVar(String var) throws IOException {
        //if(!allocatedVars.contains(var)){return;}
        //allocatedVars.add(var);
        //writer.append("delv " + var + "\n");
    }
    private int varIndex(Instruction instruction) {
        return (instruction.getStringOperand().get().charAt(0) - 'A') * 8;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    private String charStr(char chr)
    {
        switch (chr)
        {
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            case '\\':
                return "\\\\";
            default:
                return String.valueOf(chr);
        }
    }

}

