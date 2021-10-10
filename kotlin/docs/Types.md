# lisp解释器

说实话这个是目前自己写的第一个lisp解释器（）目前还有很多不成熟的地方，我先写一下比较成型的东西

## 内置方法

内置方法的加载这边直接偷了懒，利用反射反射出object里面的方法，用注解的参数列表注册内建方法名，同时重载了invoke操作符，从而达到如下的效果
```kotlin
// 内建方法的定义

object BuiltinFunctions{

    @BuiltinNames("+")
    fun add(...){...}

    operator fun invoke(val func:String,vararg args:List<SchemeExpr>)
}

/// 调用内建方法

BuiltinFunctions("+",1.toSchemeInt(),2.toSchemeInt())
```
但反射的话性能上肯定有损失，不过需要后续的考察来继续确认损失的程度

## 调用的Frame

结构是这样的

```kotlin
data class SchemeFrame(val currEnv:MutableMap<SchemeSymbol,SchemeType>,val parentEnv:SchemeFrame?)
```

这么定义的目的在于，形成了一个链表式的结构，能够实现动态语言当前作用域和父作用域的查找；同时由于语言要求规定对当前env的修改，因此currEnv是一个MutableMap

## 类型和表达式

TODO：表达式，更多的数值类型，以及考虑一下builtinproc和lambdaproc可否有合并的部分


表达式还没做（笑死），如果把一些special form做出来的话这个东西真的就图灵完备了。

规划大概是这样的，首先是四个sealed interface:

```kotlin
sealed interface SchemeExpr{
    fun eval(scope:SchemeScope):SchemeExpr
}
sealed interface SchemeType:SchemeExpr
sealed interface SchemePairType:SchemeType
sealed interface SchemeProcedure:SchemeType
```

首先，lisp里面所有东西都是值，包括类型也是。单独拎出来类型是考虑到后面做一系列special form的要求

然后Type也是Expr。其中目前已经做了的有Int,Double,String,Boolean,Symbol,Nil,Pair,内建过程和lambda procedure。

单独拎出来PairType是考虑到scheme对pair cdr的要求是Nil | Pair，所以这么限定一下

目前各type对eval的实现如下：

Int,String,Boolean，Nil都是求值到自身

Symbol的求值是，查找当前env中有无这个symbol,有就返回值，没有就返回当前Symbol

Pair的求值是先求car，再递归求cdr

考虑到lisp中函数时first-value，因此对函数的求值策略实现如下:

1. TODO： proc的args的field如果为空，则求值为自身

2. 如果args不为空，那么构造如下的作用域：
   1. 如果是builtin，那么直接把当前作用域传入即可
   2. 如果是lambda,那么把formalArgs和args压在一起后新建一个作用域，其父亲为eval参数传入的作用域，其中args是均求值完毕以后的版本
3. 对表达式进行求值
   1. builtin的args要全部求值完毕再调用

考虑到对递归函数，body可以改用成一个`()->List<SchemeType>`的闭包表示，实现自己调用自己的目的


### 递归函数

想了一下，还是define构建然后直接在当前env查找强转得了。

还有一点就是，lambda procedure构建的时候env是当前求值的作用域

## TODOS

1. scheme和kt内建类之间的cast
2. ir的dsl表示
3. 内部tag的表示不统一，考虑更改
4. parser!! 不写这个算个屁的解释器