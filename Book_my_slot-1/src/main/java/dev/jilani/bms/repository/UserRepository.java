package dev.jilani.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jilani.bmsm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Override
	Optional<User> findById(Long userId);

//	    @Override
//	    Optional<User> findBy(Long userId);

	Optional<User> findByEmail(String email);
}
