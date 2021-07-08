package domain;

public class Karta {
	private String id, datumVreme, imeKupca, prezimeKupca;
	private Manifestacija manifestacija;
	private double cena;
	private StatusKarte status;
	private TipKarte tip;
	
	public Karta(String id, String datumVreme, String imeKupca, String prezimeKupca, Manifestacija manifestacija,
			double cena, StatusKarte status, TipKarte tip) {
		super();
		this.id = id;
		this.datumVreme = datumVreme;
		this.imeKupca = imeKupca;
		this.prezimeKupca = prezimeKupca;
		this.manifestacija = manifestacija;
		this.cena = cena;
		this.status = status;
		this.tip = tip;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatumVreme() {
		return datumVreme;
	}
	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}
	public String getImeKupca() {
		return imeKupca;
	}
	public void setImeKupca(String imeKupca) {
		this.imeKupca = imeKupca;
	}
	public String getPrezimeKupca() {
		return prezimeKupca;
	}
	public void setPrezimeKupca(String prezimeKupca) {
		this.prezimeKupca = prezimeKupca;
	}
	public Manifestacija getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public StatusKarte getStatus() {
		return status;
	}
	public void setStatus(StatusKarte status) {
		this.status = status;
	}
	public TipKarte getTip() {
		return tip;
	}
	public void setTip(TipKarte tip) {
		this.tip = tip;
	}
	
	public void setCenaForTip() {
		if(tip == TipKarte.FANPIT) {
			cena = manifestacija.getCenaRegular() * 2;
		}else if(tip == TipKarte.VIP) {
			cena = manifestacija.getCenaRegular() * 4;
		}else {
			cena = manifestacija.getCenaRegular();
		}
	}
}
