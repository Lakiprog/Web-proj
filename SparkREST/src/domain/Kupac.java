package domain;

import java.util.ArrayList;

public class Kupac extends Korisnik {
	private TipMedalje tip;
	private int brBodova;
	private ArrayList<Karta> karte;

	public Kupac(String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol, Uloga uloga) {
		super(kIme, lozinka, ime, prezime, datumRodjenja, pol, uloga);
	}

	public TipMedalje getTip() {
		return tip;
	}

	public void setTip(TipMedalje tip) {
		this.tip = tip;
	}

	public int getBrBodova() {
		return brBodova;
	}

	public void setBrBodova(int brBodova) {
		this.brBodova = brBodova;
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}

}
