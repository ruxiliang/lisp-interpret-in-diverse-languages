import org.antlr.v4.runtime.*
import org.ruxi.scheme.builtins.BuiltinFunctions
import org.ruxi.scheme.environment.SchemeFrame
import org.ruxi.scheme.types.*
import java.io.FileInputStream


fun main(args: Array<String>) {
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