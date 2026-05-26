package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.DocumentType;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.xml;

/**
 * States and transition activations for the Tokeniser.
 */
enum TokeniserState {

    Data {

        // in data state, gather characters until a character reference or tag is found
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CharacterReferenceInData {

        // from & in data
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    // Rcdata has text with character references
    Rcdata {

        /// handles data in title, textarea etc
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CharacterReferenceInRcdata {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    Rawtext {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptData {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    PLAINTEXT {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    TagOpen {

        // from < in data
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    EndTagOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    TagName {

        // from < or </ in data, will have start or end tag pending
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    RcdataLessthanSign {

        // from < in rcdata
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    RCDATAEndTagOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    RCDATAEndTagName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void anythingElse(Tokeniser t, CharacterReader r) {
            t.emit("</");
            t.emit(t.dataBuffer.value());
            r.unconsume();
            t.transition(Rcdata);
        }
    }
    ,
    RawtextLessthanSign {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    RawtextEndTagOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    RawtextEndTagName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataLessthanSign {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEndTagOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEndTagName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapeStart {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapeStartDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscaped {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapedDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapedDashDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapedLessthanSign {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapedEndTagOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataEscapedEndTagName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscapeStart {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscaped {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscapedDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscapedDashDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscapedLessthanSign {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ScriptDataDoubleEscapeEnd {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeAttributeName {

        // from tagname <xxx
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AttributeName {

        // from before attribute name
        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterAttributeName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeAttributeValue {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AttributeValue_doubleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AttributeValue_singleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AttributeValue_unquoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    // CharacterReferenceInAttributeValue state handled inline
    AfterAttributeValue_quoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    SelfClosingStartTag {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BogusComment {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    // from <!
    MarkupDeclarationOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    // From <? in syntax XML
    MarkupProcessingOpen {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CommentStart {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CommentStartDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    Comment {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CommentEndDash {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CommentEnd {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CommentEndBang {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    Doctype {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeDoctypeName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypeName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterDoctypeName {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterDoctypePublicKeyword {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeDoctypePublicIdentifier {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypePublicIdentifier_doubleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypePublicIdentifier_singleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterDoctypePublicIdentifier {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BetweenDoctypePublicAndSystemIdentifiers {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterDoctypeSystemKeyword {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeDoctypeSystemIdentifier {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypeSystemIdentifier_doubleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypeSystemIdentifier_singleQuoted {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterDoctypeSystemIdentifier {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BogusDoctype {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    DoctypeInternalSubset {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    CdataSection {

        @Override
        void read(Tokeniser t, CharacterReader r) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ;

    abstract void read(Tokeniser t, CharacterReader r);

    static final char nullChar = '\u0000';

    // char searches. must be sorted, used in inSorted. MUST update TokeniserStateTest if more arrays are added.
    static final char[] attributeNameCharsSorted = new char[] { '\t', '\n', '\f', '\r', ' ', '"', '\'', '/', '<', '=', '>', '?' };

    static final char[] attributeValueUnquoted = new char[] { nullChar, '\t', '\n', '\f', '\r', ' ', '"', '&', '\'', '<', '=', '>', '`' };

    private static final char replacementChar = Tokeniser.replacementChar;

    private static final String replacementStr = String.valueOf(Tokeniser.replacementChar);

    private static final char eof = CharacterReader.EOF;

    /**
     * Handles RawtextEndTagName, ScriptDataEndTagName, and ScriptDataEscapedEndTagName. Same body impl, just
     * different else exit transitions.
     */
    private static void handleDataEndTag(Tokeniser t, CharacterReader r, TokeniserState elseTransition) {
        if (r.matchesAsciiAlpha()) {
            String name = r.consumeTagName();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }
        boolean needsExitTransition = false;
        if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            char c = r.consume();
            switch(c) {
                case '\t':
                case '\n':
                case '\r':
                case '\f':
                case ' ':
                    t.transition(BeforeAttributeName);
                    break;
                case '/':
                    t.transition(SelfClosingStartTag);
                    break;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    break;
                default:
                    t.dataBuffer.append(c);
                    needsExitTransition = true;
            }
        } else {
            needsExitTransition = true;
        }
        if (needsExitTransition) {
            t.emit("</");
            t.emit(t.dataBuffer.value());
            t.transition(elseTransition);
        }
    }

    private static void readRawData(Tokeniser t, CharacterReader r, TokeniserState current, TokeniserState advance) {
        switch(r.current()) {
            case '<':
                t.advanceTransition(advance);
                break;
            case nullChar:
                t.error(current);
                r.advance();
                t.emit(replacementChar);
                break;
            case eof:
                t.emit(new Token.EOF());
                break;
            default:
                String data = r.consumeRawData();
                t.emit(data);
                break;
        }
    }

    private static void readCharRef(Tokeniser t, TokeniserState advance) {
        int[] c = t.consumeCharacterReference(null, false);
        if (c == null)
            t.emit('&');
        else
            t.emit(c);
        t.transition(advance);
    }

    private static void readEndTag(Tokeniser t, CharacterReader r, TokeniserState a, TokeniserState b) {
        if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.transition(a);
        } else {
            t.emit("</");
            t.transition(b);
        }
    }

    private static void handleDataDoubleEscapeTag(Tokeniser t, CharacterReader r, TokeniserState primary, TokeniserState fallback) {
        if (r.matchesAsciiAlpha()) {
            String name = r.consumeLetterSequence();
            t.dataBuffer.append(name);
            t.emit(name);
            return;
        }
        char c = r.consume();
        switch(c) {
            case '\t':
            case '\n':
            case '\r':
            case '\f':
            case ' ':
            case '/':
            case '>':
                if (t.dataBuffer.value().equals("script"))
                    t.transition(primary);
                else
                    t.transition(fallback);
                t.emit(c);
                break;
            default:
                r.unconsume();
                t.transition(fallback);
        }
    }

    /**
     *     Reads an XML doctype internal subset as opaque text so it can be re-emitted without parsing declarations.
     *     Only used when in XML mode; HTML spec will drop these as Bogus.
     */
    private static void readDoctypeInternalSubset(Tokeniser t, CharacterReader r, TokeniserState current) {
        final byte None = 0, SingleQuote = 1, DoubleQuote = 2, Comment = 3, ProcessingInstruction = 4;
        byte context = None;
        TokenData subset = t.doctypePending.internalSubset;
        while (true) {
            char c = r.consume();
            switch(c) {
                case '\'':
                    subset.append(c);
                    if (context == None)
                        context = SingleQuote;
                    else if (context == SingleQuote)
                        context = None;
                    break;
                case '"':
                    subset.append(c);
                    if (context == None)
                        context = DoubleQuote;
                    else if (context == DoubleQuote)
                        context = None;
                    break;
                case '<':
                    subset.append(c);
                    if (context == None) {
                        if (r.matchConsume("!--")) {
                            subset.append("!--");
                            context = Comment;
                        } else if (r.matchConsume("?")) {
                            subset.append('?');
                            context = ProcessingInstruction;
                        }
                    }
                    break;
                case '-':
                    subset.append(c);
                    if (context == Comment && r.matchConsume("->")) {
                        subset.append("->");
                        context = None;
                    }
                    break;
                case '?':
                    subset.append(c);
                    if (context == ProcessingInstruction && r.matches('>')) {
                        r.advance();
                        subset.append('>');
                        context = None;
                    }
                    break;
                case ']':
                    if (context == None) {
                        String ws = r.consumeMatching(StringUtil::isWhitespace);
                        if (r.matches('>')) {
                            r.advance();
                            t.emitDoctypePending();
                            t.transition(Data);
                            return;
                        }
                        subset.append(c);
                        subset.append(ws);
                        break;
                    }
                    subset.append(c);
                    break;
                case nullChar:
                    t.error(current);
                    subset.append(replacementChar);
                    break;
                case eof:
                    t.eofError(current);
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    subset.append(c);
                    break;
            }
        }
    }
}
