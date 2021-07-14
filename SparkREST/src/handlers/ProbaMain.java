package handlers;

import java.time.LocalDateTime;
import java.util.HashMap;

import domain.Adresa;
import domain.Karta;
import domain.Komentar;
import domain.Kupac;
import domain.Lokacija;
import domain.Manifestacija;
import domain.Pol;
import domain.Prodavac;
import domain.StatusKarte;
import domain.StatusManifestacije;
import domain.TipKarte;
import domain.TipKupca;
import domain.TipManifestacije;
import domain.TipMedalje;


public class ProbaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KarteHandler handler = new KarteHandler();
		//handler.dodajKartu(new Karta(handler.nextId(), "2021.08.10", 1, null, 1000, StatusKarte.REZERVISANA, TipKarte.REGULAR));
		//handler.dodajKartu(new Karta(handler.nextId(), "2021.09.10", 2, null, 300, StatusKarte.REZERVISANA, TipKarte.FANPIT));
		

		//KupciHandler handler = new KupciHandler();
		//handler.dodajKupca(new Kupac(handler.nextId(), "markuzic", "markuzaRage", "Petar", "Markovic", LocalDateTime.now(), Pol.ZENSKO));
		//handler.dodajKupca(new Kupac(handler.nextId(), "micko", "loznica", "Micko", "Jarak", LocalDateTime.now(), Pol.MUSKO));
		
		//ProdavacHandler handler = new ProdavacHandler();
		//handler.dodajProdavca(new Prodavac(handler.nextId(), "machiavelli", "lozinka", "Francis", "Bacon", LocalDateTime.now(), Pol.MUSKO));
		//handler.dodajProdavca(new Prodavac(handler.nextId(), "marco", "polo", "Marco", "Polo", LocalDateTime.now(), Pol.MUSKO));
		
		//ManifestacijaHandler handler = new ManifestacijaHandler();
		//handler.dodajManifestaciju(new Manifestacija(handler.nextId(), "Koncert Ramba Amadeusa", "2021.10.10", "../static/css/rambo.jpg", null, 1000, 500, StatusManifestacije.AKTIVNO, TipManifestacije.KONCERT));
		//handler.dodajManifestaciju(new Manifestacija(handler.nextId(), "Dombos Festival", "2021.07.08", "../static/css/dombos.jpg", null, 10000, 10, StatusManifestacije.AKTIVNO, TipManifestacije.FESTIVAL));
	}

}
