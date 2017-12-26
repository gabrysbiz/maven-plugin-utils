package biz.gabrys.maven.plugin.util.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.Container;
import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.DebugInternalLoggerAdapter;
import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.InfoInternalLoggerAdapter;
import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.InternalLogger;
import biz.gabrys.maven.plugin.util.parameter.converter.DefaultValueToStringConverter;
import biz.gabrys.maven.plugin.util.parameter.converter.ValueToStringConverter;
import biz.gabrys.maven.plugin.util.parameter.sanitizer.AlwaysValidSanitizer;
import biz.gabrys.maven.plugin.util.parameter.sanitizer.ValueSanitizer;

public final class ParametersLogBuilderTest {

    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    public void append_defaultConverterAndDefaultSanitizer() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        builder.append(NAME, VALUE);

        assertThat(builder.parameters).containsKey(NAME);
        final Container container = builder.parameters.get(NAME);
        assertThat(container).isNotNull();
        final ValueToStringConverter converter = container.getConverter();
        assertThat(converter).isExactlyInstanceOf(DefaultValueToStringConverter.class);
        final ValueSanitizer sanitizer = container.getSanitizer();
        assertThat(sanitizer).isExactlyInstanceOf(AlwaysValidSanitizer.class);

        verify(builder).append(NAME, VALUE);
        verify(builder).append(eq(NAME), eq(VALUE), any(DefaultValueToStringConverter.class), any(AlwaysValidSanitizer.class));
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void append_customConverterAndDefaultSanitizer() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        builder.append(NAME, VALUE, converter);

        assertThat(builder.parameters).containsKey(NAME);
        final Container container = builder.parameters.get(NAME);
        assertThat(container).isNotNull();
        assertThat(container.getConverter()).isSameAs(converter);
        final ValueSanitizer sanitizer = container.getSanitizer();
        assertThat(sanitizer).isExactlyInstanceOf(AlwaysValidSanitizer.class);

