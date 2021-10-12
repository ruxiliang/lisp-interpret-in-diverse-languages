package org.ruxi.scheme.parser

import org.junit.Test
import kotlin.test.assertEquals


internal class LexerTest {
	var lexer = Lexer("")
	@Test
	fun nextToken(){
		lexer = Lexer("(define a 2)")
		assertEquals(Keywords.LEFT_PAREN,lexer.nextToken())
		assertEquals(Keywords.DEFINE,lexer.nextToken())
		assertEquals(SymbolToken("a"),lexer.nextToken())
		assertEquals(IntToken(2),lexer.nextToken())
		assertEquals(Keywords.RIGHT_PAREN,lexer.nextToken())
		assertEquals(Keywords.EOF,lexer.nextToken())
	}

}