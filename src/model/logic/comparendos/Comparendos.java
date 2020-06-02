package model.logic.comparendos;

import java.util.List;

public class Comparendos {
    private String type;
    private String name;
    private crs crs;
    private List<Features> features;

    public class crs{
        private String type;
        private Properties1 properties;
        public crs(String tipo, Properties1 properties1){
            this.type = tipo;
            this.properties = properties1;
        }
        public String darTipo(){
            return type;
        }
        public Properties1  darProperties1(){
            return properties;
        }
    }
    public class Properties1{
        private String name;
        public Properties1(String pName){
            name = pName;
        }
        public String darName(){
            return name;
        }
    }

    public Comparendos(String pTipo, String pName, crs crs , List<Features> featuresList){
        type = pTipo;
        name = pName;
        this.crs = crs;
        this.features = featuresList;
    }
    public String darType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String darNombre(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public crs darCrs(){
        return crs;
    }

    public void setCrs(Comparendos.crs crs) {
        this.crs = crs;
    }

    public List<Features> darListaFeatures(){
        return features;
    }

    public void setFeatures(List<Features> features){
        this.features = features;
    }
}
// te quiero bro no homo, bromance macho pecho peludo lomo plateado