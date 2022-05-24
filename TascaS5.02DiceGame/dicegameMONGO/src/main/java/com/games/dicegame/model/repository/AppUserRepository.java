package com.games.dicegame.model.repository;

import com.games.dicegame.model.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<AppUser> findAllByOrderBySuccessRateDesc();

    AppUser findFirstByOrderBySuccessRateAsc();

    AppUser findFirstByOrderBySuccessRateDesc();
}
