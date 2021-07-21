package domain;

import java.time.LocalDateTime;

public class Admin extends Korisnik {
	
	public Admin() {}

	public Admin(Integer id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol, Uloga uloga) {
		super(id, kIme, lozinka, ime, prezime, datumRodjenja, pol, uloga);
	}

}
