package com.dohnalovar;

import java.util.*;
import java.util.function.BiPredicate;

/**
 * Created by dohnalovar on 6/13/2019
 */
public class SubStringDivisibility {

    private static final Set<Integer> universum = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    private static Set<List<Integer>> results = new LinkedHashSet<>();

    private static SubStringDivisibility ourInstance = new SubStringDivisibility();

    public static SubStringDivisibility getInstance() {
        return ourInstance;
    }

    private SubStringDivisibility() {

        // start with divisibility by 17

        // 6*17 > 100 && 59*17 < 1000
        for (int i = 6; i < 59; i++) {
            int x = i*17;

            // x = a*10^2 + b*10^1 + c*10^0
            int c = x%10;
            x = x / 10;
            int b = x%10;
            x = x / 10;
            int a = x%10;

            if (areUniq(a, b, c)) {
                List<Integer> result = new ArrayList<>(Arrays.asList(c, b, a));
                produce(result);
            }
        }
    }

    private static boolean areUniq(Integer ... digits) {

        Set<Integer> set = new HashSet<Integer>(Arrays.asList(digits));
        return digits.length == set.size();
    }

    private static boolean isDividedBy(int number, int factor) {
        return (number % factor) == 0;
    };

    private static void produce(List<Integer> substring) {

        try {
            int size = substring.size();
            int factor;
            switch (size) {
                case(3):
                    factor = 13;
                    break;
                case(4):
                    factor = 11;
                    break;
                case(5):
                    factor = 7;
                    break;
                case(6):
                    factor = 5;
                    break;
                case(7):
                    factor = 3;
                    break;
                case(8):
                    factor = 2;
                    break;
                case(9):
                    // add the last not used digit to substring
                    Set<Integer> set = new HashSet<>(universum);
                    set.removeAll(substring);
                    Iterator<Integer> i = set.iterator();
                    substring.add(i.next());
                    // add satisfied substring to results
                    results.add(substring);
                    return;
                default:
                    throw new Exception("Unsupported length of substring");
            }

            Set<Integer> set = new HashSet<>(universum);
            set.removeAll(substring); //set = asymetric difference(universum, substring)

            for (Integer entry: set) {
                int number = entry*100 + substring.get(size-1)*10 + substring.get(size-2);
                if (isDividedBy(number, factor)) {
                    //produce next digit
                    List<Integer> found = new ArrayList<>(substring);
                    found.add(entry);
                    produce(found);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubStringDivisibility{} \n" +
                                                   "------------------------\n");
        results.forEach( (entry) -> {
            for (int i = entry.size()-1; i >= 0 ; i--) {
                sb.append(entry.get(i));
            }
            sb.append("\n");
        });

        return sb.toString();
    }
}
