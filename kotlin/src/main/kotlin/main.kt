import org.antlr.v4.runtime.*


fun main() {
	val input = CharStreams.fromString("hello senpai")
	val lexer = GrammarLexer(input)
	val parser = GrammarParser(CommonTokenStream(lexer))
	println(parser.tokenStream)
	println(parser.r().toStringTree())
}