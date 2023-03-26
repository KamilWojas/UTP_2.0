package zad3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputConverter<T>{
    private final T input;

    public InputConverter(T input){
        this.input = input;
    }

    public <R> R convertBy(Function<T, R>... functions) {
        R result = null;
        for (Function<T, R> function : functions) {
            if (result == null) {
                result = function.apply(input);
            } else {
                result = function.apply((T) result);
            }
        }
        return result;
    }
    public static Function<String, List<String>> flines() {
        return filename -> {
            try {
                return Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        };
    }

    public static Function<List<String>, String> join() {
        return strings -> String.join("", strings);
    }

    public static Function<String, List<Integer>> collectInts() {
        return string -> {
            List<Integer> ints = new ArrayList<>();
            Matcher matcher = Pattern.compile("-?\\d+").matcher(string);
            while (matcher.find()) {
                ints.add(Integer.valueOf(matcher.group()));
            }
            return ints;
        };
    }

    public static Function<List<Integer>, Integer> sum() {
        return ints -> ints.stream().mapToInt(Integer::intValue).sum();
    }
}