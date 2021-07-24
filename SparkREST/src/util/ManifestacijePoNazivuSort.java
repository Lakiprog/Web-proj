package util;

import java.util.Comparator;

import domain.Manifestacija;

public class ManifestacijePoNazivuSort implements Comparator<Manifestacija>{

	@Override
	public int compare(Manifestacija first, Manifestacija second) {
		return first.getNaziv().compareTo(second.getNaziv());
	}

}
