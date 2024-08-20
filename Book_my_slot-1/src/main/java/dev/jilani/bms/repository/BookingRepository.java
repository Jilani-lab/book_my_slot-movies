package dev.jilani.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jilani.bmsm.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Override
	Booking save(Booking booking);
}
