package dev.jilani.bms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import dev.jilani.bms.exception.NotFoundException;
import dev.jilani.bms.repository.BookingRepository;
import dev.jilani.bms.repository.ShowRepository;
import dev.jilani.bms.repository.ShowSeatRepository;
import dev.jilani.bms.repository.ShowSeatTypeRepository;
import dev.jilani.bms.repository.UserRepository;
import dev.jilani.bmsm.model.Booking;
import dev.jilani.bmsm.model.BookingStatus;
import dev.jilani.bmsm.model.Show;
import dev.jilani.bmsm.model.ShowSeat;
import dev.jilani.bmsm.model.ShowSeatStatus;
import dev.jilani.bmsm.model.User;

@Service
public class BookingService {
	private UserRepository userRepository;
	private BookingRepository bookingRepository;
	private ShowRepository showRepository;
	private ShowSeatRepository showSeatRepository;
	private PriceCalculator priceCalculator;

	public BookingService(UserRepository userRepository, BookingRepository bookingRepository,
			ShowRepository showRepository, PriceCalculator priceCalculator, ShowSeatRepository showSeatRepository) {
		this.bookingRepository = bookingRepository;
		this.showRepository = showRepository;
		this.userRepository = userRepository;
		this.showSeatRepository = showSeatRepository;
		this.priceCalculator = priceCalculator;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Booking creteBooking(long userId, long showId, List<Long> showSeatId) throws NotFoundException {
		/*
		 * 1. Get the user with the given userId. 2. Get the show with the given showId.
		 * 3. Get the List of showSeats with the given id's. ----------TAKE A
		 * LOCK--------------- 4. Check if all the seats are available or not. 5. If
		 * not, throw an exception. 6. If yes, Mark the status of all the seats as
		 * BLOCKED. ----------RELEASE THE LOCK--------------- 7. Save the changes in the
		 * DB as well. 8. Create the booking with pending status. [save booking obj to
		 * DB.] 9. Return the booking object.
		 */

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User with id: " + userId + " not found");
		}
		User user = optionalUser.get();

		Optional<Show> optionalShow = showRepository.findById(showId);
		if (optionalShow.isEmpty()) {
			throw new NotFoundException("Show with id: " + showId + " not found");
		}
		Show show = optionalShow.get();

		List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatId);
		for (ShowSeat showSeat : showSeats) {
			if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
				throw new RuntimeException("Show Seat with id : " + showSeat.getId() + " isn't available.");
			}
		}
		for (ShowSeat showSeat : showSeats) {
			showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
			showSeatRepository.save(showSeat);
		}

		Booking booking = new Booking();
		booking.setBookingStatus(BookingStatus.PENDING);
		booking.setCreatedAt(new Date());
		booking.setUser(user);
		booking.setShow(show);
		booking.setPayments(new ArrayList());
		booking.setShowSeats(showSeats);
		booking.setAmount(priceCalculator.calculatePrice(show, showSeats));

		return bookingRepository.save(booking);

	}
}
