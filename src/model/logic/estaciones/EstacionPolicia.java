package model.logic.estaciones;

import java.util.List;

public class EstacionPolicia {
    private String type;
    private crs crs;
    private List<FeaturesEstaciones> features;

    public class crs{
        private String type;
        private Properties1 properties;
        public crs(String tipo, Properties1 properties1){
            this.type = tipo;
            this.properties = properties1;
        }
        public void setType(String type){
            this.type = type;
        }
        public void setProperties(Properties1 properties){
            this.properties = properties;
        }
        public Properties1  getProperties(){
            return properties;
        }
        class Properties1{
            private String name;
            public void setName(String name){
                this.name = name;
            }
        }
    }
    public EstacionPolicia(String pTipo, String pName, crs crs , List<FeaturesEstaciones> featuresList){
        type = pTipo;
        this.crs = crs;
        this.features = featuresList;
    }
    public String darType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public crs darCrs(){
        return crs;
    }
    public void setCrs(EstacionPolicia.crs crs) {
        this.crs = crs;
    }
    public List<FeaturesEstaciones> darListaFeatures(){
        return features;
    }
    public void setFeatures(List<FeaturesEstaciones> features){
        this.features = features;
    }

}
