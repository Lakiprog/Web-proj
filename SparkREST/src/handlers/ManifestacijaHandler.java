package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Manifestacija;

public class ManifestacijaHandler {
	
	private static class Manifestacije{
		public HashMap<Integer, Manifestacija> manifestacije = new HashMap<>();
	}
	
	private ArrayList<Manifestacija> manifestacije;
	private HashMap<Integer, Manifestacija> ucitani;
	private final String MANIFESTACIJE_FAJL = "data/manifestacije.json";
	
	public ManifestacijaHandler() {
		manifestacije = new ArrayList<>();
		ucitajManifestacije();
	}
	
	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}
	
	private void ucitajManifestacije() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(MANIFESTACIJE_FAJL), Manifestacije.class).manifestacije;
			for (Manifestacija Manifestacija : ucitani.values()) {
				if(!Manifestacija.isObrisan()) {
					manifestacije.add(Manifestacija);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajManifestaciju(Manifestacija k) {
		manifestacije.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajManifestaciju(Manifestacija m) {
		ucitani.put(m.getId(), m);
		for (Manifestacija manifestacija : manifestacije) {
			if(m.getId() == manifestacija.getId()){
				manifestacija = m;
			}
		}
		sacuvaj();
	}
	
	public void brisiManifestacijuLogicki(int id) {
		for(int i = 0; i < manifestacije.size(); i++) {
			if(manifestacije.get(i).getId() == id) {
				manifestacije.remove(i);
			}
		}
		for (Manifestacija Manifestacija : ucitani.values()) {
			if(Manifestacija.getId() == id) {
				Manifestacija.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiManifestacijuFizicki(int id) {
		ucitani.remove(id);
		manifestacije.clear();
		for (Manifestacija Manifestacija : ucitani.values()) {
			if(!Manifestacija.isObrisan()) {
				manifestacije.add(Manifestacija);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Manifestacije k = new Manifestacije();
			k.manifestacije = ucitani;
			mapper.writeValue(new File(MANIFESTACIJE_FAJL), k);
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
		for (Manifestacija Manifestacija : manifestacije) {
			if(Manifestacija.getId() > next) {
				next = Manifestacija.getId();
			}
		}
		return next+1;
	}
	
	
	public Manifestacija poId(Integer id) {
		return ucitani.get(id);
	}
	
	public Manifestacija poNazivu(String naziv) {
		for (Manifestacija Manifestacija : manifestacije) {
			if(Manifestacija.getNaziv().equals(naziv) && !Manifestacija.isObrisan()) {
				return Manifestacija;
			}
		}
		return null;
	}
}
