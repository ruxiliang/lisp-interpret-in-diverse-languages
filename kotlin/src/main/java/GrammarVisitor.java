// Generated from C:/Users/OkitaSan/codebase/lisp-interpret-in-diverse-languages/kotlin\Grammar.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#r}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR(GrammarParser.RContext ctx);
}