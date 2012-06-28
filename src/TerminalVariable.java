import java.util.Map;

/**
 * User: Michael
 * Date: 6/27/12
 * Time: 7:02 PM
 */
public class TerminalVariable<T> implements Terminal<T> {
    @Override
    public T getValue() throws IllegalArgumentException {
        return null;
    }

    @Override
    public T evaluate(Map<TerminalVariable<T>, T> variableValues) throws IllegalArgumentException {
        return variableValues.get(this);
    }
}
