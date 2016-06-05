package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Arrays;
import java.util.Collection;

public class CollectionToStringConverter extends AbstractValueToStringConverter {

    @Override
    public String convert2(final Object value) {
        final Collection<?> collection = (Collection<?>) value;
        return Arrays.toString(collection.toArray());
    }
}
