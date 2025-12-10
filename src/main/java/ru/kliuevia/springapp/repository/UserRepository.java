package ru.kliuevia.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kliuevia.springapp.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByGroupNumberOrderByLoginDesc(Integer groupNumber);

    boolean existsByLogin(String login);

    Optional<User> findByActivationCode(UUID activationCode);

    Optional<User> findByLogin(String login);
}
