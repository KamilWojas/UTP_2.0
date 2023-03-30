package zad4;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    public static <T> Maybe<T> of(T value) {
        return new Maybe<>(value);
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public <R> Maybe<R> map(Function<? super T, ? extends R> function) {
        if (value != null) {
            R result = function.apply(value);
            return Maybe.of(result);
        } else {
            return Maybe.of(null);
        }
    }

    public T get() {
        if (value != null) {
            return value;
        } else {
            throw new NoSuchElementException("maybe is empty");
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    public T orElse(T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public Maybe<T> filter(Predicate<? super T> predicate) {
        if (value != null && predicate.test(value)) {
            return this;
        } else {
            return Maybe.of(null);
        }
    }

    @Override
    public String toString() {
        if (value != null) {
            return "Maybe has value " + value.toString();
        } else {
            return "Maybe is empty";
        }
    }
}