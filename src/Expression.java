import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 8:00 PM
 */
public class Expression<T> implements Evaluable<T> {

    Function<T> function;
    List<Evaluable<T>> args;

    public Expression(Function<T> f, List<Evaluable<T>> args) {
        function = f;
        this.args = args;
    }

    @Override
    public T evaluate(Map<TerminalVariable<T>,T> variableValues) {
        List<T> TArgs = new ArrayList<>();
        for (Evaluable<T> arg1 : args) {
            TArgs.add(arg1.evaluate(variableValues));
        }
        return function.executeWrapper(TArgs);
    }

    @Override
    public String toString() {
        String tempString = "";
        tempString += "(" + function.toString() + " ";
        for (Evaluable<T> arg1 : args) {
            tempString += arg1.toString() + " ";
        }
        tempString += ")";
        return tempString;
    }
}
