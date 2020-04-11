package model.logic;

import model.data_structures.ArregloDinamico;

public class Make {
    static String key(Features that){
        String key = "("+that.getProperties().getFECHA_HORA()+","+that.getProperties().getCLASE_VEHI()+","+that.getProperties().getINFRACCION()+")";
        return key;
    }
    static ArregloDinamico<Features> value(){

        ArregloDinamico<Features> arr = new ArregloDinamico<>(100);
        return arr;
    }
}
