package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "branches")
@ToString
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer pk_BranchID;
    @Column(name = "branchname")
    @Getter @Setter
    private String branchName;
    @Column(name = "branchcountry")
    @Getter @Setter
    private String branchCountry;

    public Branch(String nameBranch, String countryBranch) {

        this.branchName = nameBranch;
        this.branchCountry = countryBranch;
    }
}
