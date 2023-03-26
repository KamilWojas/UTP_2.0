package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> {
    private List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Predicate<T> predicate) {
        List<T> filteredList = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                filteredList.add(item);
            }
        }
        return new ListCreator<>(filteredList);
    }

    public <R> List<R> mapEvery(Function<T, R> mapper) {
        List<R> mappedList = new ArrayList<>();
        for (T item : list) {
            mappedList.add(mapper.apply(item));
        }
        return mappedList;
    }
}
