package model.logic;

public class Quick {
    public static void sort(Comparable[] list) {
        StdRandom.shuffle(list);          // Eliminate dependence on input.
        sort(list, 0, list.length - 1);
    }

    private static void sort(Comparable[] list, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(list, lo, hi);
        sort(list, lo, j - 1);
        sort(list, j + 1, hi);
    }
    public static void sortL(Features[] list){
        StdRandom.shuffle(list);
        sortL(list,0,list.length-1);
    }
    public static void sortL(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partitionL(list, lo, hi);
        sortL(list, lo, j - 1);
        sortL(list, j + 1, hi);
    }
    private static int partitionL(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (lessL(list[++i], P)) if (i == hi) break;
            while (lessL(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean lessL(Features v, Features w)
    { return v.compareToL(w) < 0; }
    private static boolean less(Comparable v, Comparable w)
    { return v.compareTo(w) < 0; }
    private static int partition(Comparable[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Comparable P = list[lo];
        while (true) {
            while (less(list[++i], P)) if (i == hi) break;
            while (less(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static void exch(Comparable[] list, int i, int j) {
        Comparable t = list[i];
        list[i] = list[j];
        list[j] = t;
    }

    public static boolean isSorted(Comparable[] list) {
        for (int i = 1; i < list.length; i++)
            if (less(list[i], list[i - 1])) return false;
        return true;
    }
}

