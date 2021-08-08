package domain;


public class Manifestacija {
	private int id;
	private String naziv, posterLink;
	private String datumVremePocetka;
	private String datumVremeKraja;
	private Lokacija lokacija;
	private int brMesta;
	private int brSlobodnihMesta;
	private double cenaRegular;
	private StatusManifestacije status;
	private TipManifestacije tip;
	private boolean obrisan;
	private int brojOcena;
	private int sumaOcena;
	
	public Manifestacija() {
		
	}
	
	
	public Manifestacija(int id, String naziv, String datumVremePocetka, String datumVremeKraja, String posterLink, Lokacija lokacija, int brMesta,
			int brSlobodnihMesta, double cenaRegular, StatusManifestacije status, TipManifestacije tip, int brojOcena, int sumaOcena) {
		super();
		this.naziv = naziv;
		this.setDatumVremePocetka(datumVremePocetka);
		this.posterLink = posterLink;
		this.lokacija = lokacija;
		this.brMesta = brMesta;
		this.setBrSlobodnihMesta(brSlobodnihMesta);
		this.cenaRegular = cenaRegular;
		this.status = status;
		this.tip = tip;
		this.id = id;
		this.setBrojOcena(brojOcena);
		this.setSumaOcena(sumaOcena);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getPosterLink() {
		return posterLink;
	}
	public void setPosterLink(String posterLink) {
		this.posterLink = posterLink;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public int getBrMesta() {
		return brMesta;
	}
	public void setBrMesta(int brMesta) {
		this.brMesta = brMesta;
	}
	public double getCenaRegular() {
		return cenaRegular;
	}
	public void setCenaRegular(double cenaRegular) {
		this.cenaRegular = cenaRegular;
	}
	public StatusManifestacije getStatus() {
		return status;
	}
	public void setStatus(StatusManifestacije status) {
		this.status = status;
	}
	public TipManifestacije getTip() {
		return tip;
	}
	public void setTip(TipManifestacije tip) {
		this.tip = tip;
	}


	public String getDatumVremePocetka() {
		return datumVremePocetka;
	}


	public void setDatumVremePocetka(String datumVremePocetka) {
		this.datumVremePocetka = datumVremePocetka;
	}


	public String getDatumVremeKraja() {
		return datumVremeKraja;
	}


	public void setDatumVremeKraja(String datumVremeKraja) {
		this.datumVremeKraja = datumVremeKraja;
	}


	public int getBrojOcena() {
		return brojOcena;
	}


	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}


	public int getSumaOcena() {
		return sumaOcena;
	}


	public void setSumaOcena(int sumaOcena) {
		this.sumaOcena = sumaOcena;
	}


	public int getBrSlobodnihMesta() {
		return brSlobodnihMesta;
	}


	public void setBrSlobodnihMesta(int brSlobodnihMesta) {
		this.brSlobodnihMesta = brSlobodnihMesta;
	}
	
	
}
