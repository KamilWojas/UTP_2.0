/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad1;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    static List<String> getPricesInPLN(List<String> destinations, double xrate) {
        return ListCreator.collectFrom(destinations)
                .when(str -> str.startsWith("WAW")) /*<-- lambda wyrażenie
         *  selekcja wylotów z Warszawy (zaczynających się od WAW)
         */

                .mapEvery(str -> {
                            String[] parts = str.split(" ");
                            String destination = parts[1];
                            int priceEUR = Integer.parseInt(parts[2]);
                            int pricePLN = (int) Math.round(priceEUR * xrate);
                            return " to " + destination + " - price in PLN:\t" + pricePLN;
                        } /*<-- lambda wyrażenie
                         *  wyliczenie ceny przelotu w PLN
                         *  i stworzenie wynikowego napisu
                         */
                );
    }

    public static void main(String[] args) {
        // Lista destynacji: port_wylotu port_przylotu cena_EUR
        List<String> dest = Arrays.asList(
                "bleble bleble 2000",
                "WAW HAV 1200",
                "xxx yyy 789",
                "WAW DPS 2000",
                "WAW HKT 1000"
        );
        double ratePLNvsEUR = 4.30;
        List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
        for (String r : result) System.out.println(r);
    }
}
