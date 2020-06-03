package model.logic.estaciones;

import org.jetbrains.annotations.NotNull;

public class FeaturesEstaciones implements Comparable<FeaturesEstaciones> {
    private String type;
    private Integer id;
    private GeometryEstaciones geometry;
    private PropertiesEstaciones properties;


    public FeaturesEstaciones(String tipo, Integer id, PropertiesEstaciones properties, GeometryEstaciones geometry){
        this.type = tipo;
        this.id = id;
        this.geometry = geometry;
        this.properties = properties;

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public GeometryEstaciones getGeometry(){
        return geometry;
    }
    public void setGeometry(GeometryEstaciones geometry) {
        this.geometry = geometry;
    }
    public PropertiesEstaciones getProperties() {
        return properties;
    }
    public void setProperties(PropertiesEstaciones properties) {
        this.properties = properties;
    }
    @Override
    public String toString(){ // modificar
        String str =
                "ID:"+ this.id + "\n" +
                        "Coordenadas GPS:\n"+
                        "\t"+this.geometry.toString() +
                        "Caracteristicas:\n"+
                        "\t"+this.properties.toString();
        return str;
    }
    @Override
    public int compareTo(@NotNull FeaturesEstaciones that) {
        return this.properties.getOBJECTID().compareTo(that.properties.getOBJECTID());
    }
}
