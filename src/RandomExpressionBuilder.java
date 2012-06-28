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
    List<Terminal<T>> terminalSet;
    Random r;

    public RandomExpressionBuilder(List<Function<T>> funcs, List<Terminal<T>> terms) {
        functionSet = funcs;
        terminalSet = terms;
        r = new Random();
    }

    public Evaluable<T> generateRandomExpression(int maxDepth, String func) {
        if(maxDepth == 0 || growShouldStop(func)) {
            return getRandomTerminal();
        }
        Function<T> f = getRandomFunction();
        List<Evaluable<T>> argSet = new ArrayList<>();
        for(int i = 0; i < f.getArity(); i++) {
            argSet.add(generateRandomExpression(maxDepth - 1, func));
        }
        return new Expression<>(f,argSet);
    }

    private Terminal<T> getRandomTerminal() {
        return terminalSet.get(r.nextInt(terminalSet.size()));
    }

    private Function<T> getRandomFunction() {
        return functionSet.get(r.nextInt(functionSet.size()));
    }

    private boolean growShouldStop(String func) {
        return (func.equals("grow") &&
                Math.random() < (double)terminalSet.size()/(terminalSet.size()+functionSet.size()));
    }
}
