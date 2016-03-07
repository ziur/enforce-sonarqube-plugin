package org.fundacionjala.enforce.sonarqube.apex.toolkit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.sonar.sslr.toolkit.Validators;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by ruiza_000 on 3/5/2016.
 */
public class ApexConfigurationModel extends AbstractConfigurationModel {
    private static final Logger LOG = LoggerFactory.getLogger(ApexConfigurationModel.class);

    private static final String CHARSET_PROPERTY_KEY = "sonar.sourceEncoding";
    private static final String DEFAULT_CHARSET = "UTF-8";

    @VisibleForTesting
    ConfigurationProperty charsetProperty = new ConfigurationProperty("Charset", CHARSET_PROPERTY_KEY,
            getCharsetProperty(DEFAULT_CHARSET),
            Validators.charsetValidator());

    @VisibleForTesting
    static String getCharsetProperty(String defaultValue) {
        String propertyValue = System.getProperty(CHARSET_PROPERTY_KEY);

        if (propertyValue == null) {
            LOG.info("The property \"" + CHARSET_PROPERTY_KEY + "\" is not set, using the default value \"" + defaultValue + "\".");
            propertyValue = defaultValue;
        }
        return propertyValue;
    }

    @Override
    public Charset getCharset() {
        return Charset.forName(charsetProperty.getValue());
    }

    @Override
    public List<ConfigurationProperty> getProperties() {
        return ImmutableList.of(charsetProperty);
    }

    @Override
    public Parser<Grammar> doGetParser() {
        return ApexParser.create(getConfiguration());
    }

    @Override
    public List<Tokenizer> doGetTokenizers() {
        return ImmutableList.of(
                (Tokenizer) new KeywordsTokenizer("<span class=\"k\">", "</span>", ApexKeyword.keywordValues()));
    }

    @VisibleForTesting
    ApexConfiguration getConfiguration() {
        return new ApexConfiguration(Charset.forName(charsetProperty.getValue()));
    }
}
