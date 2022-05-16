package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE users SET username = ?1 WHERE id = ?2")
    void updateUser(String username, Integer id );

    List<AppUser> findAllByOrderBySuccessRateDesc();

    AppUser findFirstByOrderBySuccessRateAsc();

    AppUser findFirstByOrderBySuccessRateDesc();
}
