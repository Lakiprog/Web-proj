package util;

import java.time.LocalDateTime;
import java.util.Comparator;

import domain.Manifestacija;

public class ManifestacijePoDatumuSort implements Comparator<Manifestacija> {

	@Override
	public int compare(Manifestacija first, Manifestacija second) {
		LocalDateTime firstDate = LocalDateTime.parse(first.getDatumVremePocetka());
		LocalDateTime secondDate = LocalDateTime.parse(second.getDatumVremePocetka());
		return firstDate.compareTo(secondDate);
	}

}
