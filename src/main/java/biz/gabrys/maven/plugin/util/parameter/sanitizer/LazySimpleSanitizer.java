package biz.gabrys.maven.plugin.util.parameter.sanitizer;

public class LazySimpleSanitizer extends AbstractSimpleSanitizer {

    private final ValueContainer container;

    public LazySimpleSanitizer(final boolean valid, final ValueContainer container) {
        super(valid);
        this.container = container;
    }

    public Object sanitize(final Object value) {
        return container.getValue();
    }

    public interface ValueContainer {

        Object getValue();
    }
}
