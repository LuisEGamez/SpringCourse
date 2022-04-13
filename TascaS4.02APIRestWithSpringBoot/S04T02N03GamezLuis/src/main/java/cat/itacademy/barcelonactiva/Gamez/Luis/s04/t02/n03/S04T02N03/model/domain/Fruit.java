package cat.itacademy.barcelonactiva.Gamez.Luis.s04.t02.n03.S04T02N03.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "fruits") // annotation helps us override the collection name by “fruits”.
@ToString // annotation create automatically the toString method
@NoArgsConstructor // annotation indicates there is no empty constructor.
public class Fruit {

    @Id // annotation is for the primary key.
    @Getter
    private String id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int quantityKg;


    public Fruit(String name, int quantityKg) {
        this.name = name;
        this.quantityKg = quantityKg;
    }
}
