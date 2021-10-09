// Generated from C:/Users/OkitaSan/codebase/lisp-interpret-in-diverse-languages/kotlin/grammar\LexerRule.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LexerRule extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ADJ=1, NOUN=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ADJ", "NOUN"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADJ", "NOUN"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LexerRule(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LexerRule.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\4.\b\1\4\2\t\2\4"+
		"\3\t\3\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\25\n\2"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3-\n\3\2\2\4\3\3\5\4\3\2\2\2\64\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\3\24\3\2\2\2\5,\3\2\2\2\7\b\7y\2\2\b\t\7q\2\2\t\25\7y\2\2\n\13"+
		"\7o\2\2\13\f\7c\2\2\f\r\7p\2\2\r\25\7{\2\2\16\17\7u\2\2\17\25\7q\2\2\20"+
		"\21\7u\2\2\21\22\7w\2\2\22\23\7e\2\2\23\25\7j\2\2\24\7\3\2\2\2\24\n\3"+
		"\2\2\2\24\16\3\2\2\2\24\20\3\2\2\2\25\4\3\2\2\2\26\27\7n\2\2\27\30\7k"+
		"\2\2\30\31\7u\2\2\31-\7r\2\2\32\33\7n\2\2\33\34\7c\2\2\34\35\7p\2\2\35"+
		"\36\7i\2\2\36\37\7w\2\2\37 \7c\2\2 !\7i\2\2!-\7g\2\2\"#\7d\2\2#$\7q\2"+
		"\2$%\7q\2\2%-\7m\2\2&\'\7d\2\2\'(\7w\2\2()\7k\2\2)*\7n\2\2*-\7f\2\2+-"+
		"\7e\2\2,\26\3\2\2\2,\32\3\2\2\2,\"\3\2\2\2,&\3\2\2\2,+\3\2\2\2-\6\3\2"+
		"\2\2\5\2\24,\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}