        verify(builder).append(NAME, VALUE, converter);
        verify(builder).append(eq(NAME), eq(VALUE), eq(converter), any(AlwaysValidSanitizer.class));
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger, converter);
    }

    @Test
    public void append_defaultConverterAndCustomSanitizer() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, sanitizer);

        assertThat(builder.parameters).containsKey(NAME);
        final Container container = builder.parameters.get(NAME);
        assertThat(container).isNotNull();
        final ValueToStringConverter converter = container.getConverter();
        assertThat(converter).isExactlyInstanceOf(DefaultValueToStringConverter.class);
        assertThat(container.getSanitizer()).isSameAs(sanitizer);

        verify(builder).append(NAME, VALUE, sanitizer);
        verify(builder).append(eq(NAME), eq(VALUE), any(DefaultValueToStringConverter.class), eq(sanitizer));
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger, sanitizer);
    }

    @Test
    public void append_customConverterAndCustomSanitizer() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, converter, sanitizer);

        assertThat(builder.parameters).containsKey(NAME);
        final Container container = builder.parameters.get(NAME);
        assertThat(container).isNotNull();
        assertThat(container.getConverter()).isSameAs(converter);
        assertThat(container.getSanitizer()).isSameAs(sanitizer);

        verify(builder).append(NAME, VALUE, converter, sanitizer);
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger, converter, sanitizer);
    }

    @Test
    public void append_overwriteParameter() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter1 = mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer1 = mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, converter1, sanitizer1);

        final ValueToStringConverter converter2 = mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer2 = mock(ValueSanitizer.class);
        final String value2 = VALUE + "2";
        builder.append(NAME, value2, converter2, sanitizer2);

        assertThat(builder.parameters).containsKey(NAME);
        final Container container = builder.parameters.get(NAME);
        assertThat(container).isNotNull();
        assertThat(container.getConverter()).isSameAs(converter2);
        assertThat(container.getSanitizer()).isSameAs(sanitizer2);

        verify(builder).append(NAME, VALUE, converter1, sanitizer1);
        verify(builder).append(NAME, value2, converter2, sanitizer2);
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger, converter1, sanitizer1, converter2, sanitizer2);
    }

    @Test
    public void info() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        doReturn(Boolean.TRUE).when(builder).log(any(InfoInternalLoggerAdapter.class));

        builder.info();

        verify(builder).info();
        verify(builder).log(any(InfoInternalLoggerAdapter.class));
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void debug() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        doReturn(Boolean.TRUE).when(builder).log(any(DebugInternalLoggerAdapter.class));

        builder.debug();

        verify(builder).debug();
        verify(builder).log(any(DebugInternalLoggerAdapter.class));
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void log_loggerIsDisabled_doesNothing() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        final InternalLogger internalLogger = mock(InternalLogger2.class);
        when(internalLogger.isEnabled()).thenReturn(Boolean.FALSE);

        final boolean result = builder.log(internalLogger);

        assertThat(result).isFalse();
        verify(builder).log(internalLogger);
        verify(internalLogger).isEnabled();
        verifyNoMoreInteractions(builder, internalLogger);
    }

    @Test
    public void log_loggerIsEnabled_containsZeroLines_doesNothing() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        final InternalLogger internalLogger = mock(InternalLogger2.class);
        when(internalLogger.isEnabled()).thenReturn(Boolean.TRUE);
        doReturn(Collections.emptyList()).when(builder).createLines();

        final boolean result = builder.log(internalLogger);

        assertThat(result).isFalse();
        verify(builder).log(internalLogger);
        verify(internalLogger).isEnabled();
        verify(builder).createLines();
        verifyNoMoreInteractions(builder, internalLogger);
    }

    @Test
    public void log_loggerIsEnabled_containsLines_logsLines() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        final InternalLogger internalLogger = mock(InternalLogger2.class);
        when(internalLogger.isEnabled()).thenReturn(Boolean.TRUE);
        doReturn(Arrays.asList("line1", "line2")).when(builder).createLines();

        final boolean result = builder.log(internalLogger);

        assertThat(result).isTrue();
        verify(builder).log(internalLogger);
        verify(internalLogger).isEnabled();
        verify(builder).createLines();
        verify(internalLogger).log("line1");
        verify(internalLogger).log("line2");
        verifyNoMoreInteractions(builder, internalLogger);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createLines() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));
        doReturn(Arrays.asList("line1", "line2")).when(builder).createParametersLines();

        final List<String> lines = builder.createLines();

        assertThat(lines).hasSize(4);
        assertThat(lines.get(0)).isEqualTo("Job parameters:");
        assertThat(lines.get(1)).isEqualTo("line1");
        assertThat(lines.get(2)).isEqualTo("line2");
        assertThat(lines.get(3)).isEqualTo("");
        verify(builder).createLines();
        verify(builder).createParametersLines();
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParametersLines_containsZeroParameters_returnsZeroLines() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final List<String> lines = builder.createParametersLines();

        assertThat(lines).isEmpty();
        verify(builder).createParametersLines();
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParametersLines_containsTwoParameters_returnsTwoLines() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final String name1 = "name1";
        final Container container1 = mock(Container2.class);
        builder.parameters.put(name1, container1);
        doReturn("line1").when(builder).createParemeterLine(name1, container1);

        final String name2 = "name2";
        final Container container2 = mock(Container2.class);
        builder.parameters.put(name2, container2);
        doReturn("line2").when(builder).createParemeterLine(name2, container2);

        final List<String> lines = builder.createParametersLines();
        assertThat(lines).containsExactly("line1", "line2");
        verify(builder).createParametersLines();
        verify(builder).createParemeterLine(name1, container1);
        verify(builder).createParemeterLine(name2, container2);
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger, container1, container2);
    }

    @Test
    public void createParemeterLine() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final Container container = mock(Container2.class);
        doReturn("value").when(builder).createParameterValue(container);

        final String line = builder.createParemeterLine(NAME, container);

        assertThat(line).isEqualTo("      " + NAME + " = value");
        verify(builder).createParemeterLine(NAME, container);
        verify(builder).createParameterValue(container);
        verifyNoMoreInteractions(builder);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsValid() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = mock(Container2.class);
        when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        when(converter.convert(object)).thenReturn("string");
        when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        when(sanitizer.isValid(object)).thenReturn(Boolean.TRUE);
        when(container.getSanitizer()).thenReturn(sanitizer);

        final String value = builder.createParameterValue(container);

        assertThat(value).isEqualTo("string");
        verify(builder).createParameterValue(container);
        verify(container).getConverter();
        verify(container).getValue();
        verify(converter).convert(object);
        verify(container).getSanitizer();
        verify(sanitizer).isValid(object);
        verifyNoMoreInteractions(builder, converter, sanitizer);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsNotValid() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = mock(Container2.class);
        when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        when(converter.convert(object)).thenReturn("incorrect");
        when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        when(sanitizer.isValid(object)).thenReturn(Boolean.FALSE);
        final String sanitized = "sanitized";
        when(sanitizer.sanitize(object)).thenReturn(sanitized);
        when(container.getSanitizer()).thenReturn(sanitizer);

        when(converter.convert(sanitized)).thenReturn("correct");

        final String value = builder.createParameterValue(container);

        assertThat(value).isEqualTo("incorrect (calculated: correct)");
        verify(builder).createParameterValue(container);
        verify(container).getConverter();
        verify(container).getValue();
        verify(converter).convert(object);
        verify(container).getSanitizer();
        verify(sanitizer).isValid(object);
        verify(sanitizer).sanitize(object);
        verify(converter).convert(sanitized);
        verifyNoMoreInteractions(builder, converter, sanitizer);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsNotValid_sanitizedValueIsTheSameAsValue() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = mock(Container2.class);
        when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        final String incorrectValue = "incorrect";
        when(converter.convert(object)).thenReturn(incorrectValue);
        when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        when(sanitizer.isValid(object)).thenReturn(Boolean.FALSE);
        final String sanitized = "sanitized";
        when(sanitizer.sanitize(object)).thenReturn(sanitized);
        when(container.getSanitizer()).thenReturn(sanitizer);

        when(converter.convert(sanitized)).thenReturn(incorrectValue);

        final String value = builder.createParameterValue(container);

        assertThat(value).isEqualTo(incorrectValue);
        verify(builder).createParameterValue(container);
        verify(container).getConverter();
        verify(container).getValue();
        verify(converter).convert(object);
        verify(container).getSanitizer();
        verify(sanitizer).isValid(object);
        verify(sanitizer).sanitize(object);
        verify(converter).convert(sanitized);
        verifyNoMoreInteractions(builder, converter, sanitizer);
        verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsNull_sanitizedValueIsNullText() {
        final Log logger = mock(Log.class);
        final ParametersLogBuilder builder = spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = mock(Container2.class);
        when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = mock(ValueToStringConverter.class);
        when(converter.convert(object)).thenReturn(null);
        when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = mock(ValueSanitizer.class);
        when(sanitizer.isValid(object)).thenReturn(Boolean.FALSE);
        final String sanitized = "sanitized";
        when(sanitizer.sanitize(object)).thenReturn(sanitized);
        when(container.getSanitizer()).thenReturn(sanitizer);

        when(converter.convert(sanitized)).thenReturn("null");

        final String value = builder.createParameterValue(container);

        assertThat(value).isEqualTo("null");
        verify(builder).createParameterValue(container);
        verify(container).getConverter();
        verify(container).getValue();
        verify(converter).convert(object);
        verify(container).getSanitizer();
        verify(sanitizer).isValid(object);
        verify(sanitizer).sanitize(object);
        verify(converter).convert(sanitized);
        verifyNoMoreInteractions(builder, converter, sanitizer);
        verifyZeroInteractions(logger);
    }

    public static class Container2 extends Container {

        protected Container2(final Object value, final ValueToStringConverter converter, final ValueSanitizer sanitizer) {
            super(value, converter, sanitizer);
        }
    }

    public interface InternalLogger2 extends InternalLogger {

    }
}
