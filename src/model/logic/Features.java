package model.logic;

import java.awt.*;
public class Features implements Comparable<Features> {
    private String type;
    private Properties properties;
    private Geometry geometry;

    public Features(String tipo, Properties properties, Geometry geometry) {
        this.type = tipo;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String DarTipo() {
        return type;
    }

    public void setTipo(String type) {
        this.type = type;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        String str = "Caracteristicas:\n" +
                "\t" + this.properties.toString() +
                "Coordenadas GPS:\n" +
                "\t" + this.geometry.toString();
        return str;
    }


    @Override
    public int compareTo(Features that) {
        if (this.properties.getFECHA_HORA().equalsIgnoreCase(that.properties.getFECHA_HORA())) {
            if (this.properties.getOBJECTID() > that.properties.getOBJECTID()) return 1;
            if (this.properties.getOBJECTID().equals(that.properties.getOBJECTID())) return 0;
            if (this.properties.getOBJECTID() < that.properties.getOBJECTID()) return -1;
        } else {
            String[] actual = this.properties.getFECHA_HORA().split("/");
            int mes = Integer.parseInt(actual[1]);
            int dia = Integer.parseInt(actual[2]);
            String[] comp = that.properties.getFECHA_HORA().split("/");
            int mComp = Integer.parseInt(comp[1]);
            int dComp = Integer.parseInt(comp[2]);
            if (mes == mComp) {
                if (dia > dComp) return 1;
                if (dia < dComp) return -1;
            }
            if (mes > mComp) return 1;
            if (mes < mComp) return -1;
        }
        return -2;
    }

}
