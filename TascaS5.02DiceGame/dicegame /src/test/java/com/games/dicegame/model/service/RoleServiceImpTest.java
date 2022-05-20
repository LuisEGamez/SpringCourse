package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImpTest {


    @Mock RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImp(roleRepository);
    }

    @Test
    void verifyWhenSaveRole() {

        Role role = new Role();

        roleService.saveRole(role);

        verify(roleRepository).save(role);
    }

    @Test
    void verifyWhenFindRoleByName() {

        roleService.findRoleByName("roleNane");

        verify(roleRepository).findByName("roleNane");
    }
}