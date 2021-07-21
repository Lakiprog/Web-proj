package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Kupac extends Korisnik {
	private TipMedalje tip;
	private int brBodova, brOtkazivanja;
	private ArrayList<Karta> karte;
	private boolean blokiran;

	public Kupac() {}
	
	public Kupac(Integer id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol, Uloga uloga) {
		super(id, kIme, lozinka, ime, prezime, datumRodjenja, pol, uloga);
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

	public int getBrOtkazivanja() {
		return brOtkazivanja;
	}

	public void setBrOtkazivanja(int brOtkazivanja) {
		this.brOtkazivanja = brOtkazivanja;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	
}
