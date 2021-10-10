package org.ruxi.scheme.builtins

import org.ruxi.scheme.environment.SchemeException
import org.ruxi.scheme.types.*
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions

object BuiltinFunctions {
	private val BUILTIN_FUNCTIONS:Map<String,KFunction<SchemeExpr>> by lazy {
		val res = mutableMapOf<String,KFunction<SchemeExpr>>()
		for (memberFunction in BuiltinFunctions::class.memberFunctions) {
			memberFunction.findAnnotation<BuiltinNames>()?.let {
				for(name in it.builtinNames){
					@Suppress("UNCHECKED_CAST")
					res[name] = memberFunction as KFunction<SchemeExpr>
				}
			}
		}
		res
	}
	val builtinFunc:MutableMap<SchemeSymbol,SchemeExpr>
		get() = BUILTIN_FUNCTIONS.keys.map { it.toSchemeSymbol() to SchemeBuiltinProcedure(it) }.let {
			val res:MutableMap<SchemeSymbol,SchemeExpr> = mutableMapOf()
			for((k,v) in it){
				res[k] = v
			}
			res
		}
	operator fun invoke(ident:String,vararg args:SchemeExpr):SchemeExpr = BUILTIN_FUNCTIONS[ident]?.call(this,*args) ?: throw SchemeException("This function $ident is not a builtin function now")

	@BuiltinNames("pair?")
	fun isSchemePair(expr:SchemeType) = SchemeBoolean(expr is SchemePair<*>)

	@BuiltinNames("+")
	fun add(lhs: SchemeInt, rhs:SchemeInt) = SchemeInt(lhs.intValue + rhs.intValue)

	@BuiltinNames("*")
	fun mul(lhs: SchemeInt, rhs: SchemeInt) = SchemeInt(lhs.intValue * rhs.intValue)

	@BuiltinNames("<")
	fun lessThan(lhs: SchemeInt, rhs: SchemeInt) = (lhs.intValue < rhs.intValue).toScheme()

	@BuiltinNames("-")
	fun sub(lhs: SchemeInt, rhs: SchemeInt) = (lhs.intValue - rhs.intValue).toScheme()
}