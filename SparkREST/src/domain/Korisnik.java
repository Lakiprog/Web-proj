package domain;

public class Korisnik {

	private int id;
	private String kIme, lozinka, ime, prezime;
	private String datumRodjenja;
	private Pol pol;
	private boolean obrisan;
	private Uloga uloga;
	
	public Korisnik() {}
	
	public Korisnik(int id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol, Uloga uloga) {
		super();
		this.kIme = kIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.id = id;
		this.uloga = uloga;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getkIme() {
		return kIme;
	}
	public void setkIme(String kIme) {
		this.kIme = kIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	public Pol getPol() {
		return pol;
	}
	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", kIme=" + kIme + ", lozinka=" + lozinka + ", uloga=" + uloga + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Korisnik other = (Korisnik) obj;
		if (kIme == null) {
			if (other.kIme != null)
				return false;
		} else if (!kIme.equals(other.kIme))
			return false;
		if (lozinka == null) {
			if (other.lozinka != null)
				return false;
		} else if (!lozinka.equals(other.lozinka))
			return false;
		return true;
	}

}
