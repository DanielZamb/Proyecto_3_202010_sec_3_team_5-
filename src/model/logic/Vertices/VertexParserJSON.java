package model.logic.Vertices;

import java.util.List;

public class VertexParserJSON {
    private Integer IDINICIAL;
    private Double LONGITUD;
    private List<Arcos> ARCOS = null;
    private Double LATITUD;

    public Integer getIDINICIAL() {
        return IDINICIAL;
    }

    public void setIDINICIAL(Integer iDINICIAL) {
        this.IDINICIAL = iDINICIAL;
    }

    public Double getLONGITUD() {
        return LONGITUD;
    }

    public void setLONGITUD(Double lONGITUD) {
        this.LONGITUD = lONGITUD;
    }

    public List<Arcos> getARCOS() {
        return ARCOS;
    }

    public void setARCOS(List<Arcos> aRCOS) {
        this.ARCOS = aRCOS;
    }

    public Double getLATITUD() {
        return LATITUD;
    }

    public void setLATITUD(Double lATITUD) {
        this.LATITUD = lATITUD;
    }

}

