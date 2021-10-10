import org.antlr.v4.runtime.*
import org.ruxi.scheme.builtins.BuiltinFunctions
import org.ruxi.scheme.environment.SchemeFrame
import org.ruxi.scheme.types.*
import java.io.FileInputStream

data class Foo(var dict:MutableMap<String,Int> = mutableMapOf())

fun main(args: Array<String>) {
	val test = Foo()
	val procTest = {
		println(test.dict["age"])
	}
	test.dict["age"] = 24
	procTest()
	val lambdaExpr = SchemeLambdaProcedure(
		SchemeSymbol("add-then-mul"),
		listOf(SchemeSymbol("lhs"), SchemeSymbol("rhs")),
		listOf(
			SchemeBuiltinProcedure(
				"+"
			).argsOf(
				listOf(
					SchemeBuiltinProcedure("*").argsOf(
						listOf(SchemeSymbol("lhs"),
							   SchemeSymbol("rhs")
						)
					),
					SchemeSymbol("rhs")
				)
			)
		),
		SchemeFrame(mapOf(), null)
	)
	println(
		lambdaExpr.argsOf(
			listOf(SchemeInt(20),SchemeInt(30))
		).eval(SchemeFrame(mapOf(),null))
	)
}