package util;

import java.time.LocalDateTime;
import java.util.Comparator;

import domain.Karta;

public class KartePoDatumuSort implements Comparator<Karta>{

	@Override
	public int compare(Karta first, Karta second) {
		LocalDateTime firstDate = LocalDateTime.parse(first.getDatumVreme());
		LocalDateTime secondDate = LocalDateTime.parse(second.getDatumVreme());
		return firstDate.compareTo(secondDate);
	}

}
