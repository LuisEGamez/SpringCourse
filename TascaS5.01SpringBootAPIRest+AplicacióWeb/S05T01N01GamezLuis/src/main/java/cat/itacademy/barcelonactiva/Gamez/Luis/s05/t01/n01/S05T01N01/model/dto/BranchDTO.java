package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.dto;

import lombok.*;


import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@ToString
public class BranchDTO{

    @Getter @Setter
    private Integer pk_BranchID;
    @Getter @Setter
    private String branchName;
    @Getter @Setter
    private String branchCountry;
    @Getter @Setter
    private String branchType;
    private final List<String> countries = Arrays.asList("Austria","Belgium", "Bulgaria", "Croatia", "Cyprus", "Czechia",
                                                            "Denmark", "Estonia", "Finland", " France", "Germany", "Greece",
                                                            "Hungary", "Ireland", "Italy", "Latvia", "Luxembourg", "Malta",
                                                            "Netherlands", "Poland", "Portugal", "Romania", "Slovakia", "Slovenia",
                                                            "Spain", "Sweden");

    public BranchDTO(String branchName, String branchCountry) {
        this.branchName = branchName;
        this.branchCountry = branchCountry;

    }

    public BranchDTO(Integer pk_BranchID, String branchName, String branchCountry) {
        this.pk_BranchID = pk_BranchID;
        this.branchName = branchName;
        this.branchCountry = branchCountry;
        if(countries.stream().anyMatch(country -> country.equalsIgnoreCase(branchCountry))){
            branchType = "EU";
        }else{
            branchType = "NO EU";
        }
    }
}
