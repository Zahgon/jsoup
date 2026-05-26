package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jspecify.annotations.Nullable;
import java.util.Arrays;

/**
 * Readers the input stream into tokens.
 */
final class Tokeniser {

    // replaces null character
    static final char replacementChar = '\uFFFD';

    private static final char[] notCharRefCharsSorted = new char[] { '\t', '\n', '\r', '\f', ' ', '<', '&' };

    // Some illegal character escapes are parsed by browsers as windows-1252 instead. See issue #1034
    // https://html.spec.whatwg.org/multipage/parsing.html#numeric-character-reference-end-state
    static final int win1252ExtensionsStart = 0x80;

    static final int[] win1252Extensions = new int[] { // we could build this manually, but Windows-1252 is not a standard java charset so that could break on
    // some platforms - this table is verified with a test
    0x20AC, 0x0081, 0x201A, 0x0192, 0x201E, 0x2026, 0x2020, 0x2021, 0x02C6, 0x2030, 0x0160, 0x2039, 0x0152, 0x008D, 0x017D, 0x008F, 0x0090, 0x2018, 0x2019, 0x201C, 0x201D, 0x2022, 0x2013, 0x2014, 0x02DC, 0x2122, 0x0161, 0x203A, 0x0153, 0x009D, 0x017E, 0x0178 };

    static {
        Arrays.sort(notCharRefCharsSorted);
    }

    // html input
    private final CharacterReader reader;

    // errors found while tokenising
    private final ParseErrorList errors;

    // current tokenisation state
    private TokeniserState state = TokeniserState.Data;

    // the token we are about to emit on next read
    @Nullable
    private Token emitPending = null;

    private boolean isEmitPending = false;

    // buffers data looking for </script>
    final TokenData dataBuffer = new TokenData();

    // html or xml syntax; affects processing of xml declarations vs as bogus comments
    final Document.OutputSettings.Syntax syntax;

    final Token.StartTag startPending;

    final Token.EndTag endPending;

    // tag we are building up: start or end pending
    Token.Tag tagPending;

    final Token.Character charPending = new Token.Character();

    // doctype building up
    final Token.Doctype doctypePending = new Token.Doctype();

    // comment building up
    final Token.Comment commentPending = new Token.Comment();

    // xml decl building up
    final Token.XmlDecl xmlDeclPending;

    // the last start tag emitted, to test appropriate end tag
    @Nullable
    private String lastStartTag;

    // "</" + lastStartTag, so we can quickly check for that in RCData
    @Nullable
    private String lastStartCloseSeq;

    // reader pos at the start of markup / characters. markup updated on state transition, char on token emit.
    private int markupStartPos, charStartPos = 0;

    Tokeniser(TreeBuilder treeBuilder) {
        syntax = treeBuilder instanceof XmlTreeBuilder ? Document.OutputSettings.Syntax.xml : Document.OutputSettings.Syntax.html;
        tagPending = startPending = new Token.StartTag(treeBuilder);
        endPending = new Token.EndTag(treeBuilder);
        xmlDeclPending = new Token.XmlDecl(treeBuilder);
        this.reader = treeBuilder.reader;
        this.errors = treeBuilder.parser.getErrors();
    }

    Token read() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emit(Token token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emit(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emit(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emit(int[] codepoints) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void transition(TokeniserState newState) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void advanceTransition(TokeniserState newState) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // holder to not have to keep creating arrays
    final private int[] codepointHolder = new int[1];

    final private int[] multipointHolder = new int[2];

    /**
     * Tries to consume a character reference, and returns: null if nothing, int[1], or int[2].
     */
    int @Nullable [] consumeCharacterReference(@Nullable Character additionalAllowedCharacter, boolean inAttribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Token.Tag createTagPending(boolean start) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Token.XmlDecl createXmlDeclPending(boolean isDeclaration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emitTagPending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void createCommentPending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emitCommentPending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void createBogusCommentPending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void createDoctypePending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void emitDoctypePending() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void createTempBuffer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isAppropriateEndTagToken() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    String appropriateEndTagName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the closer sequence {@code </lastStart}
     */
    String appropriateEndTagSeq() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void error(TokeniserState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void eofError(TokeniserState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void characterReferenceError(String message, Object... args) {
        if (errors.canAddError())
            errors.add(new ParseError(reader, String.format("Invalid character reference: " + message, args)));
    }

    void error(String errorMsg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void error(String errorMsg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Utility method to consume reader and unescape entities found within.
     * @param inAttribute if the text to be unescaped is in an attribute
     * @return unescaped string from reader
     */
    String unescapeEntities(boolean inAttribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
