package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRegistration(String registration);
    @Query("""
    SELECT u FROM User u 
    LEFT JOIN FETCH u.books 
    WHERE u.id = :id
""")
    Optional<User> findByIdWithBooks(@Param("id") Long id);
}
