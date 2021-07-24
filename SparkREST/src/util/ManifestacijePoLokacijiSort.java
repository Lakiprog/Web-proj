package util;

import java.util.Comparator;

import domain.Manifestacija;

public class ManifestacijePoLokacijiSort implements Comparator<Manifestacija> {

	@Override
	public int compare(Manifestacija first, Manifestacija second) {
		return first.getLokacija().getAdresa().toString().compareTo(second.getLokacija().getAdresa().toString());
	}

}
