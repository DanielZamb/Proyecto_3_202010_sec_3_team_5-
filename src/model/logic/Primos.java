package model.logic;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GarbageCollectorMXBean;
import controller.Controller;

import java.io.IOException;
import java.math.*;
import java.util.ArrayList;

public class Primos {
    private ArrayList<Integer> primos;
    public ArrayList<Integer> darPrimos(int N){
        ArrayList<Boolean> marca = new ArrayList<>();
        primos = new ArrayList<>();
        int Num = N;
        if (Num>2){
            marca.add(true);
            marca.add(true);
            for (int a = 2 ; a<(Num/2) ; a++){ //crea una lista de numeros impares hasta N
                marca.add(true);
            }
            for (int i = 2; i < marca.size(); i++){
                if (marca.get(i)) {
                    for (int j = i + 2*(i-1) + 1; j < marca.size() ; j = j + 2*(i-1) + 1 )
                        marca.set(j, false);
                }
             }
            primos.add(1);
            primos.add(2);
            for(int k = 2 ; k<Num/2 ; k++){
                if (marca.get(k))
                    primos.add(2*(k)-1);
            }
            marca.clear();
        }
        else {
           primos.add(1);
           primos.add(2);
        }
        return  primos;
    }
    public ArrayList<Integer> getPrimos(){
        return primos;
    }
    /**
     * el metodo para hallar todos los primos hasta n no se petaquea a menos que supere la capcidad de un int, 4 bytes
     * public static void main(String[] args) throws IOException
    {
        Primos primos = new Primos();
        primos.darPrimos(1000000000);
        for(int j=0;j<primos.getPrimos().size();j++)
            System.out.println(primos.getPrimos().get(j));
    }
*/
}
