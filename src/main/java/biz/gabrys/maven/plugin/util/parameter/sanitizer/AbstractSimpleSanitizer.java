package biz.gabrys.maven.plugin.util.parameter.sanitizer;

public abstract class AbstractSimpleSanitizer implements ValueSanitizer {

    private final boolean valid;

    protected AbstractSimpleSanitizer(final boolean valid) {
        this.valid = valid;
    }

    public boolean isValid(final Object value) {
        return valid;
    }
}
