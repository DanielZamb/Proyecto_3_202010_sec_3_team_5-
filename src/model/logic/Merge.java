package model.logic;

public class Merge {
    private static Comparable[] aux;
    private static Features[] aux1;
    public static void sort(Comparable[] list)
    {
        aux = new Comparable[list.length];
        sort(list, 0, list.length - 1);
    }
    private static void sort(Comparable[] list, int lo, int hi)   {
        if (hi <= lo) return;
        int mid = (lo + hi)/2;
        sort(list, lo, mid);
        sort(list, mid+1, hi);
        merge(list, lo, mid, hi);
    }
    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void sortP(Features[] list)
    {
        aux1 = new Features[list.length];    // Allocate space just once.
        sortP(list, 0, list.length - 1);
    }
    private static void sortP(Features[] list, int lo, int hi)   {
        if (hi <= lo) return;
        int mid = (lo + hi)/2;
        sortP(list, lo, mid);
        sortP(list, mid+1, hi);
        mergeP(list, lo, mid, hi);
    }
    private static void mergeP(Features[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            aux1[k] = a[k];
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux1[j++];
            else if (j > hi) a[k] = aux1[i++];
            else if (lessP(aux1[j], aux1[i])) a[k] = aux1[j++];
            else a[k] = aux1[i++];
        }
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }
    public static boolean isSortedP(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }
    private static boolean less(Comparable v, Comparable w) //dudaaaaaa
    { return v.compareTo(w) < 0; }
    private static boolean lessP(Features v, Features w)
    { return v.compareToP(w) < 0; }
}
