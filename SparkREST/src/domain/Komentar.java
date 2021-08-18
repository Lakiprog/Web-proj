package domain;

public class Komentar {
	private int id, idKupac, idManifestacija;
	private String komentar;
	private int ocena;
	private boolean obrisan, odobren, odbijen;

	public Komentar(int id, int kupac, int manifestacija, String komentar, int ocena) {
		super();
		this.idKupac = kupac;
		this.idManifestacija = manifestacija;
		this.komentar = komentar;
		this.ocena = ocena;
		this.id = id;
	}
	
	

	public boolean isOdbijen() {
		return odbijen;
	}



	public void setOdbijen(boolean odbijen) {
		this.odbijen = odbijen;
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

	

	public int getIdKupac() {
		return idKupac;
	}

	public void setIdKupac(int idKupac) {
		this.idKupac = idKupac;
	}

	public int getIdManifestacija() {
		return idManifestacija;
	}

	public void setIdManifestacija(int idManifestacija) {
		this.idManifestacija = idManifestacija;
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

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}
	
	
}
