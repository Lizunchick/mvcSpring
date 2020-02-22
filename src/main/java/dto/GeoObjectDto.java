package dto;

import model.Type;

import javax.persistence.Column;
import java.util.LinkedHashMap;

public class GeoObjectDto extends AbstractDto {
    private Type type;
    private Integer id;
    private Double Xcoord;
    private Double Ycoord;
    private Integer idfig;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getXcoord() {
        return Xcoord;
    }

    public void setXcoord(Double xcoord) {
        Xcoord = xcoord;
    }

    public Double getYcoord() {
        return Ycoord;
    }

    public void setYcoord(Double ycoord) {
        Ycoord = ycoord;
    }

    public Integer getIdfig() {
        return idfig;
    }

    public void setIdfig(Integer idfig) {
        this.idfig = idfig;
    }
}


