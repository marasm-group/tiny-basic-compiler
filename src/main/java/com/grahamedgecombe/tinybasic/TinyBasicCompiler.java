package com.grahamedgecombe.tinybasic;

import com.grahamedgecombe.tinybasic.codegen.CodeGenerator;
import com.grahamedgecombe.tinybasic.parser.Parser;
import com.grahamedgecombe.tinybasic.tokenizer.Tokenizer;
import com.marasm.codegen.marasm.MarasmCodeGenerator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TinyBasicCompiler {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);
        System.out.println("Compiling "+inputPath+" to "+outputPath);
        try (Tokenizer tokenizer = new Tokenizer(Files.newBufferedReader(inputPath, StandardCharsets.UTF_8))) {
            try (Parser parser = new Parser(tokenizer)) {
                try (CodeGenerator generator = new MarasmCodeGenerator(Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8))) {
                    generator.generate(parser.parse().compile());
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Compilation failed!");
            System.out.println(e.getLocalizedMessage() + " in file " + inputPath);
            System.exit(-1);
        }
        System.out.println("Compilation success!");
    }

}
