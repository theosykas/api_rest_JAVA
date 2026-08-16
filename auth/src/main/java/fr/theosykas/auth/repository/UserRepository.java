package fr.theosykas.auth.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.theosykas.auth.model.User;


public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
