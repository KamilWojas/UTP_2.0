/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Anagrams {

    private Map<String, List<String>> anagramsMap;

    public Anagrams(String filename) {
        anagramsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                String sortedWord = sortString(word);
                List<String> anagramsList = anagramsMap.get(sortedWord);
                if (anagramsList == null) {
                    anagramsList = new ArrayList<>();
                    anagramsMap.put(sortedWord, anagramsList);
                }
                anagramsList.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public List<List<String>> getSortedByAnQty() {
        List<List<String>> sortedAnagrams = new ArrayList<>(anagramsMap.values());
        sortedAnagrams.sort(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> list1, List<String> list2) {
                int cmp = Integer.compare(list2.size(), list1.size());
                if (cmp != 0) {
                    return cmp;
                }
                return list1.get(0).compareTo(list2.get(0));
            }
        });
        return sortedAnagrams;
    }

    public String getAnagramsFor(String word) {
        String sortedWord = sortString(word);
        List<String> anagramsList = anagramsMap.get(sortedWord);
        if (anagramsList == null) {
            return word + ": []";
        }
        return word + ": " + anagramsList.toString();
    }

    private String sortString(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
