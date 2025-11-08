package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
