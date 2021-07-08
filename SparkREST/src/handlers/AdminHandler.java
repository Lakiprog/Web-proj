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

public class AdminHandler {
	
	private static class Admini{
		public HashMap<Integer, Admin> admini = new HashMap<>();
	}
	
	private ArrayList<Admin> admini;
	private HashMap<Integer, Admin> ucitani;
	private final String ADMINI_FAJL = "data/admini.json";
	
	public AdminHandler() {
		admini = new ArrayList<>();
		ucitajAdmine();
	}
	
	public ArrayList<Admin> getAdmini() {
		return admini;
	}
	
	private void ucitajAdmine() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(ADMINI_FAJL), Admini.class).admini;
			for (Admin admin : ucitani.values()) {
				if(!admin.isObrisan()) {
					admini.add(admin);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajAdmina(Admin k) {
		admini.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void brisiAdminaFizicki(Integer id) {
		ucitani.remove(id);
		admini.clear();
		for (Admin admin : ucitani.values()) {
			if(!admin.isObrisan()) {
				admini.add(admin);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Admini k = new Admini();
			k.admini = ucitani;
			mapper.writeValue(new File(ADMINI_FAJL), k);
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
	
	public Admin poId(Integer id) {
		return ucitani.get(id);
	}
	
	public Admin poKorisnickomImenu(String kIme) {
		for (Admin admin : admini) {
			if(admin.getkIme().equals(kIme) && !admin.isObrisan()) {
				return admin;
			}
		}
		return null;
	}
	
}
