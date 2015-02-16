package apriori;

import java.util.*;
//Opensource code for making permutations
// Found at https://stackoverflow.com/questions/20906214/permutation-algorithm-for-array-of-integers-in-java

/**
 * Write a description of class GeneratePermutations here.
 *
 * @author Kushtrim
 * @version 1.01
 */
public class Permutations {


    public ArrayList<ArrayList<Object>> permute(Object[] num) {
        ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
        permute(num, 0, result);
        return result;
    }


    private void permute(Object[] num, int start, ArrayList<ArrayList<Object>> result) {

        if (start >= num.length) {
            ArrayList<Object> item = convertArrayToList(num);
            result.add(item);
        }

        for (int j = start; j <= num.length - 1; j++) {
            swap(num, start, j);
            permute(num, start + 1, result);
            swap(num, start, j);
        }
    }

    private ArrayList<Object> convertArrayToList(Object[] num) {
        ArrayList<Object> item = new ArrayList<Object>();
        for (int h = 0; h < num.length; h++) {
            item.add(num[h]);
        }
        return item;
    }

    private void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}