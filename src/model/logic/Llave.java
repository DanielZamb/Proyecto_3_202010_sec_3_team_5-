package model.logic;

import model.data_structures.ArregloDinamico;

public class Llave {
    ArregloDinamico<Features> values;
    public Llave (){
        values = new ArregloDinamico<>(20);
    }
    public Integer keyRB(Features that){
        return that.getProperties().getOBJECTID();
    }
    public String keyHash(Features that){
      return "("+that.getProperties().getFECHA_HORA()+","+that.getProperties().getCLASE_VEHICULO()+","+that.getProperties().getINFRACCION()+")";
    }
    public String keyA(String date,String type,String infrac){
        return "("+date+","+type+","+infrac+")";
    }
    public ArregloDinamico getArr(){
        return values;
    }
    public Features[] values(){
        Features[] valuesF = new Features[values.darTamano()];
        for(int i=0; i<valuesF.length;i++){
            valuesF[i] = (Features) values.darElemento(i);
        }
        return valuesF;
    }
}
