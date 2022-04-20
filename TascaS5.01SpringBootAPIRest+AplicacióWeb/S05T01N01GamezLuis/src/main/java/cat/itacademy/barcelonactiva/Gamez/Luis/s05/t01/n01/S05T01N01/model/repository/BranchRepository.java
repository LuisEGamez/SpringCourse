package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.repository;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    Branch findByBranchName(String branchName);

}
