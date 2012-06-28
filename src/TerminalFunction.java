import java.util.Map;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:13 PM
 */
public abstract class TerminalFunction<T> extends Function<T> implements Terminal<T> {

    public TerminalFunction() {
        super(0);
    }

    @Override
    public T getValue() {
        return execute(null);
    }

    @Override
    public T evaluate(Map<TerminalVariable<T>, T> vars) {
        return getValue();
    }
}
