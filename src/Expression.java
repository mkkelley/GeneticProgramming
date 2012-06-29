import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 8:00 PM
 */
public class Expression<T> implements Evaluable<T> {

    private Function<T> function;
    private List<Evaluable<T>> args;

    /**
     * Create a new Expression - a function with the given parameters.
     * Note that the parameters are not limited to constants, variables,
     * or 0-arity functions. They can be any other Evaluable, notably other
     * expressions.
     * @param f The function.
     * @param args The arguments to the function. Must match its arity.
     * @throws IllegalArgumentException If the arity of the function and the size
     *                                  of the argument list do not match.
     */
    public Expression(Function<T> f, List<Evaluable<T>> args) throws IllegalArgumentException {
        function = f;
        setArgs(args);
    }

    /**
     * Gets the true value of the expression - apply the function to the given arguments.
     * It takes a map of variables and the values that they include. Probably throws a nullPointerException
     * if any variable is not defined in the input map.
     * @param variableValues Map of the variables and the input values.
     * @return The value of the function applied to the arguments.
     */
    @Override
    public T evaluate(Map<TerminalVariable<T>,T> variableValues) {
        List<T> TArgs = new ArrayList<>();
        for (Evaluable<T> arg1 : args) {
            TArgs.add(arg1.evaluate(variableValues));
        }
        return function.executeWrapper(TArgs);
    }

    public Expression<T> replaceExpressionAtNode(Expression<T> toBeReplaced, Expression<T> toReplace) {
        if(toReplace.equals(toBeReplaced)) {
            return this;
        }
        if(this.getAllExpressions().contains(toReplace)) {
            toReplace = new Expression<T>(function,args);
        }
        Expression<T> newExpr = new Expression<>(function,args);
        for (int i = 0; i < newExpr.args.size(); i++) {
            if(newExpr.args.get(i).equals(toBeReplaced)) {
                newExpr.args.set(i,toReplace);
                break;
            }
            if (newExpr.args.get(i) instanceof Expression) {
                ((Expression<T>) newExpr.args.get(i)).replaceExpressionAtNode(toBeReplaced,toReplace);
            }
        }
        return newExpr;
    }

    public Expression<T> replaceTerminal(Terminal<T> toBeReplaced, Terminal<T> toReplace) {
        Expression<T> newExpr = new Expression<>(function,args);
        for (int i = 0; i < newExpr.args.size(); i++) {
            if(newExpr.args.get(i).equals(toBeReplaced)) {
                newExpr.args.set(i,toReplace);
                break;
            }
            if(newExpr.args.get(i) instanceof Expression) {
                ((Expression<T>) newExpr.args.get(i)).replaceTerminal(toBeReplaced,toReplace);
            }
        }
        return newExpr;
    }

    public List<Evaluable<T>> getAllExpressions() {
        List<Evaluable<T>> exprList = new ArrayList<>();
        for (Evaluable<T> ex : args) {
            if (ex instanceof Terminal) {
                exprList.add(ex);
            } else if (ex instanceof Expression && !((Expression) ex).getAllExpressions().contains(ex)) {
                exprList.addAll(((Expression<T>) ex).getAllExpressions());
            }
        }
        exprList.add(this);
        return exprList;
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

    private void setArgs(List<Evaluable<T>> args) throws IllegalArgumentException {
        if(function.getArity() != args.size()) {
            throw new IllegalArgumentException("Function arity and given argument length do not match.");
        }
        this.args = args;
    }
}
