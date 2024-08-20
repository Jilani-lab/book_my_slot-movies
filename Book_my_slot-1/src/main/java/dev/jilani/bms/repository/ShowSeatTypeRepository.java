package dev.jilani.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jilani.bmsm.model.Show;
import dev.jilani.bmsm.model.ShowSeatType;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
	
	List<ShowSeatType> findAllByShow(Show show);
}
