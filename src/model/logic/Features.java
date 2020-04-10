package model.logic;

public class Features implements Comparable<Features>{
    private String type;
    private Properties properties;
    private Geometry geometry;

    public Features(String tipo, Properties properties, Geometry geometry){
        this.type = tipo;
        this.properties = properties;
        this.geometry = geometry;
    }
    public String DarTipo(){
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

    public Geometry getGeometry(){
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }
    @Override
    public String toString(){
        String str ="Caracteristicas:\n"+
                "\t"+this.properties.toString()+
                "Coordenadas GPS:\n"+
                "\t"+this.geometry.toString();
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
    public int compareToP(Features that){
            int comp = 0;
            String[] thisInfrac = this.getProperties().getINFRACCION().split("");
            String[] thatInfrac = that.getProperties().getINFRACCION().split("");
            Boolean comp1 = thisInfrac[0].equalsIgnoreCase(thatInfrac[0]);
            if (thisInfrac[0].compareToIgnoreCase(thatInfrac[0])>0) return 1;
            if (thisInfrac[0].compareToIgnoreCase(thatInfrac[0])<0) return -1;
            if (comp1) {
                if (thisInfrac.length >1 && thatInfrac.length>1) {
                    if (thisInfrac[1].compareToIgnoreCase(thatInfrac[1])>0) return 1;
                    if (thisInfrac[1].compareToIgnoreCase(thatInfrac[1])<0) return -1;
                    Boolean comp2 = thisInfrac[1].equalsIgnoreCase(thatInfrac[1]);
                    Boolean comp3 = thisInfrac[2].equalsIgnoreCase(thatInfrac[2]);
                    if (comp2) {
                        if (thisInfrac[2].compareToIgnoreCase(thatInfrac[2]) > 0) return 1;
                        if (thisInfrac[2].compareToIgnoreCase(thatInfrac[2]) < 0) return -1;
                    }
                    if (comp1&&comp2&&comp3) return 0;
                }
                if  (thisInfrac.length<thatInfrac.length) return -1;
                if  (thisInfrac.length>thatInfrac.length) return 1;
                else return 0;
            }

        return -2;
        }
        public Boolean compareD(String startDate,String endDate){
            if (this.properties.getFECHA_HORA().equalsIgnoreCase(startDate)||this.properties.getFECHA_HORA().equalsIgnoreCase(endDate)) return true;
            else {
                String[] actual = this.properties.getFECHA_HORA().split("/");
                String[] sDate = startDate.split("/");
                String[] eDate = endDate.split("/");
                int mes,dia,smes,sdia,emes,edia;
                mes = Integer.parseInt(actual[1]);
                dia = Integer.parseInt(actual[2]);
                smes = Integer.parseInt(sDate[1]);
                sdia = Integer.parseInt(sDate[2]);
                emes = Integer.parseInt(eDate[1]);
                edia = Integer.parseInt(eDate[2]);
                if (mes>smes){
                    if (mes<emes){
                        if (dia<edia) return true;
                    }
                }
                if (smes==emes){
                    if (dia>sdia && dia<edia) return true;
                    if (dia==sdia) return true;
                    if (dia==edia) return true;
                    if (sdia == edia)
                        if(dia == edia) return true;
                }
                if (mes == smes){
                    if (dia > sdia) return true;
                    else return false;
                }
                if (mes == emes){
                    if (dia < edia) return true;
                    else return false;
                }
                else return false;
            }
        }
        public int compareToL(Features that){
        int comp =this.getProperties().getLOCALIDAD().compareTo(that.getProperties().getLOCALIDAD());
        if (comp > 0) return 1;
        if (comp < 0) return -1;
        else return 0;
        }
}
