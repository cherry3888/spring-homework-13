package ru.cherry.springhomework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cherry.springhomework.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
