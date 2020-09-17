package biz.gabrys.maven.plugin.util.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.Test;

public final class ParameterUtilsTest {

    @Test
    public void verifyNotNull_parameterValueIsNotNull_doNothing() {
        ParameterUtils.verifyNotNull(null, "value");
        ParameterUtils.verifyNotNull(null, Boolean.FALSE);
        ParameterUtils.verifyNotNull(null, new ArrayList<String>());
        ParameterUtils.verifyNotNull("name", "value");
        ParameterUtils.verifyNotNull("name", Boolean.FALSE);
        ParameterUtils.verifyNotNull("name", new ArrayList<String>());
    }

    @Test
    public void verifyNotNull_parameterNameAndValueAreNull_throwException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ParameterUtils.verifyNotNull(null, null);
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter is null");
    }

    @Test
    public void verifyNotNull_parameterNameIsNotNullAndValueIsNull_throwException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ParameterUtils.verifyNotNull("name", null);
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter \"name\" is null");
    }
}
