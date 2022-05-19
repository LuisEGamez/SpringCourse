package com.games.dicegame.model.service;

import com.games.dicegame.model.domain.Role;
import org.springframework.stereotype.Service;


public interface RoleService {

    Role saveRole(Role role);

    Role findRoleByName(String roleName);

}
