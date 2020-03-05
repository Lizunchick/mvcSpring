package lizun.dto;

import lizun.model.Dot;
import lizun.model.Type;

import java.util.List;

public class FigureDto {
    private Integer id;
    private List<Dot> coords;
    private Type type;

    public List<Dot> getCoords() {
        return coords;
    }

    public void setCoords(List<Dot> coords) {
        this.coords = coords;
    }



    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
