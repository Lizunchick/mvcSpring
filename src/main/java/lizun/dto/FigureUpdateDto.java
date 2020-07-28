package lizun.dto;

import lizun.model.Dot;

import java.util.List;

public class FigureUpdateDto {
    private Integer id;
    private List<Dot> coords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Dot> getCoords() {
        return coords;
    }


}
