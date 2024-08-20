package dev.jilani.bms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import dev.jilani.bms.dto.BookingRequestDto;
import dev.jilani.bms.dto.BookingResponseDto;
import dev.jilani.bms.dto.ResponseStatus;
import dev.jilani.bms.exception.NotFoundException;
import dev.jilani.bms.service.BookingService;
import dev.jilani.bmsm.model.Booking;

@Controller
public class BookingController {

	private BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto)  {
		BookingResponseDto bookingResponseDto = new BookingResponseDto();
		try {
			Booking booking = bookingService.creteBooking(bookingRequestDto.getUserId(), bookingRequestDto.getShowId(),
					bookingRequestDto.getShowSeatIds());
			bookingResponseDto.setBookingId(booking.getId());
			bookingResponseDto.setResponseStatus(ResponseStatus.SUCESS);
		} catch (NotFoundException e) {
			bookingResponseDto.setResponseStatus(ResponseStatus.FAILER);
		}
		
		
		return bookingResponseDto;
	}
}
