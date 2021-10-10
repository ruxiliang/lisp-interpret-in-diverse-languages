package org.ruxi.scheme.types

import org.ruxi.scheme.builtins.BuiltinFunctions
import org.ruxi.scheme.environment.SchemeFrame
import org.ruxi.scheme.environment.extendEnv
import org.ruxi.scheme.environment.lookUp

sealed interface SchemeType: SchemeExpr

sealed interface SchemePairType : SchemeType {
	fun evalPair(scope: SchemeFrame): SchemePairType
	override fun eval(scope: SchemeFrame): SchemeType = this.evalPair(scope)
}

sealed interface SchemeProcedure : SchemeType{
	override fun eval(scope: SchemeFrame): SchemeExpr = this
	operator fun invoke(args: List<SchemeExpr>, scope: SchemeFrame): SchemeExpr
}

data class SchemeInt(val intValue: Int) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = this
	override fun toString(): String = intValue.toString()
}

fun SchemeInt.toInt(): Int = this.intValue
fun Int.toScheme() = SchemeInt(this)
data class SchemeString(val StringValue: String) : SchemeType {
	override fun eval(scope: SchemeFrame): SchemeType = this
	override fun toString(): String {
		return StringValue
	}
}

fun SchemeString.toKtString() = this.StringValue
fun String.toScheme() = SchemeString(this)

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

fun SchemeBoolean.toBoolean() = this.BooleanValue
fun Boolean.toScheme() = SchemeBoolean(this)
fun String.toSchemeString() = SchemeString(this)
fun String.toSchemeSymbol() = SchemeSymbol(this)

data class SchemeSymbol(val tag: String) : SchemeExpr {
	override fun eval(scope: SchemeFrame): SchemeExpr = scope.lookUp(this) ?: this
	override fun toString(): String = tag
}

fun SchemeSymbol.toKtString() = this.tag

object Nil : SchemePairType {
	override fun evalPair(scope: SchemeFrame): SchemePairType = this
	override fun toString(): String = "()"
}

data class SchemePair<out T:SchemePairType>(val car: SchemeExpr, val cdr: T) : SchemePairType {
	override fun evalPair(scope: SchemeFrame): SchemePairType = SchemePair(car.eval(scope), cdr.evalPair(scope))
	override fun toString(): String = "($car,$cdr)"
}

/**
 * scheme的内建函数类
 * @property name:是内建函数的名字
 */
data class SchemeBuiltinProcedure(val name: String) : SchemeProcedure {
	// 修改了func的eval语义，func的eval只会返回自身
	// 真正调用的话需要先直接传参然后调用invoke方法
	override operator fun invoke(args: List<SchemeExpr>, scope: SchemeFrame): SchemeExpr{
		val newArgs = args.map { it.eval(scope) }
		return BuiltinFunctions(name,*newArgs.toTypedArray())
	}
	operator fun invoke(vararg args:SchemeExpr,scope: SchemeFrame):SchemeExpr = this(args.toList(),scope)
	override fun toString(): String = "#[builtin($name)]"
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
	val body: List<SchemeExpr>,
	val env: SchemeFrame
) : SchemeProcedure {
	// 修改了func的eval语义，func的eval只会返回自身
	// 真正调用的话需要先直接传参然后调用invoke方法
	override operator fun invoke(args: List<SchemeExpr>, scope: SchemeFrame): SchemeExpr{
		var res:SchemeExpr = Nil
		val newArgs = args.map { it.eval(scope) }
		val callEnv = env.extendEnv(formalArgs.zip(newArgs))
		for(expr in body){
			res = expr.eval(callEnv)
		}
		return res
	}
	operator fun invoke(vararg args:SchemeExpr,scope: SchemeFrame):SchemeExpr = this(args.toList(),scope)
	override fun toString(): String = "#[lambda($tag)]"
}