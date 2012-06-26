import java.util.ArrayList;
import java.util.List;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 8:00 PM
 */
public class Expression<T> {

    Function<T> function;
    List<Expression<T>> args;
    T arg;

    public Expression(T arg) {
        this.arg = arg;
        function = null;
        this.args = null;
    }

    public Expression(Function<T> f, List<Expression<T>> args) {
        function = f;
        this.args = args;
        arg = null;
    }

    public T evaluateExpression() {
        if (isTerminal()) {
            return arg;
        }
        List<T> TArgs = new ArrayList<T>();
        for (Expression<T> arg1 : args) {
            TArgs.add(arg1.evaluateExpression());
        }
        return function.executeWrapper(TArgs);
    }

    public boolean isTerminal() {
        return function == null;
    }

    @Override
    public String toString() {
        String tempString = "";
        if (isTerminal()) {
            return arg.toString();
        }
        tempString += "(" + function.toString() + " ";
        for (Expression<T> arg1 : args) {
            tempString += arg1.toString() + " ";
        }
        tempString += ")";
        return tempString;
    }
}
