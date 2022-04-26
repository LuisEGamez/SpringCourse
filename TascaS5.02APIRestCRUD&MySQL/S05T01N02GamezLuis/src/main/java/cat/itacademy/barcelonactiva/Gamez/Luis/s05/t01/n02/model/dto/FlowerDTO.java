package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n02.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@ToString
public class FlowerDTO {

    @Getter @Setter
    private Integer pk_flowerID;
    @Getter @Setter
    private String flowerName;
    @Getter @Setter
    private String flowerCountry;
    @Getter @Setter
    private String flowerType;
    private final List<String> countries = Arrays.asList("Austria","Belgium", "Bulgaria", "Croatia", "Cyprus", "Czechia",
                                                            "Denmark", "Estonia", "Finland", " France", "Germany", "Greece",
                                                            "Hungary", "Ireland", "Italy", "Latvia", "Luxembourg", "Malta",
                                                            "Netherlands", "Poland", "Portugal", "Romania", "Slovakia", "Slovenia",
                                                            "Spain", "Sweden");

    public FlowerDTO(String branchName, String branchCountry) {
        this.flowerName = branchName;
        this.flowerCountry = branchCountry;

    }

    public FlowerDTO(Integer pk_flowerID, String flowerName, String flowerCountry) {
        this.pk_flowerID = pk_flowerID;
        this.flowerName = flowerName;
        this.flowerCountry = flowerCountry;
        if(countries.stream().anyMatch(country -> country.equalsIgnoreCase(flowerCountry))){
            flowerType = "EU";
        }else{
            flowerType = "NO EU";
        }
    }
}
