package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n02.S04T02N02GamezLuis.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity // annotation indicates that the class is a persistent Java class
@Table(name = "fruits") // annotation provides the table that maps this entity
@ToString // annotation create automatically the toString method
@NoArgsConstructor // annotation indicates there is no empty constructor.
public class Fruit {

    @Id // annotation is for the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO) // annotation is used to define generation strategy for the primary key. GenerationType.AUTO means Auto Increment field.
    @Getter
    private long id;
    @Column(name = "name")
    @Getter @Setter
    private String name;
    @Column(name = "quantityKg")
    @Getter @Setter
    private int quantityKg;


    public Fruit(String name, int quantityKg) {
        this.name = name;
        this.quantityKg = quantityKg;
    }
}
