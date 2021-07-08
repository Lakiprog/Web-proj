package domain;


public abstract class Korisnik {
	private Integer id;
	private String kIme, lozinka, ime, prezime, datumRodjenja;
	private Pol pol;
	private boolean obrisan;
	
	public Korisnik() {
		
	}
	
	
	public Korisnik(Integer id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol) {
		super();
		this.kIme = kIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
}
