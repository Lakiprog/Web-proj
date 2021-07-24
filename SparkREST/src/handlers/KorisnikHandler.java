package handlers;

import java.util.ArrayList;
import java.util.Collections;

import DTO.KorisnikSortiranjeDTO;
import domain.Admin;
import domain.Korisnik;
import domain.Kupac;
import domain.Prodavac;
import util.KorisniciPoBodovimaSort;
import util.KorisniciPoImenuSort;
import util.KorisniciPoKorisnickomImenuSort;
import util.KorisniciPoPrezimenuSort;
import util.ManifestacijePoCeniSort;
import util.ManifestacijePoDatumuSort;
import util.ManifestacijePoLokacijiSort;
import util.ManifestacijePoNazivuSort;

public class KorisnikHandler {

	private KupciHandler kupciHandler;
	private AdminHandler adminHandler;
	private ProdavacHandler prodavacHandler;
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public KorisnikHandler() {
		kupciHandler = new KupciHandler();
		adminHandler = new AdminHandler();
		prodavacHandler = new ProdavacHandler();
		
		for (Kupac kupac : kupciHandler.getkupci()) {
			korisnici.add(kupac);
		}
		
		for (Admin admin : adminHandler.getAdmini()) {
			korisnici.add(admin);
		}
		
		for(Prodavac prodavac : prodavacHandler.getProdavci()) {
			korisnici.add(prodavac);
		}
	}
	
	public boolean postojiKorisnickoIme(String kIme) {
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getkIme().equals(kIme)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Korisnik> getKorisnici() {
		return this.korisnici;
	}
	
	public ArrayList<Kupac> getKupci() {
		return kupciHandler.getkupci();
	}
	
	public ArrayList<Admin> getAdmini() {
		return adminHandler.getAdmini();
	}
	
	public ArrayList<Prodavac> getProdavci() {
		return prodavacHandler.getProdavci();
	}
	
	public void addKupac(Kupac k) {
		korisnici.add(k);
		kupciHandler.dodajKupca(k);
	}
	
	public void addAdmin(Admin a) {
		korisnici.add(a);
		adminHandler.dodajAdmina(a);
	}
	
	public void addProdavac(Prodavac p) {
		korisnici.add(p);
		prodavacHandler.dodajProdavca(p);
	}
	
	public void updateKupac(Kupac k) {
		for (Korisnik korisnik : korisnici) {
			if((korisnik instanceof Kupac) && (korisnik.getId() == k.getId())) {
				Kupac kupac = (Kupac) korisnik;
				kupac = k;
				break;
			}
		}
		kupciHandler.azurirajKupca(k);
	}
	
	public void updateAdmin(Admin a) {
		for (Korisnik korisnik : korisnici) {
			if((korisnik instanceof Admin) && (korisnik.getId() == a.getId())) {
				Admin admin = (Admin) korisnik;
				admin = a;
				break;
			}
		}
		adminHandler.azurirajAdmina(a);
	}
	
	public void updateProdavac(Prodavac p) {
		for (Korisnik korisnik : korisnici) {
			if((korisnik instanceof Prodavac) && (korisnik.getId() == p.getId())) {
				Prodavac prodavac = (Prodavac) korisnik;
				prodavac = p;
				break;
			}
		}
		prodavacHandler.azurirajProdavca(p);
	}
	
	public void deleteKupac(Kupac k) {
		for (int i = 0; i < korisnici.size(); i++) {
			if((korisnici.get(i) instanceof Kupac) && (korisnici.get(i).getId() == k.getId())) {
				korisnici.get(i).setObrisan(true);
				break;
			}
		}
		kupciHandler.brisiKupcaLogicki(k.getId());
	}
	
	public void deleteAdmin(Admin k) {
		for (int i = 0; i < korisnici.size(); i++) {
			if((korisnici.get(i) instanceof Admin) && (korisnici.get(i).getId() == k.getId())) {
				korisnici.get(i).setObrisan(true);
				break;
			}
		}
		adminHandler.brisiAdminaLogicki(k.getId());
	}
	
	public void deleteProdavac(Prodavac k) {
		for (int i = 0; i < korisnici.size(); i++) {
			if((korisnici.get(i) instanceof Prodavac) && (korisnici.get(i).getId() == k.getId())) {
				korisnici.get(i).setObrisan(true);
				break;
			}
		}
		prodavacHandler.brisiProdavcaLogicki(k.getId());
	}
	
	public int nextIdKupac() {
		return kupciHandler.nextId();
	}
	
	public int nextIdProdavac() {
		return prodavacHandler.nextId();
	}
	
	public int nextIdAdmin() {
		return adminHandler.nextId();
	}
	
	public Kupac poIdKupac(int id) {
		return kupciHandler.poId(id);
	}
	
	public Admin poIdAdmin(int id) {
		return adminHandler.poId(id);
	}
	
	public Prodavac poIdProdavac(int id) {
		return prodavacHandler.poId(id);
	}
	
	public Kupac poKorisnickomImenuKupac(String kIme) {
		return kupciHandler.poKorisnickomImenu(kIme);
	}
	
	public Admin poKorisnickomImenuAdmin(String kIme) {
		return adminHandler.poKorisnickomImenu(kIme);
	}
	
	public Prodavac poKorisnickomImenuProdavac(String kIme) {
		return prodavacHandler.poKorisnickomImenu(kIme);
	}
	
	public ArrayList<Korisnik> sortiranje(KorisnikSortiranjeDTO kriterijumi){
		ArrayList<Korisnik> k = new ArrayList<>();
		
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getIme().contains(kriterijumi.getIme())) {
				
				if(korisnik.getPrezime().contains(kriterijumi.getPrezime())) {
					
					if(korisnik.getkIme().contains(kriterijumi.getkIme())) {
						
						if(kriterijumi.getUloga().equals("SVE") || korisnik.getUloga().toString().equals(kriterijumi.getUloga())) {
							
							//TODO to za tip da vidimo dal cemo imati uopste, ako da onda jos jedan if
							k.add(korisnik);
							
						}
						
					}
					
				}
				
			}
		}
		
		switch(kriterijumi.getSortirajPo()) {
		case "IME":
			Collections.sort(k, new KorisniciPoImenuSort());
			break;
		case "PREZIME":
			Collections.sort(k, new KorisniciPoPrezimenuSort());
			break;
		case "KIME":
			Collections.sort(k, new KorisniciPoKorisnickomImenuSort());
			break;
		case "BODOVI":
			Collections.sort(k, new KorisniciPoBodovimaSort());
			break;
		}
		
		if(kriterijumi.getSortiraj().equals("OPADAJUCE")) {
			Collections.reverse(k);
		}
		
		
		return k;
	}
}
