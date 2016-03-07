package org.fundacionjala.enforce.sonarqube.apex.toolkit;

import com.google.common.base.Charsets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class ApexConfigurationModelTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getConfiguration_charset() {
        ApexConfigurationModel model = new ApexConfigurationModel();
        model.charsetProperty.setValue("UTF-8");
        assertThat(model.getCharset()).isEqualTo(Charsets.UTF_8);
        assertThat(model.getConfiguration().getCharset()).isEqualTo(Charsets.UTF_8);
        model.charsetProperty.setValue("ISO-8859-1");
        assertThat(model.getCharset()).isEqualTo(Charsets.ISO_8859_1);
        assertThat(model.getConfiguration().getCharset()).isEqualTo(Charsets.ISO_8859_1);
    }


}
