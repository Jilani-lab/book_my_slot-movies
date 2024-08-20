package dev.jilani.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jilani.bmsm.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
//  @Override
//  Optional<Show> findById(Long aLong);
}
