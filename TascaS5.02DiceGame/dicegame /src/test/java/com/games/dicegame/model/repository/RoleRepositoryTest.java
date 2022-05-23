package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void itShouldReturnRoleIfExistsByName() {
        //give
        String roleName = "ROLE_USER";
        Role role = new Role(null, roleName);
        roleRepository.save(role);
        //when
        Role expected = roleRepository.findByName(roleName);
        //then
        assertThat(expected.getName()).isEqualTo(roleName);

    }

}