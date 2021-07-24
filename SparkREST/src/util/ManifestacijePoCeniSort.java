package util;

import java.util.Comparator;

import domain.Manifestacija;

public class ManifestacijePoCeniSort implements Comparator<Manifestacija>{

	@Override
	public int compare(Manifestacija first, Manifestacija second) {
		return Double.compare(first.getCenaRegular(), second.getCenaRegular());
	}

}
