package model.logic;

import java.util.Comparator;

public class Quick{
    private static Comparator comparator;
    public static void sort(Comparable[] list,Comparator c) {
        StdRandom.shuffle(list);          // Eliminate dependence on input.
        comparator = c;
        sort((Features[]) list, 0, list.length - 1);
    }
    private static void sort(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partition(list, lo, hi);
        sort(list, lo, j - 1);
        sort(list, j + 1, hi);
    }
    private static int partition(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (less(list[++i], P)) if (i == hi) break;
            while (less(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean less(Features v, Features w)
    { return comparator.compare(v,w)<0; }
    private static void exch(Comparable[] list, int i, int j) {
        Comparable t = list[i];
        list[i] = list[j];
        list[j] = t;
    }
    public static boolean isSorted(Features[] list) {
        for (int i = 1; i < list.length; i++)
            if (less(list[i], list[i - 1])) return false;
        return true;
    }
}

