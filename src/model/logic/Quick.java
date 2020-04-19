package model.logic;

public class Quick {
    public static void sort(Comparable[] list, String tipoSort) {
        StdRandom.shuffle(list);          // Eliminate dependence on input.
        if (tipoSort.equalsIgnoreCase("normal"))
            sort(list, 0, list.length - 1);
        else if (tipoSort.equalsIgnoreCase("localidad"))
            sortL((Features[]) list,0,list.length-1);
        else if (tipoSort.equalsIgnoreCase("vehiculo"))
            sortV((Features[]) list,0,list.length-1);
        else if (tipoSort.equalsIgnoreCase("infraccion"))
            sortP((Features[]) list,0,list.length-1);
        else if (tipoSort.equalsIgnoreCase("OBJECTID"))
            sortO((Features[])list,0,list.length-1);
        else if (tipoSort.equalsIgnoreCase("key"))
            sortG((Features[]) list,0,list.length-1);
    }
    private static void sort(Comparable[] list, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(list, lo, hi);
        sort(list, lo, j - 1);
        sort(list, j + 1, hi);
    }
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
    private static void sortG(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partitionG(list, lo, hi);
        sortG(list, lo, j - 1);
        sortG(list, j + 1, hi);
    }
    private static int partitionG(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (lessG(list[++i], P)) if (i == hi) break;
            while (lessG(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean lessG(Features v, Features w)
    { return v.compareKey(w) < 0; }
    private static void sortO(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partitionO(list, lo, hi);
        sortO(list, lo, j - 1);
        sortO(list, j + 1, hi);
    }
    private static int partitionO(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (lessO(list[++i], P)) if (i == hi) break;
            while (lessO(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean lessO(Features v, Features w)
    { return v.compareOb(w) < 0; }
    private static void sortL(Features[] list, int lo, int hi){
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
    private static void sortP(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partitionP(list, lo, hi);
        sortP(list, lo, j - 1);
        sortP(list, j + 1, hi);
    }
    private static int partitionP(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (lessP(list[++i], P)) if (i == hi) break;
            while (lessP(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean lessP(Features v, Features w)
    { return v.compareToP(w) < 0; }
    private static void sortV(Features[] list, int lo, int hi){
        if (hi <= lo) return;
        int j = partitionV(list, lo, hi);
        sortV(list, lo, j - 1);
        sortV(list, j + 1, hi);
    }
    private static int partitionV(Features[] list, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Features P = list[lo];
        while (true) {
            while (lessV(list[++i], P)) if (i == hi) break;
            while (lessV(P, list[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }
    private static boolean lessV(Features v, Features w)
    { return v.compareClaseV(w) < 0; }
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

