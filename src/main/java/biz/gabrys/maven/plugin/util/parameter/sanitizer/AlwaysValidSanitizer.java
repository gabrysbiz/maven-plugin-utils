package biz.gabrys.maven.plugin.util.parameter.sanitizer;

public class AlwaysValidSanitizer implements ValueSanitizer {

    public AlwaysValidSanitizer() {
        // do nothing
    }

    public boolean isValid(final Object value) {
        return true;
    }

    public Object sanitize(final Object value) {
        return value;
    }
}
