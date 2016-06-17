package biz.gabrys.maven.plugin.util.parameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

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
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        builder.append(NAME, VALUE);

        Assert.assertTrue("Parameters should store key.", builder.parameters.containsKey(NAME));
        final Container container = builder.parameters.get(NAME);
        Assert.assertNotNull("Parameters should store container.", container);
        final ValueToStringConverter converter = container.getConverter();
        Assert.assertNotNull("Converter instance.", converter);
        Assert.assertEquals("Converter class.", DefaultValueToStringConverter.class, converter.getClass());
        final ValueSanitizer sanitizer = container.getSanitizer();
        Assert.assertNotNull("Sanitizer instance.", sanitizer);
        Assert.assertEquals("Sanitizer class.", AlwaysValidSanitizer.class, sanitizer.getClass());

        Mockito.verify(builder).append(NAME, VALUE);
        Mockito.verify(builder).append(Matchers.anyString(), Matchers.anyString(), Matchers.any(DefaultValueToStringConverter.class),
                Matchers.any(AlwaysValidSanitizer.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void append_customConverterAndDefaultSanitizer() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter = Mockito.mock(ValueToStringConverter.class);
        builder.append(NAME, VALUE, converter);

        Assert.assertTrue("Parameters should store key.", builder.parameters.containsKey(NAME));
        final Container container = builder.parameters.get(NAME);
        Assert.assertNotNull("Parameters should store container.", container);
        Assert.assertEquals("Custom converter instance.", converter, container.getConverter());
        final ValueSanitizer sanitizer = container.getSanitizer();
        Assert.assertNotNull("Sanitizer instance.", sanitizer);
        Assert.assertEquals("Sanitizer class.", AlwaysValidSanitizer.class, sanitizer.getClass());

        Mockito.verify(builder).append(NAME, VALUE, converter);
        Mockito.verify(builder).append(Matchers.anyString(), Matchers.anyString(), Matchers.any(DefaultValueToStringConverter.class),
                Matchers.any(AlwaysValidSanitizer.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger, converter);
    }

    @Test
    public void append_defaultConverterAndCustomSanitizer() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final ValueSanitizer sanitizer = Mockito.mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, sanitizer);

        Assert.assertTrue("Parameters should store key.", builder.parameters.containsKey(NAME));
        final Container container = builder.parameters.get(NAME);
        Assert.assertNotNull("Parameters should store container.", container);
        final ValueToStringConverter converter = container.getConverter();
        Assert.assertNotNull("Converter instance.", converter);
        Assert.assertEquals("Converter class.", DefaultValueToStringConverter.class, converter.getClass());
        Assert.assertEquals("Custom sanitizer instance.", sanitizer, container.getSanitizer());

        Mockito.verify(builder).append(NAME, VALUE, sanitizer);
        Mockito.verify(builder).append(Matchers.anyString(), Matchers.anyString(), Matchers.any(DefaultValueToStringConverter.class),
                Matchers.any(AlwaysValidSanitizer.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger, sanitizer);
    }

    @Test
    public void append_customConverterAndCustomSanitizer() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter = Mockito.mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer = Mockito.mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, converter, sanitizer);

        Assert.assertTrue("Parameters should store key.", builder.parameters.containsKey(NAME));
        final Container container = builder.parameters.get(NAME);
        Assert.assertNotNull("Parameters should store container.", container);
        Assert.assertEquals("Custom converter instance.", converter, container.getConverter());
        Assert.assertEquals("Custom sanitizer instance.", sanitizer, container.getSanitizer());

        Mockito.verify(builder).append(NAME, VALUE, converter, sanitizer);
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger, converter, sanitizer);
    }

    @Test
    public void append_overwriteParameter() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final ValueToStringConverter converter1 = Mockito.mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer1 = Mockito.mock(ValueSanitizer.class);
        builder.append(NAME, VALUE, converter1, sanitizer1);

        final ValueToStringConverter converter2 = Mockito.mock(ValueToStringConverter.class);
        final ValueSanitizer sanitizer2 = Mockito.mock(ValueSanitizer.class);
        final String value2 = VALUE + "2";
        builder.append(NAME, value2, converter2, sanitizer2);

        Assert.assertTrue("Parameters should store key.", builder.parameters.containsKey(NAME));
        final Container container = builder.parameters.get(NAME);
        Assert.assertNotNull("Parameters should store container.", container);
        Assert.assertEquals("Custom converter instance.", converter2, container.getConverter());
        Assert.assertEquals("Custom sanitizer instance.", sanitizer2, container.getSanitizer());

        Mockito.verify(builder).append(NAME, VALUE, converter1, sanitizer1);
        Mockito.verify(builder).append(NAME, value2, converter2, sanitizer2);
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger, converter1, sanitizer1, converter2, sanitizer2);
    }

    @Test
    public void info() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        Mockito.doReturn(Boolean.TRUE).when(builder).log(Matchers.any(InfoInternalLoggerAdapter.class));

        builder.info();
        Mockito.verify(builder).info();
        Mockito.verify(builder).log(Matchers.any(InfoInternalLoggerAdapter.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void debug() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        Mockito.doReturn(Boolean.TRUE).when(builder).log(Matchers.any(DebugInternalLoggerAdapter.class));

        builder.debug();
        Mockito.verify(builder).debug();
        Mockito.verify(builder).log(Matchers.any(DebugInternalLoggerAdapter.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void log_loggerIsDisabled_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final InternalLogger internalLogger = Mockito.mock(InternalLogger.class);
        Mockito.when(internalLogger.isEnabled()).thenReturn(Boolean.FALSE);

        final boolean result = builder.log(internalLogger);

        Assert.assertFalse("Should return false.", result);
        Mockito.verify(builder).log(internalLogger);
        Mockito.verify(internalLogger).isEnabled();
        Mockito.verifyNoMoreInteractions(builder, internalLogger);
    }

    @Test
    public void log_loggerIsEnabled_containsZeroLines_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final InternalLogger internalLogger = Mockito.mock(InternalLogger.class);
        Mockito.when(internalLogger.isEnabled()).thenReturn(Boolean.TRUE);

        Mockito.doReturn(Collections.emptyList()).when(builder).createLines();

        final boolean result = builder.log(internalLogger);

        Assert.assertFalse("Should return false.", result);
        Mockito.verify(builder).log(internalLogger);
        Mockito.verify(internalLogger).isEnabled();
        Mockito.verify(builder).createLines();
        Mockito.verifyNoMoreInteractions(builder, internalLogger);
    }

    @Test
    public void log_loggerIsEnabled_containsLines_logsLines() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final InternalLogger internalLogger = Mockito.mock(InternalLogger.class);
        Mockito.when(internalLogger.isEnabled()).thenReturn(Boolean.TRUE);

        Mockito.doReturn(Arrays.asList("line1", "line2")).when(builder).createLines();

        final boolean result = builder.log(internalLogger);

        Assert.assertTrue("Should return true.", result);
        Mockito.verify(builder).log(internalLogger);
        Mockito.verify(internalLogger).isEnabled();
        Mockito.verify(builder).createLines();
        Mockito.verify(internalLogger).log("line1");
        Mockito.verify(internalLogger).log("line2");
        Mockito.verifyNoMoreInteractions(builder, internalLogger);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void createLines() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        Mockito.doReturn(Arrays.asList("line1", "line2")).when(builder).createParametersLines();

        final List<String> lines = builder.createLines();

        Assert.assertEquals("Lines size.", 4, lines.size());
        Assert.assertEquals("Line 1.", "Job parameters:", lines.get(0));
        Assert.assertEquals("Line 2.", "line1", lines.get(1));
        Assert.assertEquals("Line 3.", "line2", lines.get(2));
        Assert.assertEquals("Line 4.", "", lines.get(3));
        Mockito.verify(builder).createLines();
        Mockito.verify(builder).createParametersLines();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void createParametersLines_containsZeroParameters_returnsZeroLines() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final List<String> lines = builder.createParametersLines();
        Assert.assertTrue("Lines should be empty.", lines.isEmpty());
        Mockito.verify(builder).createParametersLines();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void createParametersLines_containsTwoParameters_returnsTwoLines() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final String name1 = "name1";
        final Container container1 = Mockito.mock(Container.class);
        builder.parameters.put(name1, container1);
        Mockito.doReturn("line1").when(builder).createParemeterLine(name1, container1);

        final String name2 = "name2";
        final Container container2 = Mockito.mock(Container.class);
        builder.parameters.put(name2, container2);
        Mockito.doReturn("line2").when(builder).createParemeterLine(name2, container2);

        final List<String> lines = builder.createParametersLines();
        Assert.assertEquals("Lines size.", 2, lines.size());
        Assert.assertEquals("Line 1.", "line1", lines.get(0));
        Assert.assertEquals("Line 2.", "line2", lines.get(1));
        Mockito.verify(builder).createParametersLines();
        Mockito.verify(builder).createParemeterLine(name1, container1);
        Mockito.verify(builder).createParemeterLine(name2, container2);
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger, container1, container2);
    }

    @Test
    public void createParemeterLine() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final Container container = Mockito.mock(Container.class);
        Mockito.doReturn("value").when(builder).createParameterValue(container);

        final String line = builder.createParemeterLine(NAME, container);

        Assert.assertEquals("Line.", "      " + NAME + " = value", line);
        Mockito.verify(builder).createParemeterLine(NAME, container);
        Mockito.verify(builder).createParameterValue(container);
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsValid() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = Mockito.mock(Container.class);
        Mockito.when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = Mockito.mock(ValueToStringConverter.class);
        Mockito.when(converter.convert(object)).thenReturn("string");
        Mockito.when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = Mockito.mock(ValueSanitizer.class);
        Mockito.when(sanitizer.isValid(object)).thenReturn(Boolean.TRUE);
        Mockito.when(container.getSanitizer()).thenReturn(sanitizer);

        final String value = builder.createParameterValue(container);

        Assert.assertEquals("Value.", "string", value);
        Mockito.verify(builder).createParameterValue(container);
        Mockito.verify(container).getConverter();
        Mockito.verify(container).getValue();
        Mockito.verify(converter).convert(object);
        Mockito.verify(container).getSanitizer();
        Mockito.verify(sanitizer).isValid(object);
        Mockito.verifyNoMoreInteractions(builder, converter, sanitizer);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void createParameterValue_valueIsNotValid() {
        final Log logger = Mockito.mock(Log.class);
        final ParametersLogBuilder builder = Mockito.spy(new ParametersLogBuilder(logger));

        final String object = "object";

        final Container container = Mockito.mock(Container.class);
        Mockito.when(container.getValue()).thenReturn(object);

        final ValueToStringConverter converter = Mockito.mock(ValueToStringConverter.class);
        Mockito.when(converter.convert(object)).thenReturn("incorrect");
        Mockito.when(container.getConverter()).thenReturn(converter);

        final ValueSanitizer sanitizer = Mockito.mock(ValueSanitizer.class);
        Mockito.when(sanitizer.isValid(object)).thenReturn(Boolean.FALSE);
        final String sanitized = "sanitized";
        Mockito.when(sanitizer.sanitize(object)).thenReturn(sanitized);
        Mockito.when(container.getSanitizer()).thenReturn(sanitizer);

        Mockito.when(converter.convert(sanitized)).thenReturn("correct");

        final String value = builder.createParameterValue(container);

        Assert.assertEquals("Value.", "incorrect (calculated: correct)", value);
        Mockito.verify(builder).createParameterValue(container);
        Mockito.verify(container).getConverter();
        Mockito.verify(container).getValue();
        Mockito.verify(converter).convert(object);
        Mockito.verify(container).getSanitizer();
        Mockito.verify(sanitizer).isValid(object);
        Mockito.verify(sanitizer).sanitize(object);
        Mockito.verify(converter).convert(sanitized);
        Mockito.verifyNoMoreInteractions(builder, converter, sanitizer);
        Mockito.verifyZeroInteractions(logger);
    }
}
