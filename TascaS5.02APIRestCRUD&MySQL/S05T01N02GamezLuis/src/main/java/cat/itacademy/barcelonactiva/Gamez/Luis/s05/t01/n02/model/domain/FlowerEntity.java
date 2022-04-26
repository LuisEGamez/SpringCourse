package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flowers")
@ToString
@NoArgsConstructor
public class FlowerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer pk_FlowerID;
    @Column(name = "flowername")
    @Getter @Setter
    private String flowerName;
    @Column(name = "branchcountry")
    @Getter @Setter
    private String flowerCountry;

    public FlowerEntity(String flowerName, String flowerCountry) {

        this.flowerName = flowerName;
        this.flowerCountry = flowerCountry;
    }
}
