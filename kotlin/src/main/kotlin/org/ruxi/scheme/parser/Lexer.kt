package org.ruxi.scheme.parser

import org.ruxi.scheme.environment.SchemeException

class Lexer(val input: String){
	var cursor = 0
	private fun Char.isSymbolComponent() =
		this in """${('a'..'z').joinToString("")}${('A'..'Z').joinToString("")}!${'$'}%&*/:<=>?@^_~+-.${(0..9).joinToString("")}"""

	private fun tokenizeNumber(): Tokens {
		val res = mutableListOf<Char>()
		while (true) {
			when (input.getOrNull(cursor)) {
				'0' -> {
					cursor += 1
					return IntToken(0)
				}
				'-' -> {
					res.add(input[cursor])
					cursor += 1
					continue
				}
				in '1'..'9' -> {
					while (input.getOrNull(cursor) != null && input[cursor].isDigit()) {
						res.add(input[cursor])
						cursor += 1
					}
					return input.getOrNull(cursor)?.let {
						IntToken(res.joinToString("").toInt())
					} ?: run {
						Keywords.EOF
					}
				}
			}
		}
	}

	private inline fun tokenizeKeywords(cont: (MutableList<Char>) -> Tokens): Tokens {
		val res = mutableListOf<Char>()
		while (input.getOrNull(cursor) != null && input[cursor].isSymbolComponent()) {
			res.add(input[cursor])
			cursor += 1
		}
		return input.getOrNull(cursor)?.let {
			cont(res)
		} ?: run{
			Keywords.EOF
		}
	}

	fun nextToken(): Tokens {
		while (true) {
			when (val ch = input.getOrNull(cursor) ?: return Keywords.EOF) {
				'd' -> return tokenizeKeywords {
					when (val token = it.joinToString("")) {
						"define" -> Keywords.DEFINE
						else -> SymbolToken(token)
					}
				}
				'l' -> return tokenizeKeywords {
					when (val token = it.joinToString("")) {
						"lambda" -> Keywords.LAMBDA
						else -> SymbolToken(token)
					}
				}
				'i' -> return tokenizeKeywords {
					when (val token = it.joinToString("")) {
						"if" -> Keywords.IF
						else -> SymbolToken(token)
					}
				}
				'n' -> return tokenizeKeywords {
						when (val token = it.joinToString("")) {
							"nil" -> NilToken
							else -> SymbolToken(token)
						}
					}
				in """${('a'..'z').joinToString("")}${('A'..'Z').joinToString("")}!${'$'}%&*/:<=>?@^_~+-.${(0..9).joinToString("")}""" -> {
					val res = mutableListOf<Char>()
					while (input.getOrNull(cursor) != null && input[cursor].isSymbolComponent()) {
						res.add(input[cursor])
						cursor += 1
					}
					input.getOrNull(cursor)?.let {
						return SymbolToken(res.joinToString(""))
					} ?: run{
						return Keywords.EOF
					}
				}
				'0', '-', in '1'..'9' -> return tokenizeNumber()
				'#' -> {
					val res = mutableListOf<Char>(input[cursor])
					res.add(input.getOrNull(cursor + 1) ?: return Keywords.EOF)
					cursor += 2
					return when (res.joinToString("")) {
						"#t" -> BooleanToken(true)
						"#f" -> BooleanToken(false)
						else -> throw SchemeException("unexpected scheme token")
					}
				}
				'(' -> {
					cursor += 1
					return Keywords.LEFT_PAREN
				}
				')' -> {
					cursor += 1
					return Keywords.RIGHT_PAREN
				}
				'\'' -> {
					cursor += 1
					return Keywords.SINGLE_QUOTE
				}
				'"' -> {
					var flag = true
					val res = mutableListOf<Char>()
					while (input.getOrNull(cursor) != null) {
						res.add(input[cursor])
						if (flag && input[cursor] == '"') {
							flag = false
						} else if (!flag && input[cursor] == '"') {
							flag = true
							cursor += 1
							return StringToken(res.joinToString(""))
						}
						cursor += 1
					}
					return Keywords.EOF
				}
				else -> {
					if (ch.isWhitespace()) {
						cursor += 1
						continue
					} else {
						throw SchemeException("invalid tokens")
					}
				}
			}
		}
	}

}