package dev.jilani.bms.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingResponseDto {

	private long bookingId;
	private ResponseStatus responseStatus;
}
