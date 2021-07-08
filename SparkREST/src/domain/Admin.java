package domain;

public class Admin extends Korisnik {
	
	public Admin() {}

	public Admin(Integer id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol) {
		super(id, kIme, lozinka, ime, prezime, datumRodjenja, pol);
	}

}
