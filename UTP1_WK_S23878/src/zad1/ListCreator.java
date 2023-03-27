/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> { // Uwaga: klasa musi być sparametrtyzowana
    private final List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public List<T> when(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public <R> List<R> mapEvery(Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            R value = function.apply(item);
            result.add(value);
        }
        return result;
    }
}

