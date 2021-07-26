package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import domain.Admin;
import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Komentar;
import domain.Lokacija;
import domain.Manifestacija;
import domain.Prodavac;
import domain.StatusKarte;
import domain.Uloga;
import DTO.KarteSortiranjeDTO;
import DTO.KartaDTO;
import DTO.KomentarDTO;
import DTO.KorisnikSortiranjeDTO;
import DTO.ManifestacijaDTO;
import DTO.ManifestacijaSortiranjeDTO;
import handlers.KarteHandler;
import handlers.KomentariHandler;
import handlers.KorisnikHandler;
import handlers.KupciHandler;
import handlers.LokacijeHandler;
import handlers.ManifestacijaHandler;
import handlers.WsHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Session;

public class SparkAppMain {

	private static KorisnikHandler usersHandler = new KorisnikHandler();
	private static ManifestacijaHandler manifestationHandler = new ManifestacijaHandler();
	private static LokacijeHandler locationHandler = new LokacijeHandler();
	private static KarteHandler cardHandler = new KarteHandler();
	private static KomentariHandler commentHandler = new KomentariHandler();
	
	private static Gson gson = new Gson();
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		post("/rest/users/logUserIn", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.equals(temp_user)) {
					user.setUloga(temp_user.getUloga());
					user.setId(temp_user.getId());
					req.session().attribute("currentUser", user);
					return "success";
				}
			}

			return "Uneto korisnicko ime i lozinka ne postoje";
		});
		
		post("/rest/users/registerBuyer", (req, res) -> {
			Kupac user = gson.fromJson(req.body(), Kupac.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.getkIme().equals(temp_user.getkIme())) {
					return "Uneto korisnicko ime vec postoji";
				}
			}
			
			user.setId(usersHandler.nextIdKupac());
			usersHandler.addKupac(user);
			return "success";
		});
		
		post("/rest/users/registerSeller", (req, res) -> {
			Prodavac user = gson.fromJson(req.body(), Prodavac.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.getkIme().equals(temp_user.getkIme())) {
					return "Uneto korisnicko ime vec postoji";
				}
			}
			
			user.setId(usersHandler.nextIdProdavac());
			usersHandler.addProdavac(user);
			return "success";
		});

		get("/rest/users/logUserOut", (req, res) -> {
			req.session().invalidate();

			return "success";
		});
		
		post("/rest/users/updateAdmin", (req, res) -> {
			Admin user = gson.fromJson(req.body(), Admin.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.getkIme().equals(temp_user.getkIme())) {
					if((user.getId() != temp_user.getId()) || (!user.getUloga().equals(temp_user.getUloga()))) {
						return "Uneto korisnicko ime vec postoji";
					}
				}
			}
			
			Korisnik u = (Korisnik) req.session().attribute("currentUser");
			u.setkIme(user.getkIme());
			u.setLozinka(user.getLozinka());
			req.session().attribute("currentUser", u);
			
			usersHandler.updateAdmin(user);
			
			return "success";
		});
		
		post("/rest/users/updateKupac", (req, res) -> {
			Kupac user = gson.fromJson(req.body(), Kupac.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.getkIme().equals(temp_user.getkIme())) {
					if((user.getId() != temp_user.getId()) || (!user.getUloga().equals(temp_user.getUloga()))) {
						return "Uneto korisnicko ime vec postoji";
					}
				}
			}
			
			Korisnik u = (Korisnik) req.session().attribute("currentUser");
			u.setkIme(user.getkIme());
			u.setLozinka(user.getLozinka());
			req.session().attribute("currentUser", u);
			
			usersHandler.updateKupac(user);
			
			return "success";
		});
		
		post("/rest/users/updateProdavac", (req, res) -> {
			Prodavac user = gson.fromJson(req.body(), Prodavac.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.getkIme().equals(temp_user.getkIme())) {
					if((user.getId() != temp_user.getId()) || (!user.getUloga().equals(temp_user.getUloga()))) {
						return "Uneto korisnicko ime vec postoji";
					}
				}
			}
			
			Korisnik u = (Korisnik) req.session().attribute("currentUser");
			u.setkIme(user.getkIme());
			u.setLozinka(user.getLozinka());
			req.session().attribute("currentUser", u);
			
			usersHandler.updateProdavac(user);
			
			return "success";
		});
		
		post("/rest/users/block", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			if(user.getUloga().equals(Uloga.KUPAC)) {
				usersHandler.blockKupac(user.getId());
			}else if(user.getUloga().equals(Uloga.PRODAVAC)) {
				usersHandler.blockProdavac(user.getId());
			}else {
				
			}
			
			//ArrayList<Korisnik> users = usersHandler.getKorisnici();

			res.type("application/json");

			return "success";
		});
		
		
		post("/rest/users/unblock", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			if(user.getUloga().equals(Uloga.KUPAC)) {
				usersHandler.unblockKupac(user.getId());
			}else if(user.getUloga().equals(Uloga.PRODAVAC)) {
				usersHandler.unblockProdavac(user.getId());
			}else {
				
			}
			//ArrayList<Korisnik> users = usersHandler.getKorisnici();

			res.type("application/json");

			return "success";
		});
		
		post("/rest/users/delete", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			if(user.getUloga().equals(Uloga.KUPAC)) {
				usersHandler.deleteKupac(user.getId());
			}else if(user.getUloga().equals(Uloga.PRODAVAC)) {
				usersHandler.deleteProdavac(user.getId());
			}else {
				usersHandler.deleteAdmin(user.getId());
			}
			
			//ArrayList<Korisnik> users = usersHandler.getKorisnici();

			res.type("application/json");

			return "success";
		});
		
		get("/rest/users/getCurrentUser", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");

			res.type("application/json");

			return gson.toJson(user);
		});
		
		get("/rest/users/getCurrentUserInfo", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");

			res.type("application/json");
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					return gson.toJson(korisnik);
				}
			}

			return gson.toJson(user);
		});
		
		get("rest/manifestations/getManifestationsProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			Prodavac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Manifestacija> manifestations = manifestationHandler.manifestacijePoIdima(p.getManifestacije());
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});
		
		get("rest/cards/getCardsProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			Prodavac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Karta> cards = cardHandler.kartePoManifestacijama(p.getManifestacije());
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		get("rest/cards/getCardsKupac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			Kupac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Kupac) korisnik;
					break;
				}
			}
			
			ArrayList<Karta> cards = cardHandler.kartePoIdima(p.getKarte());
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("rest/cards/odustani", (req, res) -> {
			KartaDTO c = gson.fromJson(req.body(), KartaDTO.class);
			Karta card = cardHandler.poId(c.getId());
			
			card.setStatus(StatusKarte.ODUSTANAK);
			cardHandler.azurirajKartu(card);
			
			res.type("application/json");

			return "success";
		});
		
		get("rest/comments/getCommentsProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			Prodavac p = null;

			res.type("application/json");
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Komentar> comments = commentHandler.komentariPoManifestacijama(p.getManifestacije());
			ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			for (Komentar k : comments) {
				commentsDTO.add(new KomentarDTO(k));
			}

			res.type("application/json");

			return gson.toJson(commentsDTO);
		});

		get("rest/manifestations/getManifestations", (req, res) -> {
			ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});
		
		post("rest/manifestations/getManifestationsSorted", (req, res) -> {
			ManifestacijaSortiranjeDTO criteria = gson.fromJson(req.body(), ManifestacijaSortiranjeDTO.class);
			ArrayList<Manifestacija> manifestations = manifestationHandler.sortiranje(criteria);
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});
		
		get("rest/users/getUsers", (req, res) -> {
			ArrayList<Korisnik> users = usersHandler.getKorisnici();
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			res.type("application/json");

			return gson.toJson(users);
		});
		
		post("rest/users/getUsersSorted", (req, res) -> {
			KorisnikSortiranjeDTO criteria = gson.fromJson(req.body(), KorisnikSortiranjeDTO.class);
			ArrayList<Korisnik> users = usersHandler.sortiranje(criteria);
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			res.type("application/json");

			return gson.toJson(users);
		});
		
		get("rest/cards/getCards", (req, res) -> {
			ArrayList<Karta> cards = cardHandler.getKarte();
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("rest/cards/getCardsSorted", (req, res) -> {
			KarteSortiranjeDTO criteria = gson.fromJson(req.body(), KarteSortiranjeDTO.class);
			ArrayList<Karta> cards = cardHandler.sortiranje(criteria);
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		get("rest/comments/getComments", (req, res) -> {
			ArrayList<Komentar> comments = commentHandler.getKomentari();
			ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			for (Komentar k : comments) {
				commentsDTO.add(new KomentarDTO(k));
			}

			res.type("application/json");

			return gson.toJson(commentsDTO);
		});
		
		post("rest/comments/delete", (req, res) -> {
			KomentarDTO comment = gson.fromJson(req.body(), KomentarDTO.class);
			commentHandler.brisiKomentarLogicki(comment.getId());

			//ArrayList<Komentar> comments = commentHandler.getKomentari();
			//ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			//for (Komentar k : comments) {
			//	commentsDTO.add(new KomentarDTO(k));
			//}

			res.type("application/json");

			return "success";
		});
		
		post("rest/comments/odobri", (req, res) -> {
			KomentarDTO comment = gson.fromJson(req.body(), KomentarDTO.class);
			Komentar c = commentHandler.poId(comment.getId());
			c.setOdobren(true);
			commentHandler.azurirajKomentar(c);

			//ArrayList<Komentar> comments = commentHandler.getKomentari();
			//ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			//for (Komentar k : comments) {
			//	commentsDTO.add(new KomentarDTO(k));
			//}

			res.type("application/json");

			return "success";
		});
		
		post("/rest/manifestations/createManifestation", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			if(manifestationHandler.checkBadTimes(manifestation.getDatumVremePocetka(), manifestation.getDatumVremeKraja(), manifestation.getLokacija())) {
				return "Vec postoji manifestacija na datoj lokaciji za dato vreme";
			}
			
			//TODO mozda prekopirati sliku u neki nas folder
			
			manifestation.setId(manifestationHandler.nextId());
			manifestationHandler.dodajManifestaciju(manifestation);
			
			for (Lokacija location : locationHandler.getLokacije()) {
				if(location.equals(manifestation.getLokacija())) {
					return "success";
				}
			}
			
			manifestation.getLokacija().setId(locationHandler.nextId());
			locationHandler.dodajLokaciju(manifestation.getLokacija());
			return "success";
		});

		
		post("/rest/manifestations/delete", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			manifestationHandler.brisiManifestacijuLogicki(manifestation.getId());
			//ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			return "success";
		});
		
		post("/rest/manifestations/update", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			manifestationHandler.azurirajManifestaciju(manifestation);;
			//ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			return "success";
		});
		
		post("/rest/manifestations/activate", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			manifestationHandler.aktiviraj(manifestation.getId());;
			//ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			return "success";
		});

		get("/rest/demo/test", (req, res) -> {
			return "Works";
		});
		
		get("/rest/demo/book/:isbn", (req, res) -> {
			String isbn = req.params("isbn");
			return "/rest/demo/book received PathParam 'isbn': " + isbn;
		});

		get("/rest/demo/books", (req, res) -> {
			String num = req.queryParams("num");
			String num2 = req.queryParams("num2");
			return "/rest/demo/book received QueryParam 'num': " + num + ", and num2: " + num2;
		});
		
		get("/rest/demo/testheader", (req, res) -> {
			String cookie = req.headers("Cookie");
			return "/rest/demo/testheader received HeaderParam 'Cookie': " + cookie;
		});
		
		get("/rest/demo/testcookie", (req, res) -> {
			String cookie = req.cookie("pera");
			if (cookie == null) {
				res.cookie("pera", "Perin kolacic");
				return "/rest/demo/testcookie <b>created</b> CookieParam 'pera': 'Perin kolacic'";  
			} else {
				return "/rest/demo/testcookie <i><u>received</u></i> CookieParam 'pera': " + cookie;
			}
		});

		

	}
}
