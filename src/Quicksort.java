import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Quicksort {

    public static ArrayList<Integer> quicksort(ArrayList<Integer> list) {
        if (list.size() == 1) {
            return list;
        } else {
            Integer pivot = list.remove(list.size() / 2);
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
        /*ArrayList<Integer> unsorted = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5000; i++) {
            unsorted.add(random.nextInt(10000));
        }
        ArrayList<Integer> sorted = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            sorted.add(i);
        }
        ArrayList<Integer> reverseSorted = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            reverseSorted.add(5000 - i);
        }
        System.out.println(runTest(reverseSorted, 1000));
        System.out.println(runTest(unsorted, 1000));
        System.out.println(runTest(sorted, 1000));*/
        System.out.println(runTest(createList(0,5000),1000));
        System.out.println(runTest(createList(0.5,5000),1000));
        System.out.println(runTest(createList(1,5000),1000));

    }

    public static String runTest(ArrayList<Integer> list, int tests) {
        ArrayList<Long> timeTaken = new ArrayList<>();
        for (int i = 0; i < tests; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(list);
            Date now = new Date();
            long start = now.getTime();
            quicksort(temp);
            now=new Date();
            long end = now.getTime();
            timeTaken.add(end - start);
        }
        return "A list of " + list.size() + " items with sortedness "+sortedness(list)+" sorted " + tests + " times took on average " + averageTime(timeTaken) + "ms";
    }

    public static double averageTime(ArrayList<Long> times) {
        double total = 0;
        for (Long time : times
        ) {
            total += time;
        }
        return total / times.size();
    }

    public static double sortedness(ArrayList<Integer> list) {
        boolean swapsMade = true;
        double swaps=0;
        //Run until no swaps are made
        while (swapsMade) {
            swapsMade = false;
            for (int i = 0; i < list.size()-1; i++) {
                //If the node after the current node is larger than the current node, swap the node's elements
                if (list.get(i)>list.get(i+1)) {
                    int temp = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1,temp);
                    swapsMade = true;
                    swaps++;
                }

            }
        }
        int n = list.size();

        return 1-(swaps/(0.5*(n-1)*n));
    }

    public static ArrayList<Integer> createList(double randomness, int size){
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        for(int i = 0; i<(randomness*size); i++){
            list.set(random.nextInt(size), random.nextInt(size*2));
        }
        return list;

    }
}
