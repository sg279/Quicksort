import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Quicksort {

    static ArrayList<double[]> allResults = new ArrayList<>();

    /**
     * A regular recursive quicksort implementation, choosing the pivot point to be the first element in the list
     * @param list The list to be sorted
     * @return The sorted list
     */
    public static ArrayList<Integer> quicksort(ArrayList<Integer> list) {
        if (list.size() == 1) {
            return list;
        } else {
            Integer pivot = list.remove(list.size()/2);
            ArrayList<Integer> lessThan = new ArrayList<Integer>();
            ArrayList<Integer> moreThan = new ArrayList<Integer>();
            while (!list.isEmpty()) {
                if (list.get(0) <= pivot) {
                    lessThan.add(list.remove(0));
                } else {
                    moreThan.add(list.remove(0));
                }
            }
            if (lessThan.size() != 0) {
                lessThan = quicksort(lessThan);
            }
            if (moreThan.size() != 0) {
                moreThan = quicksort(moreThan);
            }
            ArrayList<Integer> sorted = new ArrayList<Integer>();
            sorted.addAll(lessThan);
            sorted.add(pivot);
            sorted.addAll(moreThan);
            return sorted;
        }

    }

    public static void main(String[] args) {

        for (float i = 0; i <= 3; i++) {
            for (int j = 100; j <= 1000; j += 100) {
                ArrayList<Integer> list = createList(i / 20, j);
                System.out.println(runTest(list, 5000));
            }


            File directory = new File(System.getProperty("user.dir") + "/results/");
            if (!directory.isDirectory()) {
                new File(System.getProperty("user.dir") + "/results/").mkdirs();
            }
            File file = new File(directory.getAbsolutePath() + "/timeComplexityMidPivotDescending.csv");
            try {
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                //bufferedWriter.write("Sortedness, Average time");
                bufferedWriter.write(i+"/20, Size of list, Average time");
                bufferedWriter.newLine();
                for (double[] results : allResults
                ) {
                    bufferedWriter.write(results[0] + "," + results[1]+ ","+results[2]);
                    //bufferedWriter.write(results[0] + "," + results[1]);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                fileWriter.close();
                allResults.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * This method gets the average time it takes in milleseconds to sort a list a given number of times
     * @param list The list to be sorted
     * @param tests The number of times to sort the list
     * @return A string saying the size, sortedness, and average time it takes to sort the list,
     */
    public static String runTest(ArrayList<Integer> list, int tests) {
        ArrayList<Long> timeTaken = new ArrayList<>();
        for (int i = 0; i < tests; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(list);
            Date now = new Date();
            long start = now.getTime();
            quicksort(temp);
            now = new Date();
            long end = now.getTime();
            timeTaken.add(end - start);
        }
        double averageTime = averageTime(timeTaken);

        //Choosing what measure of sortedness to use
        double sortedness = sortedness(list);
        //double sortedness = adjacentSortedness(list);

        //Choosing what results to record
        //allResults.add(new double[]{sortedness, averageTime});
        allResults.add(new double[]{sortedness, list.size(), averageTime});

        return "A list of " + list.size() + " items with sortedness " + sortedness + " sorted " + tests + " times took on average " + averageTime + "ms";


    }

    /**
     * This method gets the mean of a list of times
     * @param times The list of times
     * @return The mean of the list
     */
    public static double averageTime(ArrayList<Long> times) {
        double total = 0;
        for (Long time : times
        ) {
            total += time;
        }
        return total / times.size();
    }

    /**
     * This method returns 1 minus the number of swaps bubble sort makes to sort a given list divided by the maximum number of swaps a bubble sort can
     * make to sort a list of that size
     * @param list The list being checked for 'sortedness'
     * @return The 'sortedness' of the list
     */
    public static double sortedness(ArrayList<Integer> list) {
        boolean swapsMade = true;
        double swaps = 0;
        //Run until no swaps are made
        while (swapsMade) {
            swapsMade = false;
            for (int i = 0; i < list.size() - 1; i++) {
                //If the node after the current node is larger than the current node, swap the node's elements
                if (list.get(i) > list.get(i + 1)) {
                    int temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapsMade = true;
                    swaps++;
                }

            }
        }
        int n = list.size();

        return 1 - (swaps / (0.5 * (n - 1) * n));
    }

    /**
     * This method returns 1 minus the number of adjacent pairs in a given list are out of order divided by the size of the list
     * @param list The list being checked for 'sortedness'
     * @return The 'sortedness' of the list
     */
    public static double adjacentSortedness(ArrayList<Integer> list) {
        float outOfOrder = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            //If the node after the current node is larger than the current node, swap the node's elements
            if (list.get(i) > list.get(i + 1)) {
                outOfOrder++;
            }

        }
        int n = list.size();

        return 1 - (outOfOrder / n);
    }

    /**
     * This method Creates a list of a given size and randomises a given proportion of items
     * @param randomness The proportion of the list to be randomised
     * @param size The size of the list
     * @return The randomised list
     */
    public static ArrayList<Integer> createList(double randomness, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        //Create a list of descending values to 0 and then ascending values
        /*for (int i = 0; i < size/2; i++) {
            list.add(list.size()-i);
        }
        for (int i = 0; i < size/2; i++) {
            list.add(i);
        }*/
        //Create a sorted list
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        //Randomise twice the given randomness value numbers in the list
        for (int i = 0; i < (randomness * size); i++) {
            list.set(random.nextInt(size), random.nextInt(size * 2));
        }
        return list;

    }


}
