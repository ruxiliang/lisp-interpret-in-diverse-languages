package org.ruxi.scheme.types

import org.ruxi.scheme.builtins.BuiltinFunctions
import org.ruxi.scheme.environment.SchemeException
import org.ruxi.scheme.environment.SchemeFrame
import org.ruxi.scheme.environment.extendEnv
import org.ruxi.scheme.environment.lookUp

sealed interface SchemeType {
	fun eval(scope: SchemeFrame): SchemeType
}

sealed interface SchemePairType : SchemeType {
	fun evalPair(scope: SchemeFrame): SchemePairType
	override fun eval(scope: SchemeFrame): SchemeType = this.evalPair(scope)
}

sealed interface SchemeProcedure : SchemeType

data class SchemeInt(val intValue: Int) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = this
	override fun toString(): String = intValue.toString()
}

data class SchemeString(val StringValue: String) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = this
	override fun toString(): String {
		return StringValue
	}
}

data class SchemeBoolean(val BooleanValue: Boolean) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = this
	override fun toString(): String {
		return if (BooleanValue) {
			"#t"
		} else {
			"#f"
		}
	}
}

data class SchemeSymbol(val tag: String) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = scope.lookUp(this) ?: this
	override fun toString(): String = tag
}

object Nil : SchemePairType {
	override fun evalPair(scope: SchemeFrame): SchemePairType = this
	override fun toString(): String = "()"
}

data class SchemePair(val car: SchemeType, val cdr: SchemePairType) : SchemePairType {
	override fun evalPair(scope: SchemeFrame): SchemePairType = SchemePair(car.eval(scope), cdr.evalPair(scope))
	override fun toString(): String = "($car,$cdr)"
}

/**
 * scheme的内建函数类
 * @property name:是内建函数的名字
 * @property args:事实参的函数列表，由于是内建就不加formal args了ww
 */
data class SchemeBuiltinProcedure(val name: String) : SchemeProcedure {
	private var args:List<SchemeType>? = null
	override fun eval(scope: SchemeFrame): SchemeType = BuiltinFunctions(name, *args?.map{it.eval(scope)}?.toTypedArray()
		?: throw SchemeException("no arguments of internal procedure"))
	fun argsOf(args:List<SchemeType>):SchemeBuiltinProcedure{
		this.args = args
		return this
	}
	override fun toString(): String = "#[builtin($name)]\n"
}

/**
 * scheme的lambda函数类
 * @property tag:lambda函数的tag
 * @property formalArgs:lambda函数接受的形参，这里事形参名，和eval函数的env的值结合然后扩展当前env
 * @property body:lambda函数的函数体
 * @property env:lambda函数创建时的frame
 */
data class SchemeLambdaProcedure(
	val tag: SchemeSymbol,
	val formalArgs: List<SchemeSymbol>,
	val body: List<SchemeType>,
	val env: SchemeFrame
) : SchemeProcedure {
	private var args:List<SchemeType>? = null
	override fun eval(scope: SchemeFrame): SchemeType {
		val newEnv = env.extendEnv(formalArgs.zip(args!!.map { it.eval(scope) }).toMap())
		var res: SchemeType = Nil
		for (expr in body) {
			res = expr.eval(newEnv)
		}
		return res
	}
	fun argsOf(args:List<SchemeType>):SchemeLambdaProcedure{
		this.args = args
		return this
	}
	override fun toString(): String = "#[lambda($tag)\n]"
}