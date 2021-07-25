package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.Komentar;

public class KomentariHandler {
	
	private static class Komentari{
		public HashMap<Integer, Komentar> komentari = new HashMap<>();
	}
	
	private ArrayList<Komentar> komentari;
	private HashMap<Integer, Komentar> ucitani;
	private final String KOMENTARI_FAJL = "data/komentari.json";
	
	public KomentariHandler() {
		komentari = new ArrayList<>();
		ucitajKomentare();
	}
	
	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}
	
	private void ucitajKomentare() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(KOMENTARI_FAJL), Komentari.class).komentari;
			for (Komentar komentar : ucitani.values()) {
				if(!komentar.isObrisan()) {
					komentari.add(komentar);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajKomentar(Komentar k) {
		komentari.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajKomentar(Komentar k) {
		ucitani.put(k.getId(), k);
		for (Komentar komentar : komentari) {
			if(k.getId() == komentar.getId()){
				komentar = k;
			}
		}
		sacuvaj();
	}
	
	public void brisiKomentarLogicki(int id) {
		for(int i = 0; i < komentari.size(); i++) {
			if(komentari.get(i).getId() == id) {
				komentari.remove(i);
			}
		}
		for (Komentar komentar : ucitani.values()) {
			if(komentar.getId() == id) {
				komentar.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiKomentarFizicki(int id) {
		ucitani.remove(id);
		komentari.clear();
		for (Komentar komentar : ucitani.values()) {
			if(!komentar.isObrisan()) {
				komentari.add(komentar);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Komentari k = new Komentari();
			k.komentari = ucitani;
			mapper.writeValue(new File(KOMENTARI_FAJL), k);
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
		for (Komentar Komentar : ucitani.values()) {
			if(Komentar.getId() > next) {
				next = Komentar.getId();
			}
		}
		return next+1;
	}
	
	
	public Komentar poId(Integer id) {
		return ucitani.get(id);
	}
	
	public ArrayList<Komentar> komentariPoManifestaciji(int id){
		ArrayList<Komentar> k = new ArrayList<>();
		
		for (Komentar komentar : komentari) {
			if(komentar.getManifestacija().getId() == id) {
				k.add(komentar);
			}
		}
		
		return k;
	}
	
	public ArrayList<Komentar> komentariPoManifestacijama(ArrayList<Integer> ids){
		ArrayList<Komentar> k = new ArrayList<>();
		
		for (Integer id : ids) {
			for (Komentar komentar : komentariPoManifestaciji(id)) {
				k.add(komentar);
			}
		}
		
		return k;
	}
}
