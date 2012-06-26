import java.util.List;

/**
 * User: Michael
 * Date: 6/24/12
 * Time: 7:59 PM
 */
public abstract class Function<T> {
    private int arity;

    public Function(int arity) {
        this.arity = arity;
    }

    public T executeWrapper(List<T> args) {
        if(!hasValidArguments(args)) {
            throw new IllegalArgumentException();
        }
        return execute(args);
    }

    public abstract T execute(List<T> args);

    public boolean hasValidArguments(List<T> args) {
        return args.size() == arity;
    }

    public int getArity() {
        return arity;
    }

    @Override
    public String toString() {
        return "F";
    }
}
