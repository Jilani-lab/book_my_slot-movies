package dev.jilani.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jilani.bmsm.model.ShowSeat;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
	@Override
	List<ShowSeat> findAllById(Iterable<Long> showSeatIds);

	@Override
	ShowSeat save(ShowSeat showSeat);
}
