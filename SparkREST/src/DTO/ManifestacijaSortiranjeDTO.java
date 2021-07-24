package DTO;

import java.time.LocalDateTime;

import domain.TipManifestacije;

public class ManifestacijaSortiranjeDTO {
	private String naziv, adresa, sortiraj, sortirajPo, tip, datumOd, datumDo;
	private int cenaMin, cenaMax;
	
	
	
	public ManifestacijaSortiranjeDTO() {
	}

	
	
	public ManifestacijaSortiranjeDTO(String naziv, String adresa, String sortiraj, String sortirajPo,
			String datumOd, String datumDo, int cenaMin, int cenaMax, String tip) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.sortiraj = sortiraj;
		this.sortirajPo = sortirajPo;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.cenaMin = cenaMin;
		this.cenaMax = cenaMax;
		this.tip = tip;
	}



	public String getTip() {
		return tip;
	}



	public void setTip(String tip) {
		this.tip = tip;
	}



	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getSortiraj() {
		return sortiraj;
	}
	public void setSortiraj(String sortiraj) {
		this.sortiraj = sortiraj;
	}
	public String getSortirajPo() {
		return sortirajPo;
	}
	public void setSortirajPo(String sortirajPo) {
		this.sortirajPo = sortirajPo;
	}
	public String getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(String datumOd) {
		this.datumOd = datumOd;
	}
	public String getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(String datumDo) {
		this.datumDo = datumDo;
	}
	public int getCenaMin() {
		return cenaMin;
	}
	public void setCenaMin(int cenaMin) {
		this.cenaMin = cenaMin;
	}
	public int getCenaMax() {
		return cenaMax;
	}
	public void setCenaMax(int cenaMax) {
		this.cenaMax = cenaMax;
	}
}
