package DTO;

public class KorisnikSortiranjeDTO {
	private String ime, prezime, kIme, uloga, tip, sortirajPo, sortiraj;
	
	

	public KorisnikSortiranjeDTO() {
		super();
	}

	public KorisnikSortiranjeDTO(String ime, String prezime, String kIme, String uloga, String tip, String sortirajPo,
			String sortiraj) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.kIme = kIme;
		this.uloga = uloga;
		this.tip = tip;
		this.sortirajPo = sortirajPo;
		this.sortiraj = sortiraj;
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

	public String getkIme() {
		return kIme;
	}

	public void setkIme(String kIme) {
		this.kIme = kIme;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getSortirajPo() {
		return sortirajPo;
	}

	public void setSortirajPo(String sortirajPo) {
		this.sortirajPo = sortirajPo;
	}

	public String getSortiraj() {
		return sortiraj;
	}

	public void setSortiraj(String sortiraj) {
		this.sortiraj = sortiraj;
	}
	
	
}
