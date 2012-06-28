import java.util.Map;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 8:59 PM
 */
public interface Evaluable<T> {
    public T evaluate(Map<TerminalVariable<T>,T> variableValues) throws IllegalArgumentException;
}
