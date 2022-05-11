package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
