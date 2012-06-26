import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 7:59 PM
 */
public class RandomExpressionBuilder<T> {

    List<Function<T>> functionSet;
    List<T> terminalSet;
    Random r;

    public RandomExpressionBuilder(List<Function<T>> funcs, List<T> terms) {
        functionSet = funcs;
        terminalSet = terms;
        r = new Random();
    }

    private T getRandomTerminal() {
        return terminalSet.get(r.nextInt(terminalSet.size()));
    }

    private Function<T> getRandomFunction() {
        return functionSet.get(r.nextInt(functionSet.size()));
    }

    public Expression<T> generateRandomExpression(int maxDepth, String func) {
        if(maxDepth == 0 ||
                (func.equals("grow") &&
                        Math.random() < (double)terminalSet.size()/(terminalSet.size()+functionSet.size()))) {
            return new Expression<T>(getRandomTerminal());
        }
        Function<T> f = getRandomFunction();
        List<Expression<T>> argSet = new ArrayList<Expression<T>>();
        for(int i = 0; i < f.getArity(); i++) {
            argSet.add(generateRandomExpression(maxDepth - 1, func));
        }
        return new Expression<T>(f,argSet);
    }
}
