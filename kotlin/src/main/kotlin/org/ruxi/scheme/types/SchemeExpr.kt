package org.ruxi.scheme.types

import org.ruxi.scheme.environment.SchemeException
import org.ruxi.scheme.environment.SchemeFrame

sealed interface SchemeExpr {
	fun eval(scope: SchemeFrame): SchemeExpr
}

data class DefineExpr(val name: SchemeSymbol, val expr: SchemeExpr) : SchemeExpr {
	override fun eval(scope: SchemeFrame): SchemeExpr {
		scope.currEnv[name] = expr
		return name
	}
}

data class CallExpr(val func: SchemeExpr, val args: List<SchemeExpr>) : SchemeExpr {
	constructor(func: SchemeExpr,vararg args:SchemeExpr):this(func,args.toList())
	override fun eval(scope: SchemeFrame): SchemeExpr =
		when (val evalFunc = func.eval(scope)) {
			is SchemeProcedure -> evalFunc(args, scope)
			else -> throw SchemeException("func should be scheme procedure")
		}
}

data class IfExpr(val predicate: SchemeExpr, val consequent: SchemeExpr, val alternative: SchemeExpr) : SchemeExpr {
	override fun eval(scope: SchemeFrame): SchemeExpr =
		when (val cond = predicate.eval(scope)) {
			is SchemeBoolean -> {
				if (cond.BooleanValue) {
					consequent.eval(scope)
				} else {
					alternative.eval(scope)
				}
			}
			else -> consequent.eval(scope)
		}
}