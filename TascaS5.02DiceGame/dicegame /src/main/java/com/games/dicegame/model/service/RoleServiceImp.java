package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class RoleServiceImp implements RoleService{

    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }
}
