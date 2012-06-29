import java.util.*;

/**
 * User: Michael
 * Date: 6/28/12
 * Time: 7:27 PM
 */
public class Generation<T> {

    public static void main(String[] args) {
        List<Function<Double>> functionSet = new ArrayList<>();
        functionSet.add(CommonFunctions.add);
        functionSet.add(CommonFunctions.subtract);
        functionSet.add(CommonFunctions.multiply);
        functionSet.add(CommonFunctions.divide);
        List<Terminal<Double>> termSet = new ArrayList<>();
        termSet.add(new TerminalConstant<>(1.0));
        termSet.add(new TerminalConstant<>(2.0));
        termSet.add(new TerminalConstant<>(3.0));
        termSet.add(new TerminalConstant<>(4.0));
        termSet.add(new TerminalConstant<>(5.0));
        termSet.add(new TerminalConstant<>(6.0));
        termSet.add(new TerminalConstant<>(7.0));
        termSet.add(new TerminalConstant<>(8.0));
        termSet.add(new TerminalConstant<>(9.0));
        termSet.add(new TerminalConstant<>(0.0));
        TerminalVariable<Double> x = new TerminalVariable<Double>() {
            @Override
            public String toString() {
                return "X";
            }
        };
        FitnessFunctionDouble ff = new FitnessFunctionDouble() {
            @Override
            public double functionToEmulate(double... vars) {
                return vars[0] * vars[0] + vars[0] + 1;
            }
        };
        termSet.add(x);
        RandomExpressionBuilder<Double> reb = new RandomExpressionBuilder<>(functionSet, termSet);
        Generation<Double> gen = new Generation<>(reb, 3);
        gen.evaluateFitnessOfAll(ff,x);
        for (int i = 0; i < gen.individuals.size(); i++) {
            System.out.println("gen.individuals.get(i) = " + gen.individuals.get(i));
        }
        Generation currentGen = gen.createOffspring();
        for(int i = 0 ; i < 50; i++) {
            //gen2.evaluateFitnessOfAll(ff,x);
            for (int j = 0; j < currentGen.individuals.size(); j++) {
                System.out.println("gen2.individuals.get(i) = " + currentGen.individuals.get(i));
            }
            currentGen = currentGen.createOffspring();
        }
    }

    private static final double CHOOSE_SUBTREE_XOVER_CHANCE = 1.1;

    private static final double SUBTREE_FUNCTION_CHOICE_CHANCE = 1.1;

    private static final String growFunc = "grow";

    private static final String fullFunc = "full";

    private static final int POPULATION_SIZE = 5000;

    private List<Evaluable<T>> individuals;

    private Map<Evaluable<T>, Double> individualsFitness;

    private Random r;

    public Generation(RandomExpressionBuilder<T> reb, int maxDepth) {
        individuals = new ArrayList<>();
        individualsFitness = new HashMap<>();
        r = new Random();
        Evaluable<T> temp;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            temp = reb.generateRandomExpression(maxDepth, (i % 2 == 0 ? growFunc : fullFunc));
            individuals.add(temp);
        }
        System.out.println("individuals.size() = " + individuals.size());
    }

    private Generation(List<Evaluable<T>> indivs, Map<Evaluable<T>,Double> indivFit) {
        individuals = new ArrayList<>();
        individuals.addAll(indivs);
        individualsFitness = new HashMap<>();
        individualsFitness.putAll(indivFit);
        System.out.println("gen2 individuals.size() = " + individuals.size());
    }

    @SafeVarargs
    public final void evaluateFitnessOfAll(FitnessFunction<T> ff, TerminalVariable<T>... vars) {
        double maxFitness = Double.MAX_VALUE;
        for (Evaluable<T> ev : individuals) {
            double evFitness = ff.evaluateFitness(ev, maxFitness, vars);
            if (evFitness < maxFitness) {
                maxFitness = evFitness;
            }
            individualsFitness.put(ev, evFitness);
        }
    }

    public Generation<T> createOffspring() {
        Generation<T> newGeneration = new Generation<>(individuals, individualsFitness);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Evaluable<T> ts1 = tournamentSelection();
            Evaluable<T> ts2 = tournamentSelection();
            if (!(ts1 instanceof Expression) || !(ts2 instanceof Expression)) {
                i--;
                continue;
            }
            newGeneration.individuals.set(i,subtreeCrossover((Expression<T>)ts1,(Expression<T>)ts2));
        }
        return newGeneration;
    }

    private Expression<T> subtreeCrossover(Expression<T> parentOne, Expression<T> parentTwo) throws IllegalArgumentException {
        Expression<T> newExpression = null;
        List<Expression<T>> pOneFullList = ((Expression) parentOne).getAllExpressions();
        List<Expression<T>> pTwoFullList = ((Expression) parentTwo).getAllExpressions();

        List<Expression<T>> pOneExprList = new ArrayList<>(pOneFullList);
        List<Expression<T>> pTwoExprList = new ArrayList<>(pTwoFullList);

        List<Terminal<T>> pOneTermList = new ArrayList<>();
        List<Terminal<T>> pTwoTermList = new ArrayList<>();

        for (Evaluable<T> ev : pOneFullList) {
            if (ev instanceof Terminal) {
                pOneTermList.add((Terminal<T>) ev);
            }
        }

        for (Evaluable<T> ev : pTwoFullList) {
            if (ev instanceof Terminal) {
                pTwoTermList.add((Terminal<T>) ev);
            }
        }

        pOneExprList.removeAll(pOneTermList);
        pTwoExprList.removeAll(pTwoTermList);

        if (Math.random() < CHOOSE_SUBTREE_XOVER_CHANCE) {
            //so we're doing a subtree crossover
            if (Math.random() < SUBTREE_FUNCTION_CHOICE_CHANCE) {
                //and choosing a function
                Expression<T> p1ST = pOneExprList.get(r.nextInt(pOneExprList.size()));
                Expression<T> p2ST = pTwoExprList.get(r.nextInt(pTwoExprList.size()));

                newExpression = parentOne.replaceExpressionAtNode(p1ST,p2ST);
            } else {
                //and choosing a leaf
                Terminal<T> p1S = pOneTermList.get(r.nextInt(pOneTermList.size()));
                Terminal<T> p2S = pTwoTermList.get(r.nextInt(pTwoTermList.size()));

                newExpression = parentOne.replaceTerminal(p1S, p2S);
            }
        } else {
            //so we're doing a mutation
        }
        return newExpression;
    }

    private Evaluable<T> tournamentSelection() {
        List<Evaluable<T>> selections = new ArrayList<>(POPULATION_SIZE / 10);
        for (int i = 0; i < POPULATION_SIZE / 10; i++) {
            selections.add(individuals.get(r.nextInt(POPULATION_SIZE)));
        }
        Evaluable<T> selection = selections.get(0);
        double bestFitness = individualsFitness.get(selection);
        for (int i = 1; i < selections.size(); i++) {
            double currentFitness = individualsFitness.get(selections.get(i));
            //System.out.println("currentFitness = " + currentFitness);
            if (currentFitness < bestFitness) {
                selection = selections.get(i);
                bestFitness = currentFitness;
            }
        }
        return selection;
    }
}
