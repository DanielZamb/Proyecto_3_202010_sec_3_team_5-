package model.logic.comparendos;

import java.util.List;

public class Geometry {
    private String type;
    private List<Double> coordinates;

    public Geometry(String pTipo, List<Double> pCoordenadas){
        type = pTipo;
        coordinates = pCoordenadas;
    }
    public List<Double> DarCoordenadas(){
        return coordinates;
    }
    public void ponerCoordenadas(List<Double> pCoord){
        coordinates = pCoord;
    }
    public String type(){
        return type;
    }
    public void ponerType(String pTipo){
        type = pTipo;
    }
    @Override
    public String toString(){
        String str = "type : "+this.type+",\n"+
                "geometry : ["+this.coordinates.get(0)+","+this.coordinates.get(1)+","+this.coordinates.get(2)+"]\n";
        return str;
    }

}
