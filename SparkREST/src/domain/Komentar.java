package domain;

public class Komentar {
	private int id;
	private Kupac kupac;
	private Manifestacija manifestacija;
	private String komentar;
	private int ocena;
	private boolean obrisan;

	public Komentar(int id, Kupac kupac, Manifestacija manifestacija, String komentar, int ocena) {
		super();
		this.kupac = kupac;
		this.manifestacija = manifestacija;
		this.komentar = komentar;
		this.ocena = ocena;
		this.id = id;
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

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public Komentar() {
	}

}
