/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Pobranie listy słów z URL
        URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> words = br.lines().collect(Collectors.toList());
        br.close();

        // Utworzenie mapy anagramów
        Map<String, List<String>> anagramsMap = words.stream()
                .collect(Collectors.groupingBy(word -> {
                    char[] chars = word.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                }));

        // Wypisanie słów z maksymalną liczbą anagramów
        anagramsMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1) // Tylko anagramy
                .max(Comparator.comparingInt(entry -> entry.getValue().size())) // Najwięcej anagramów
                .ifPresent(entry -> {
                    System.out.printf("Słowo z maksymalną liczbą anagramów: %s%n", entry.getKey());
                    System.out.println("Anagramy: " + String.join(" ", entry.getValue()));
                });
    }
}