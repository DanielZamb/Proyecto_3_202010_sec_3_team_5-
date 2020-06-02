package model.logic;

import model.data_structures.ArregloDinamico;
import model.logic.comparendos.Features;

public class Llave {
    ArregloDinamico<Features> values;
    public Llave (){
        values = new ArregloDinamico<>(20);
    }
    public String keyReq3A(Features that){
        return "("+that.getProperties().getLOCALIDAD()+","+that.getProperties().getFECHA_HORA()+")";
    }
    public String keyReq2BComp(Features that){
        return that.getProperties().getMEDIO_DETECCION() + that.getProperties().getCLASE_VEHICULO() + that.getProperties().getTIPO_SERVICIO() + that.getProperties().getLOCALIDAD();
    }
    public String keyReq2B(Features that){
        return "("+that.getProperties().getMEDIO_DETECCION()+","+ that.getProperties().getCLASE_VEHICULO()+","+ that.getProperties().getTIPO_SERVICIO()+","+ that.getProperties().getLOCALIDAD()+")";
    }
    public String keyHash(Features that){
      return "("+that.getProperties().getFECHA_HORA()+","+that.getProperties().getCLASE_VEHICULO()+","+that.getProperties().getINFRACCION()+")";
    }
    public String keyReq2A(Features that){
        String[] fechaHora = that.getProperties().getFECHA_HORA().split("T");
        return fechaHora[0];
    }
    public String keyReq3B(Features that){
        return "("+that.getProperties().getCLASE_VEHICULO()+","+that.getGeometry().DarCoordenadas().get(0)+")";
    }
    public String keyReq1C(Features that){
        String[] fechaHora_ = that.getProperties().getFECHA_HORA().split("T");
        return fechaHora_[0];
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
