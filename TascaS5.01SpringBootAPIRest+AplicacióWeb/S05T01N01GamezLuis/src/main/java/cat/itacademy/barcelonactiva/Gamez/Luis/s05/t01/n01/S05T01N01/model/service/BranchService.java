package cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.service;


import cat.itacademy.barcelonactiva.Gamez.Luis.s05.t01.n01.S05T01N01.model.dto.BranchDTO;

import java.util.List;


public interface BranchService {

    BranchDTO saveBranch(BranchDTO branchDTO);

    BranchDTO updateBranch(String name, BranchDTO branchDTO);

    BranchDTO updateBranch( BranchDTO branchDTO);

    void deleteBranch(Integer id);

    BranchDTO getOne(Integer id);

    List<BranchDTO> getAll();


}
