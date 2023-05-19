package com.rentabook.repository;

import com.rentabook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u where u.email = :email")
    User findUserByEmail(@Param("email") String email);

    User findUserByUsername(String username);
}
