/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:07 PM
 */
public class TerminalExpression<T> implements Terminal<T> {

    T value;

    public TerminalExpression(T v) {
        value = v;
    }

    @Override
    public T evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
