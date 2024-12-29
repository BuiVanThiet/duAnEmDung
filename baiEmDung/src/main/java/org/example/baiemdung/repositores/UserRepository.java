package org.example.baiemdung.repositores;

import org.example.baiemdung.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select use from User use where use.numberPhone = :numberPhone")
    User getUserByNumberPhone(@Param("numberPhone") String numberPhone);

    @Override
    <S extends User> S save(S entity);
}
