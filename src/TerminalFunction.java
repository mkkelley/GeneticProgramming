import java.util.ArrayList;

/**
 * User: Michael
 * Date: 6/26/12
 * Time: 9:13 PM
 */
public abstract class TerminalFunction<T> extends Function<T> implements Terminal<T> {

    private ArrayList<T> blankList;
    public TerminalFunction() {
        super(0);
        blankList = new ArrayList<T>();
    }

    @Override
    public T evaluate() {
        return execute(blankList);
    }
}
