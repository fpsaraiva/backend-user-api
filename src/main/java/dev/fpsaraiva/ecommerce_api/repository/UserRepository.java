package dev.fpsaraiva.ecommerce_api.repository;

import dev.fpsaraiva.ecommerce_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> queryByNameLike(String name);

    Optional<User> findByEmail(String email);
}
