package lizun.model;


import javax.persistence.*;

@Entity
@Table(name = "DOT")
public class Dot {
    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Xvalue")
    private Float Xcoord;
    @Column(name = "Yvalue")
    private Float Ycoord;

    @ManyToOne
    @JoinColumn(name = "idfigure")
    private Figure figure;

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getXcoord() {
        return Xcoord;
    }

    public void setXcoord(Float xcoord) {
        Xcoord = xcoord;
    }

    public Float getYcoord() {
        return Ycoord;
    }

    public void setYcoord(Float ycoord) {
        Ycoord = ycoord;
    }

}
