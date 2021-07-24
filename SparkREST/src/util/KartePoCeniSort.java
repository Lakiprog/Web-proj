package util;

import java.util.Comparator;

import domain.Karta;

public class KartePoCeniSort implements Comparator<Karta> {

	@Override
	public int compare(Karta first, Karta second) {
		return Double.compare(first.getCena(), second.getCena());
	}

}
