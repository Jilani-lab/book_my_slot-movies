package dev.jilani.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.jilani.bms.repository.ShowSeatTypeRepository;
import dev.jilani.bmsm.model.Show;
import dev.jilani.bmsm.model.ShowSeat;
import dev.jilani.bmsm.model.ShowSeatType;

@Service
public class PriceCalculator {

	private ShowSeatTypeRepository showSeatTypeRepository;

	PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
		this.showSeatTypeRepository = showSeatTypeRepository;
	}

	public int calculatePrice(Show show, List<ShowSeat> showSeats) {
		int amount = 0;
		List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
		for (ShowSeat showSeat : showSeats) {
			for (ShowSeatType showSeatType : showSeatTypes) {
				if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
					amount += showSeatType.getPrice();
					break;
				}
			}
		}
		return amount;

	}

}
