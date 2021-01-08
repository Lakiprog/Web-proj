package beans;

public class TipKupca {
	private TipMedalje tip;
	private double popust;
	private int brBodova;
	
	public TipMedalje getTip() {
		return tip;
	}
	public void setTip(TipMedalje tip) {
		this.tip = tip;
	}
	public double getPopust() {
		return popust;
	}
	public void setPopust(double popust) {
		this.popust = popust;
	}
	public int getBrBodova() {
		return brBodova;
	}
	public void setBrBodova(int brBodova) {
		this.brBodova = brBodova;
	}
	
	public void setForTip(TipMedalje tip) {
		if(tip == TipMedalje.BRONZANI) {
			popust = 5;
			brBodova = 100;
		}else if(tip == TipMedalje.SREBRNI) {
			popust = 10;
			brBodova = 300;
		}else {
			popust = 20;
			brBodova = 1000;
		}
	}
	
	public TipKupca(TipMedalje tip, double popust, int brBodova) {
		super();
		this.tip = tip;
		this.popust = popust;
		this.brBodova = brBodova;
	}
}
