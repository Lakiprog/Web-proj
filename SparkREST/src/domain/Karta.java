package domain;

public class Karta {
	private String datumVreme;
	private int id, idKupca;
	private Manifestacija manifestacija;
	private double cena;
	private StatusKarte status;
	private TipKarte tip;
	private boolean obrisan;
	
	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public Karta(int id, String datumVreme, int idKupca, Manifestacija manifestacija,
			double cena, StatusKarte status, TipKarte tip) {
		super();
		this.id = id;
		this.datumVreme = datumVreme;
		this.idKupca = idKupca;
		this.manifestacija = manifestacija;
		this.cena = cena;
		this.status = status;
		this.tip = tip;
	}

	public String getDatumVreme() {
		return datumVreme;
	}
	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
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
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdKupca() {
		return idKupca;
	}

	public void setIdKupca(int idKupca) {
		this.idKupca = idKupca;
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
