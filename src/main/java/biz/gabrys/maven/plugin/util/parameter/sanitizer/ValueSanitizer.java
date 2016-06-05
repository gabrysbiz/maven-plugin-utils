package biz.gabrys.maven.plugin.util.parameter.sanitizer;

public interface ValueSanitizer {

    boolean isValid(Object value);

    Object sanitize(Object value);
}
