package biz.gabrys.maven.plugin.util.parameter.sanitizer;

public class SimpleSanitizer extends AbstractSimpleSanitizer {

    private final Object sanitizedValue;

    public SimpleSanitizer(final boolean valid, final Object sanitizedValue) {
        super(valid);
        this.sanitizedValue = sanitizedValue;
    }

    public Object sanitize(final Object value) {
        return sanitizedValue;
    }
}
