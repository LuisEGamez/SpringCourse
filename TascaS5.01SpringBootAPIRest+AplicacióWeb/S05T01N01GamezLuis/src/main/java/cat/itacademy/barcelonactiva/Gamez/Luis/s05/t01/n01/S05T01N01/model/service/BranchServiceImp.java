package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.service;

import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.domain.Branch;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.dto.BranchDTO;
import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImp implements BranchService{

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {

        Branch branch = branchRepository.save(new Branch(branchDTO.getBranchName(), branchDTO.getBranchCountry()));

        return new BranchDTO(branch.getPk_BranchID(), branch.getBranchName(), branch.getBranchCountry());
    }

    @Override
    public BranchDTO updateBranch(String branchName, BranchDTO branchDTO) {

        BranchDTO branchDTO1 = null;

        Branch branch = branchRepository.findByBranchName(branchName);

        if(branch!= null){
            branch.setBranchName(branchDTO.getBranchName());
            branch.setBranchCountry(branchDTO.getBranchCountry());
            branch = branchRepository.save(branch);
            branchDTO1 = new BranchDTO(branch.getPk_BranchID(), branch.getBranchName(), branch.getBranchCountry());
        }

        return branchDTO1;
    }

    @Override
    public void deleteBranch(Integer id) {

        branchRepository.deleteById(id);

    }

    @Override
    public BranchDTO getOne(Integer id) {

        BranchDTO branchDTO = null;
        Branch branch;
        Optional<Branch> branchData = branchRepository.findById(id);

        if(branchData.isPresent()){

            branch = branchData.get();
            branchDTO = new BranchDTO(branch.getPk_BranchID(), branch.getBranchName(), branch.getBranchCountry());

        }


        return branchDTO;
    }

    @Override
    public List<BranchDTO> getAll() {

        List<Branch> branches = new ArrayList<>();
        List<BranchDTO> branchDTOs = new ArrayList<>();

        branchRepository.findAll().forEach(branch -> branches.add(branch));

            for (Branch branch: branches){

                branchDTOs.add(new BranchDTO(branch.getPk_BranchID(), branch.getBranchName(), branch.getBranchCountry()));

            }

        return branchDTOs;
    }
}
