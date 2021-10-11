package org.ruxi.scheme.environment

import org.ruxi.scheme.types.SchemeExpr
import org.ruxi.scheme.types.SchemeSymbol
import org.ruxi.scheme.types.SchemeType


data class SchemeFrame(val currEnv:MutableMap<SchemeSymbol,SchemeExpr>,val parentEnv:SchemeFrame?)

fun SchemeFrame.extendEnv(curr:MutableMap<SchemeSymbol,SchemeExpr>):SchemeFrame = SchemeFrame(curr,this)
fun SchemeFrame.extendEnv(curr:List<Pair<SchemeSymbol,SchemeExpr>>):SchemeFrame{
	val res:MutableMap<SchemeSymbol,SchemeExpr> = mutableMapOf()
	for((k,v) in curr){
		res[k] = v
	}
	return this.extendEnv(res)
}
fun SchemeFrame.lookUp(item:SchemeSymbol):SchemeExpr?{
	var (envHandle,frameHandle) = this
	if(envHandle[item] != null){
		return envHandle[item]
	}else{
		while (frameHandle != null) {
			envHandle = frameHandle.component1()
			frameHandle = frameHandle.component2()
			envHandle[item]?.let { return it } ?: continue
		}
	}
	return null
}
