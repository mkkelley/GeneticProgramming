/**
 * User: Michael
 * Date: 6/28/12
 * Time: 8:31 PM
 */
public abstract class FitnessFunctionDouble extends FitnessFunction<Double> {

    @Override
    @SafeVarargs
    public final double evaluateFitness(Evaluable<Double> expression, double maxFitness, TerminalVariable<Double>... vars) {
        double fitness = 0;
        for (double d = -2; fitness < maxFitness && d <= 2; d+=.1) {
            for (TerminalVariable<Double> var : vars) {
                varMap.put(var, d);
            }
            fitness += Math.abs(functionToEmulate(d) - expression.evaluate(varMap));
            varMap.clear();
        }
        return fitness;
    }
}
