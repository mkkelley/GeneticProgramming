import java.util.List;

/**
 * User: Michael
 * Date: 6/28/12
 * Time: 10:22 PM
 */
public class CommonFunctions {
    public static Function<Double> multiply = new Function<Double>(2) {
        @Override
        public Double execute(List<Double> args) {
            return args.get(0) * args.get(1);
        }
        @Override
        public String toString() {
            return "*";
        }
    };
    public static Function<Double> add = new Function<Double>(2) {
        @Override
        public Double execute(List<Double> args) {
            return args.get(0) + args.get(1);
        }
        @Override
        public String toString() {
            return "+";
        }
    };
    public static Function<Double> subtract = new Function<Double>(2) {
        @Override
        public Double execute(List<Double> args) {
            return args.get(0) - args.get(1);
        }
        @Override
        public String toString() {
            return "-";
        }
    };
    public static Function<Double> divide = new Function<Double>(2) {
        @Override
        public Double execute(List<Double> args) {
            double result;
            try {
                result = args.get(0) / args.get(1);
            } catch (Exception e) {
                result = 1;
            }
            return result;
        }
        @Override
        public String toString() {
            return "/";
        }
    };
}
