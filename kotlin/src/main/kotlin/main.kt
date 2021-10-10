import org.antlr.v4.runtime.*
import org.ruxi.scheme.builtins.BuiltinFunctions
import org.ruxi.scheme.environment.SchemeFrame
import org.ruxi.scheme.types.*
import java.io.FileInputStream

data class Foo(var dict: MutableMap<String, Int> = mutableMapOf())

fun main(args: Array<String>) {
	val initEnv = SchemeFrame(mutableMapOf("two".toSchemeSymbol() to (SchemeInt(2))), SchemeFrame(BuiltinFunctions.builtinFunc,null))
	val fib =
		DefineExpr(
			name = "fib".toSchemeSymbol(),
			expr = SchemeLambdaProcedure(
				tag = "whatever".toSchemeSymbol(),
				formalArgs = listOf("n".toSchemeSymbol()),
				body = listOf(
					IfExpr(
						predicate = CallExpr(
							func = SchemeSymbol("<"),
							args = listOf("n".toSchemeSymbol(), SchemeSymbol("two"))
						),
						consequent = SchemeSymbol("n"),
						alternative = CallExpr(
							func = SchemeSymbol("+"),
							args = listOf(
								CallExpr(
									func = SchemeSymbol("fib"),
									args = listOf(
										CallExpr(
											func = SchemeSymbol("-"),
											args = listOf(
												SchemeSymbol("n"),
												SchemeInt(2)
											)
										),
									)
								),
								CallExpr(
									func = SchemeSymbol("fib"),
									args = listOf(
										CallExpr(
											func = SchemeSymbol("-"),
											args = listOf(
												SchemeSymbol("n"),
												SchemeInt(1)
											)
										),
									)
								)
							)
						)
					)
				),
				env = initEnv
			)
		).eval(initEnv).eval(initEnv) as SchemeLambdaProcedure
	for (elem in 1..4) {
		println("$elem:${fib(listOf(elem.toScheme()), initEnv)}")
	}
}