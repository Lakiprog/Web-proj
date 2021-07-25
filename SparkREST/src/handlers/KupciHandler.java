package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.Admin;
import domain.Kupac;

public class KupciHandler {
	
	private static class Kupci{
		public HashMap<Integer, Kupac> kupci = new HashMap<>();
	}
	
	private ArrayList<Kupac> kupci;
	private HashMap<Integer, Kupac> ucitani;
	private final String KUPCI_FAJL = "data/kupci.json";
	
	public KupciHandler() {
		kupci = new ArrayList<>();
		ucitajKupce();
	}
	
	public ArrayList<Kupac> getkupci() {
		return kupci;
	}
	
	private void ucitajKupce() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(KUPCI_FAJL), Kupci.class).kupci;
			for (Kupac kupac : ucitani.values()) {
				if(!kupac.isObrisan()) {
					kupci.add(kupac);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajKupca(Kupac k) {
		kupci.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajKupca(Kupac k) {
		ucitani.put(k.getId(), k);
		for (Kupac kupac : kupci) {
			if(k.getId() == kupac.getId()){
				kupac = k;
			}
		}
		sacuvaj();
	}
	
	public void brisiKupcaLogicki(int id) {
		for(int i = 0; i < kupci.size(); i++) {
			if(kupci.get(i).getId() == id) {
				kupci.remove(i);
			}
		}
		for (Kupac kupac : ucitani.values()) {
			if(kupac.getId() == id) {
				kupac.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiKupcaFizicki(int id) {
		ucitani.remove(id);
		kupci.clear();
		for (Kupac kupac : ucitani.values()) {
			if(!kupac.isObrisan()) {
				kupci.add(kupac);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Kupci k = new Kupci();
			k.kupci = ucitani;
			mapper.writeValue(new File(KUPCI_FAJL), k);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int nextId() {
		int next = 0;
		for (Kupac kupac : ucitani.values()) {
			if(kupac.getId() > next) {
				next = kupac.getId();
			}
		}
		return next+1;
	}
	
	public Kupac poId(Integer id) {
		return ucitani.get(id);
	}
	
	public void blokiraj(int id) {
		ucitani.get(id).setBlokiran(true);
		for (Kupac kupac : kupci) {
			if(kupac.getId() == id) {
				kupac.setBlokiran(true);
				break;
			}
		}
		sacuvaj();
	}
	
	public void odBlokiraj(int id) {
		ucitani.get(id).setBlokiran(false);
		for (Kupac kupac : kupci) {
			if(kupac.getId() == id) {
				kupac.setBlokiran(false);
				break;
			}
		}
		sacuvaj();
	}
	
	public Kupac poKorisnickomImenu(String kIme) {
		for (Kupac kupac : kupci) {
			if(kupac.getkIme().equals(kIme) && !kupac.isObrisan()) {
				return kupac;
			}
		}
		return null;
	}
	
}
