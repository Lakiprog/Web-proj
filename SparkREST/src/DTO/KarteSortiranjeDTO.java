package DTO;

import java.time.LocalDateTime;

public class KarteSortiranjeDTO {
	private String naziv, tip, status, sortirajPo, sortiraj, datumOd, datumDo;
	private double cenaMin, cenaMax;
	
	
	
	public KarteSortiranjeDTO() {
		super();
	}
	public KarteSortiranjeDTO(String naziv, String tip, String status, String sortirajPo, String sortiraj,
			String datumOd, String datumDo, double cenaMin, double cenaMax) {
		super();
		this.naziv = naziv;
		this.tip = tip;
		this.status = status;
		this.sortirajPo = sortirajPo;
		this.sortiraj = sortiraj;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.cenaMin = cenaMin;
		this.cenaMax = cenaMax;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public double getCenaMin() {
		return cenaMin;
	}
	public void setCenaMin(double cenaMin) {
		this.cenaMin = cenaMin;
	}
	public double getCenaMax() {
		return cenaMax;
	}
	public void setCenaMax(double cenaMax) {
		this.cenaMax = cenaMax;
	}
	
	
}
