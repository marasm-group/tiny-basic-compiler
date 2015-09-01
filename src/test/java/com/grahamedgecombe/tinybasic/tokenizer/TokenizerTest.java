package com.grahamedgecombe.tinybasic.tokenizer;

import com.grahamedgecombe.tinybasic.tokenizer.Token.Type;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public final class TokenizerTest {
    public long line=0;
    @Test
    public void testEof() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("")) {
            assertEquals(new Token(line,Type.EOF), tokenizer.nextToken());
        }
    }

    @Test
    public void testNewLine() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("\n")) {
            assertEquals(new Token(line,Type.LF), tokenizer.nextToken());
        }
    }

    @Test
    public void testString() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("\"hello\"")) {
            assertEquals(new Token(line,Type.STRING, "hello"), tokenizer.nextToken());
        }
    }

    @Test
    public void testNumber() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("123")) {
            assertEquals(new Token(line,Type.NUMBER, "123"), tokenizer.nextToken());
        }
    }

    @Test
    public void testRelationalOperators() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("= <= < <> >< > >=")) {
            assertEquals(new Token(line,Type.EQ), tokenizer.nextToken());
            assertEquals(new Token(line,Type.LTE), tokenizer.nextToken());
            assertEquals(new Token(line,Type.LT), tokenizer.nextToken());
            assertEquals(new Token(line,Type.NE), tokenizer.nextToken());
            assertEquals(new Token(line,Type.NE), tokenizer.nextToken());
            assertEquals(new Token(line,Type.GT), tokenizer.nextToken());
            assertEquals(new Token(line,Type.GTE), tokenizer.nextToken());
        }
    }

    @Test
    public void testVariable() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("G")) {
            assertEquals(new Token(line,Type.VAR, "G"), tokenizer.nextToken());
        }
    }

    @Test
    public void testKeyword() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("GOTO")) {
            assertEquals(new Token(line,Type.KEYWORD, "GOTO"), tokenizer.nextToken());
        }
    }

    @Test
    public void testArithmeticOperators() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("+ - * /")) {
            assertEquals(new Token(line,Type.PLUS), tokenizer.nextToken());
            assertEquals(new Token(line,Type.MINUS), tokenizer.nextToken());
            assertEquals(new Token(line,Type.MULT), tokenizer.nextToken());
            assertEquals(new Token(line,Type.DIV), tokenizer.nextToken());
        }
    }

    @Test
    public void testMisc() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("( ) ,")) {
            assertEquals(new Token(line,Type.LPAREN), tokenizer.nextToken());
            assertEquals(new Token(line,Type.RPAREN), tokenizer.nextToken());
            assertEquals(new Token(line,Type.COMMA), tokenizer.nextToken());
        }
    }

    @Test
    public void testWithoutWhitespace() throws IOException {
        try (Tokenizer tokenizer = new Tokenizer("PRINT13+37")) {
            assertEquals(new Token(line,Type.KEYWORD, "PRINT"), tokenizer.nextToken());
            assertEquals(new Token(line,Type.NUMBER, "13"), tokenizer.nextToken());
            assertEquals(new Token(line,Type.PLUS), tokenizer.nextToken());
            assertEquals(new Token(line,Type.NUMBER, "37"), tokenizer.nextToken());
        }
    }

}
