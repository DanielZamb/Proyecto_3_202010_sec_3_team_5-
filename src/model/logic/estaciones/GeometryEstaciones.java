package model.logic.estaciones;

import java.util.List;

public class GeometryEstaciones {
    private String type;
    private List<Double> coordinates;

    public GeometryEstaciones(String pTipo, List<Double> pCoordenadas){
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
                "geometry : ["+this.coordinates.get(0)+","+this.coordinates.get(1)+"]\n";
        return str;
    }
}
