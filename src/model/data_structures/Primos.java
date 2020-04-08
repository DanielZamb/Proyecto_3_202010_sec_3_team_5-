package model.data_structures;

import controller.Controller;

import java.io.IOException;
import java.math.*;
import java.util.ArrayList;

public class Primos {
    public static ArrayList<Integer> darPrimos(int N){
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<Boolean> marca = new ArrayList<>();
        ArrayList<Integer> primos = new ArrayList<>();
        int Num = N;
        if (Num>2){
            numeros.add(1);
            numeros.add(2);
            marca.add(false);
            marca.add(false);
            for (int a = 2 ; a<Num ; a++){ //crea una lista de numeros hasta N
                numeros.add(a+1);
                marca.add(false);
            }
            for (int i = 2; i<= Math.floor(Math.sqrt(Num)); i++){
                if (!marca.get(i-1)) {
                    for (int j = i; j<=(Num/i) ; j++)
                        marca.set(j*i-1, true);
                }
             }
            for(int k = 0 ; k<Num ; k++){
                if (!marca.get(k))
                    primos.add(numeros.get(k));
            }
        }
        else {
           primos.add(1);
           primos.add(2);
        }
        return  primos;
    }
}
