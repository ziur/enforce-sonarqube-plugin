/*
 * The MIT License
 *
 * Copyright 2016 Jalasoft.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex.lexer;

import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

/**
 * Built a {@link Lexer} instance for Apex. Required an configuration.
 */
public class ApexLexer {

    /**
     * Pattern to identify a keyword.
     */
    private static final String KEYWORD = "[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?+";

    /**
     * Patter to identify a String.
     */
    private static final String STRING = "\'([^\'\\\\]*+(\\\\[\\s\\S])?+)*+\'";

    /**
     * Pattern to identify a black hole.
     */
    private static final String BLACK_HOLE = "[ \t\r\n]+";

    /**
     * This method to create a Lexer, contains all channels to analyze apex language.
     *
     * @param conf apex configuration.
     *
     * @return a lexer instance.
     */
    public static Lexer create(ApexConfiguration conf) {
        return Lexer.builder()
                .withCharset(conf.getCharset())
                .withFailIfNoChannelToConsumeOneCharacter(Boolean.TRUE)
                .withChannel(regexp(ApexTokenType.STRING, STRING))
                .withChannel(new IdentifierAndKeywordChannel(KEYWORD,
                        Boolean.TRUE, ApexKeyword.values()))
                .withChannel(new PunctuatorChannel(ApexPunctuator.values()))
                .withChannel(new BlackHoleChannel(BLACK_HOLE))
                .build();
    }
}
