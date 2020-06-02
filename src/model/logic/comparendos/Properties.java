package model.logic.comparendos;

public class Properties {

    private Integer OBJECTID;

    private String FECHA_HORA;

    private String MEDIO_DETECCION;

    private String CLASE_VEHICULO;

    private String TIPO_SERVICIO;

    private String INFRACCION;

    private String DES_INFRACCION;

    private String LOCALIDAD;

    private String MUNICIPIO;

    public Integer getOBJECTID() {
        return OBJECTID;
    }

    public void setOBJECTID(Integer oBJECTID) {
        this.OBJECTID = oBJECTID;
    }

    public String getFECHA_HORA() {
        return FECHA_HORA;
    }

    public void setFECHA_HORA(String fECHAHORA) {
        this.FECHA_HORA = fECHAHORA;
    }

    public String getMEDIO_DETECCION() {
        return MEDIO_DETECCION;
    }

    public void setMEDIO_DETECCION(String mEDIODETE) {
        this.MEDIO_DETECCION = mEDIODETE;
    }

    public String getCLASE_VEHICULO() {
        return CLASE_VEHICULO;
    }

    public void setCLASE_VEHICULO(String cLASEVEHI) {
        this.CLASE_VEHICULO = cLASEVEHI;
    }

    public String getTIPO_SERVICIO() {
        return TIPO_SERVICIO;
    }

    public void setTIPO_SERVICIO(String tIPOSERVI) {
        this.TIPO_SERVICIO = tIPOSERVI;
    }

    public String getINFRACCION() {
        return INFRACCION;
    }

    public void setINFRACCION(String iNFRACCION) {
        this.INFRACCION = iNFRACCION;
    }

    public String getDES_INFRACCION() {
        return DES_INFRACCION;
    }

    public void setDES_INFRACCION(String dESINFRAC) {
        this.DES_INFRACCION = dESINFRAC;
    }

    public String getLOCALIDAD() {
        return LOCALIDAD;
    }

    public void setLOCALIDAD(String lOCALIDAD) {
        this.LOCALIDAD = lOCALIDAD;
    }
    public void setMUNICIPIO(String mUNICIPIO){
        this.MUNICIPIO = mUNICIPIO;
    }
    public String getMUNICIPIO(){
        return MUNICIPIO;
    }

    public String toString(){
        String str ="OBJECTID : "+ this.OBJECTID +",\n"+
                "FECHA_HORA : "+ this.FECHA_HORA +",\n"+
                "MEDIO_DETE : "+ this.MEDIO_DETECCION +",\n"+
                "CLASE_VEHI : "+ this.CLASE_VEHICULO +",\n"+
                "TIPO_SERV : "+ this.TIPO_SERVICIO +",\n"+
                "INFRACCION : "+ this.INFRACCION +",\n"+
                "DESC_INFRAC : "+ this.DES_INFRACCION +",\n"+
                "LOCALIDAD : "+ this.LOCALIDAD +",\n"+
                "MUNICIPIO : "+ this.MUNICIPIO +"\n";
        return str;
    }
}
