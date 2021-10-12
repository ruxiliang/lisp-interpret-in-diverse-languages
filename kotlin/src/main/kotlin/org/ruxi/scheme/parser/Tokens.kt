package org.ruxi.scheme.parser

sealed interface Tokens

data class IntToken(val value: Int):Tokens

data class StringToken(val value: String):Tokens

data class SymbolToken(val value: String):Tokens

data class BooleanToken(val value: Boolean):Tokens

object NilToken :Tokens

enum class Keywords:Tokens{
	DEFINE,
	IF,
	LAMBDA,
	LEFT_PAREN,
	RIGHT_PAREN,
	EOF,
	SINGLE_QUOTE,
}

fun Tokens.isEof():Tokens? = if(this == Keywords.EOF){null}else{this}