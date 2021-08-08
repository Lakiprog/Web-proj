package util;

import java.util.Comparator;

import domain.Karta;

public class KartePoManifestacijiSort implements Comparator<Karta> {

	@Override
	public int compare(Karta first, Karta second) {
		return Integer.compare(first.getIdManifestacije(), second.getIdManifestacije());
	}
	
}
