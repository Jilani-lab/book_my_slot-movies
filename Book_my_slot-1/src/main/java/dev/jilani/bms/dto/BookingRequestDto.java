package dev.jilani.bms.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequestDto {

	private long userId;
	private long showId;
	private List<Long> showSeatIds;
}
