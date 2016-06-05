package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultValueToStringConverter extends AbstractValueToStringConverter {

    private final Map<Class<?>, ValueToStringConverter> converters;
    private final ValueToStringConverter fallback;

    public DefaultValueToStringConverter() {
        converters = new ConcurrentHashMap<Class<?>, ValueToStringConverter>();
        converters.put(Object[].class, new ArrayToStringConverter());
        converters.put(Collection.class, new CollectionToStringConverter());
        fallback = new ObjectToStringConverter();
    }

    @Override
    public String convert2(final Object value) {
        for (final Map.Entry<Class<?>, ValueToStringConverter> entry : converters.entrySet()) {
            if (entry.getKey().isInstance(value)) {
                return entry.getValue().convert(value);
            }
        }
        return fallback.convert(value);
    }
}
