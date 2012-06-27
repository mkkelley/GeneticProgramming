import java.util.ArrayList;
import java.util.List;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 8:07 PM
 */
public class Driver {
    public static void main(String[] args) {
        Function<Integer> multiply = new Function<Integer>(2) {
            @Override
            public Integer execute(List<Integer> args) {
                return args.get(0) * args.get(1);
            }
            @Override
            public String toString() {
                return "*";
            }
        };
        Function<Integer> add = new Function<Integer>(2) {
            @Override
            public Integer execute(List<Integer> args) {
                return args.get(0) + args.get(1);
            }
            @Override
            public String toString() {
                return "+";
            }
        };
        Function<Integer> cube = new Function<Integer>(1) {
            @Override
            public Integer execute(List<Integer> args) {
                return (int)Math.pow(args.get(0), 3);
            }
            @Override
            public String toString() {
                return "^3";
            }
        };

        List<Function<Integer>> functionSet = new ArrayList<Function<Integer>>();
        functionSet.add(multiply);
        functionSet.add(add);
        functionSet.add(cube);
        List<Terminal<Integer>> termSet = new ArrayList<Terminal<Integer>>();
        termSet.add(new TerminalExpression<Integer>(4));
        termSet.add(new TerminalExpression<Integer>(5));
        termSet.add(new TerminalExpression<Integer>(6));
        RandomExpressionBuilder<Integer> reb = new RandomExpressionBuilder<Integer>(functionSet, termSet);
        int highestAnswer = 0;
        int currentAnswer;
        Evaluable<Integer> giveItAShot;
        long count = 0;
        while (highestAnswer != 10077696) {
            giveItAShot = reb.generateRandomExpression(2, "!grow");

            currentAnswer = giveItAShot.evaluate();
            count++;
            if (currentAnswer > highestAnswer) {
                highestAnswer = currentAnswer;
                System.out.println("currentAnswer = " + currentAnswer);
                System.out.println("giveItAShot.toString() = " + giveItAShot.toString());
            }

        }
        System.out.println("count = " + count);

    }
}
