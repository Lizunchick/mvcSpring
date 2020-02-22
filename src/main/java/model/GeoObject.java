package model;


import org.dom4j.tree.AbstractEntity;


import javax.persistence.*;

@Entity
@Table(name="DOT")
public class GeoObject extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Xvalue")
    private Double Xcoord;
    @Column(name = "Yvalue")
    private Double Ycoord;
    @Column(name = "IDfigure")
    private Integer idfig;
    private Type type;

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

    public void setIdfig(Integer idMain) {
        this.idfig = idMain;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public GeoObject() {
    }

    public GeoObject(Type type, double x, double y) {
        this.type = type;
        this.Xcoord = x;
        this.Ycoord = y;
    }

    @Override
    public String toString() {
        return "models.GeoObject{" +
                "id=" + id +
                ", idmain="+ idfig +", type='" + type + '\'' +
                ", coords.x=" + Xcoord +
                ", coords.y=" + Ycoord +
                '}';
    }
}
