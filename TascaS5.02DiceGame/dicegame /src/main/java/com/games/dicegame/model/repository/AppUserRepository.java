package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE users SET username = ?1 WHERE email = ?2")
    void updateUser(String username, String email );

    List<AppUser> findAllByOrderBySuccessRateDesc();
}
