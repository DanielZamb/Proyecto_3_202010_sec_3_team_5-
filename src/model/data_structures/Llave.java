package model.data_structures;

import model.logic.Features;

public class Llave {
    ArregloDinamico<Features> values;
    public Llave (){
        values = new ArregloDinamico<>(20);
    }
    public String key(Features that){
      return "("+that.getProperties().getFECHA_HORA()+","+that.getProperties().getCLASE_VEHI()+","+that.getProperties().getINFRACCION()+")";
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
