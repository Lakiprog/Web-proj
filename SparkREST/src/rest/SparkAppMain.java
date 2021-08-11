package rest;

import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import domain.Admin;
import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Komentar;
import domain.Lokacija;
import domain.Manifestacija;
import domain.Prodavac;
import domain.StatusKarte;
import domain.TipKarte;
import domain.TipMedalje;
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
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SparkAppMain {

	
	private static final int SILVER_POINTS = 3000;
	private static final int GOLD_POINTS = 4000;

	private static final int SILVER_DISCOUNT = 97;
	private static final int GOLD_DISCOUNT = 95;

	private static KorisnikHandler usersHandler = new KorisnikHandler();
	private static ManifestacijaHandler manifestationHandler = new ManifestacijaHandler();
	private static LokacijeHandler locationHandler = new LokacijeHandler();
	private static KarteHandler cardHandler = new KarteHandler();
	private static KomentariHandler commentHandler = new KomentariHandler();
	private static KupciHandler customersHandler = new KupciHandler();
	
	private static Gson gson = new Gson();
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static double getDiscountedPrice(Kupac cust, double price) {
		if (cust.getTip().equals(TipMedalje.SREBRNI))
			price = (price / 100) * SILVER_DISCOUNT;
		else if (cust.getTip().equals(TipMedalje.ZLATNI))
			price = (price / 100) * GOLD_DISCOUNT;
		
		return price;
	}
	
	private static void addPointsToCustomer(Kupac cust, TipKarte type, double regularPrice, int numberOfCards) {
		double cardPrice = regularPrice;

		if (type != TipKarte.REGULAR) {
			if (type == TipKarte.FANPIT)
				cardPrice *= 2;
			else if (type == TipKarte.VIP)
				cardPrice *= 4;
		}

		int numberOfPoints = (int) ((cardPrice / 1000) * 133);
		cust.setBrBodova(cust.getBrBodova() + (numberOfPoints * numberOfCards));

		if (cust.getBrBodova() >= SILVER_POINTS && cust.getBrBodova() < GOLD_POINTS)
			cust.setTip(TipMedalje.SREBRNI);
		else if (cust.getBrBodova() >= GOLD_POINTS)
			cust.setTip(TipMedalje.ZLATNI);
	}

	public static void main(String[] args) throws Exception {
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		
		Runnable resetPenalties = () -> {
			ArrayList<Kupac> customers = customersHandler.getkupci();
			
			System.out.println("Reseting penalties...");
			for (int i = 0; i < customers.size(); ++i) {
					Kupac k = customers.get(i);
					k.setBrOtkazivanja(0);
					customersHandler.azurirajKupca(k);
			}
		};
		
		ScheduledFuture<?> sf = ses.scheduleAtFixedRate(resetPenalties, 0, 30, TimeUnit.DAYS);
		
		post("/rest/users/logUserIn", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			res.type("application/json");

			for (Korisnik temp_user : usersHandler.getKorisnici()) {
				if (user.equals(temp_user)) {
					
					if(!(temp_user instanceof Admin)) {
						
						if(temp_user instanceof Kupac){
							Kupac k = (Kupac) temp_user;
							
							if(k.isBlokiran()) {
								return "Jedan admin je Vas banovao!";
							}
							
						}else {
							
							Prodavac p = (Prodavac) temp_user;
							
							if(p.isBlokiran()) {
								return "Jedan admin je Vas banovao!";
							}
						}
						
					}
					
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
			
			user.setTip(TipMedalje.BRONZANI);
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
		
		get("/rest/manifestations/getManifestationsProdavac", (req, res) -> {
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
		
		get("/rest/users/getBuyersProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			Prodavac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Kupac> buyers = new ArrayList<>();
			
			for (Karta karta : cardHandler.kartePoManifestacijama(p.getManifestacije())) {
				boolean ima = false;
				
				for (Kupac kupac : buyers) {
					if((karta.getIdKupca() == kupac.getId())) {
						ima = true;
						break;
					}
				}
				
				if(!ima) {
					buyers.add(usersHandler.poIdKupac(karta.getIdKupca()));
				}
			}

			res.type("application/json");

			return gson.toJson(buyers);
		});
		
		
		post("/rest/manifestations/filterManifestationsProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			ManifestacijaSortiranjeDTO criteria = gson.fromJson(req.body(), ManifestacijaSortiranjeDTO.class);
			Prodavac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Manifestacija> manifestations = manifestationHandler.sortiranje(criteria, manifestationHandler.manifestacijePoIdima(p.getManifestacije()));
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});
		
		get("/rest/cards/getCardsProdavac", (req, res) -> {
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
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("/rest/cards/filterCardsProdavac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			KarteSortiranjeDTO criteria = gson.fromJson(req.body(), KarteSortiranjeDTO.class);
			Prodavac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Prodavac) korisnik;
					break;
				}
			}
			
			ArrayList<Karta> cards = cardHandler.sortiranje(criteria, cardHandler.kartePoManifestacijama(p.getManifestacije()), manifestationHandler);
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		get("/rest/cards/getCardsKupac", (req, res) -> {
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
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("/rest/cards/filterCardsKupac", (req, res) -> {
			Korisnik user = (Korisnik) req.session().attribute("currentUser");
			KarteSortiranjeDTO criteria = gson.fromJson(req.body(), KarteSortiranjeDTO.class);
			Kupac p = null;
			
			for (Korisnik korisnik : usersHandler.getKorisnici()) {
				if((user.getId() == korisnik.getId()) && user.getUloga().equals(korisnik.getUloga())) {
					p = (Kupac) korisnik;
					break;
				}
			}
			
			ArrayList<Karta> cards = cardHandler.sortiranje(criteria, cardHandler.kartePoIdima(p.getKarte()), manifestationHandler);
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("/rest/cards/odustani", (req, res) -> {
			KartaDTO c = gson.fromJson(req.body(), KartaDTO.class);
			Karta card = cardHandler.poId(c.getId());
			Kupac kupac = usersHandler.poIdKupac(card.getIdKupca());
			LocalDateTime start = LocalDateTime.parse(manifestationHandler.poId(card.getIdManifestacije()).getDatumVremePocetka());
			LocalDateTime now = LocalDateTime.now();
			
			if(now.isAfter(start.minusDays(7))) {
				return "Prekasno je vec da odustanete!";
			}
			
			kupac.setBrOtkazivanja(kupac.getBrOtkazivanja()+1);
			kupac.setBrBodova(kupac.getBrBodova() - (int) Math.round(card.getCena()/1000*133*4));
			
			if(kupac.getBrBodova() >= GOLD_POINTS) {
				kupac.setTip(TipMedalje.ZLATNI);
			}else if(kupac.getBrBodova() >= SILVER_POINTS) {
				kupac.setTip(TipMedalje.SREBRNI);
			}else {
				kupac.setTip(TipMedalje.BRONZANI);
			}
				
			usersHandler.updateKupac(kupac);
			
			card.setStatus(StatusKarte.ODUSTANAK);
			cardHandler.azurirajKartu(card);
			
			res.type("application/json");

			return "success";
		});
		
		get("/rest/comments/getCommentsProdavac", (req, res) -> {
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
				commentsDTO.add(new KomentarDTO(k, usersHandler.poIdKupac(k.getIdKupac()), manifestationHandler.poId(k.getIdManifestacija())));
			}

			res.type("application/json");

			return gson.toJson(commentsDTO);
		});

		get("/rest/manifestations/getManifestations", (req, res) -> {
			ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});

		get("/rest/manifestations/manifestation/:id", (req, res) -> {
			int temp_id = Integer.parseInt(req.params("id"));
			ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			Manifestacija temp_manifestation = null;
			
			for (Manifestacija m : manifestations) {
				if (m.getId() == temp_id)
					temp_manifestation = m;
			}

			if (temp_manifestation == null) 
				return null;

			res.type("application/json");

			return gson.toJson(new ManifestacijaDTO(temp_manifestation));
		});
		
		post("/rest/manifestations/getManifestationsSorted", (req, res) -> {
			ManifestacijaSortiranjeDTO criteria = gson.fromJson(req.body(), ManifestacijaSortiranjeDTO.class);
			ArrayList<Manifestacija> manifestations = manifestationHandler.sortiranje(criteria, null);
			ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			for (Manifestacija m : manifestations) {
				manifestationsDTO.add(new ManifestacijaDTO(m));
			}

			res.type("application/json");

			return gson.toJson(manifestationsDTO);
		});
		
		get("/rest/users/getUsers", (req, res) -> {
			ArrayList<Korisnik> users = usersHandler.getKorisnici();
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			res.type("application/json");

			return gson.toJson(users);
		});
		
		post("/rest/users/getUsersSorted", (req, res) -> {
			KorisnikSortiranjeDTO criteria = gson.fromJson(req.body(), KorisnikSortiranjeDTO.class);
			ArrayList<Korisnik> users = usersHandler.sortiranje(criteria, null);
			//ArrayList<ManifestacijaDTO> manifestationsDTO = new ArrayList<>();
			
			//for (Manifestacija m : manifestations) {
			//	manifestationsDTO.add(new ManifestacijaDTO(m));
			//}

			res.type("application/json");

			return gson.toJson(users);
		});

		get("/rest/users/getCustomerType/:id", (req, res) -> {
			ArrayList<Kupac> customers = customersHandler.getkupci();
			int id = Integer.parseInt(req.params("id"));
			TipMedalje type = TipMedalje.BRONZANI;
			
			for (Kupac k : customers) {
				if (k.getId() == id) {
					type = k.getTip();
					break;
				}
			}

			res.type("application/json");
			
			return type;
		});
		
		get("/rest/cards/getCards", (req, res) -> {
			ArrayList<Karta> cards = cardHandler.getKarte();
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});
		
		post("/rest/cards/getCardsSorted", (req, res) -> {
			KarteSortiranjeDTO criteria = gson.fromJson(req.body(), KarteSortiranjeDTO.class);
			ArrayList<Karta> cards = cardHandler.sortiranje(criteria, null, manifestationHandler);
			ArrayList<KartaDTO> cardsDTO = new ArrayList<>();
			
			for (Karta k : cards) {
				cardsDTO.add(new KartaDTO(k, usersHandler.poIdKupac(k.getIdKupca()), manifestationHandler.poId(k.getIdManifestacije())));
			}

			res.type("application/json");

			return gson.toJson(cardsDTO);
		});

		post("/rest/cards/reserveCard/:type/:manifestationId/:numberOfCards", (req, res) -> {
			Korisnik u = (Korisnik) req.session().attribute("currentUser");
			TipKarte card_type = TipKarte.valueOf(req.params("type").toString());
			int manifestation_id = Integer.parseInt(req.params("manifestationId"));
			int number_of_cards = Integer.parseInt(req.params("numberOfCards"));

			ArrayList<Kupac> customers = customersHandler.getkupci();

			Kupac temp_customer = null;
			for (Kupac k : customers) {
				if (k.getId() == u.getId()) {
					temp_customer = k;
					break;
				}
			}

			if (temp_customer == null)
				return null;

			ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();

			Manifestacija temp_manifestation = null;
			for (Manifestacija m : manifestations) {
				if (m.getId() == manifestation_id) {
					if (m.getBrSlobodnihMesta() < number_of_cards)
						return null;
					temp_manifestation = m;
					break;
				}
			}

			if (temp_manifestation == null)
				return null;

			double cardPrice = (card_type == TipKarte.REGULAR) ? temp_manifestation.getCenaRegular() : 
				(card_type == TipKarte.FANPIT) ? temp_manifestation.getCenaRegular() * 2 : temp_manifestation.getCenaRegular() * 4;
			cardPrice *= number_of_cards;

			if (!temp_customer.getTip().equals(TipMedalje.BRONZANI))
				cardPrice = getDiscountedPrice(temp_customer, cardPrice);

			for (int i = 0; i < number_of_cards; ++i) {
				Karta new_card = new Karta(cardHandler.nextId(), LocalDateTime.now().toString(), temp_customer.getId(), temp_manifestation.getId(), cardPrice, StatusKarte.REZERVISANA, card_type);

				temp_customer.getKarte().add(new_card.getId());
				cardHandler.dodajKartu(new_card);
			}

			temp_manifestation.setBrSlobodnihMesta(temp_manifestation.getBrSlobodnihMesta() - number_of_cards);
			manifestationHandler.azurirajManifestaciju(temp_manifestation);

			addPointsToCustomer(temp_customer, card_type, temp_manifestation.getCenaRegular(), number_of_cards);
			usersHandler.updateKupac(temp_customer);

			res.type("application/json");

			return "success";
		});
		
		get("/rest/comments/getComments", (req, res) -> {
			ArrayList<Komentar> comments = commentHandler.getKomentari();
			ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			for (Komentar k : comments) {
				commentsDTO.add(new KomentarDTO(k, usersHandler.poIdKupac(k.getIdKupac()), manifestationHandler.poId(k.getIdManifestacija())));
			}

			res.type("application/json");

			return gson.toJson(commentsDTO);
		});

		get("/rest/comments/getManifestationComments/:manifId", (req, res) -> {
			int manifId = Integer.parseInt(req.params("manifId"));

			ArrayList<Komentar> comments = commentHandler.getKomentari();
			ArrayList<KomentarDTO> commentsDTO = new ArrayList<>();
			
			for (Komentar k : comments) {
				if (k.getIdManifestacija() == manifId) {
					commentsDTO.add(new KomentarDTO(k, usersHandler.poIdKupac(k.getIdKupac()), manifestationHandler.poId(k.getIdManifestacija())));
				}
			}

			res.type("application/json");

			return gson.toJson(commentsDTO);
		});
		
		post("/rest/comments/delete", (req, res) -> {
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
		
		post("/rest/comments/odobri", (req, res) -> {
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

		get("/rest/manifestations/rateable/:manifId/:userId", (req, res) -> {
			int manifId = Integer.parseInt(req.params("manifId"));
			int userId = Integer.parseInt(req.params("userId"));

			ArrayList<Karta> tickets = cardHandler.getKarte();
			ArrayList<Komentar> comments = commentHandler.getKomentari();
			
			res.type("application/json");

			for (Komentar k : comments) {
				if (k.getIdKupac() == userId && k.getIdManifestacija() == manifId)
					return false;
			}

			for (Karta k : tickets) {
				if (k.getIdManifestacije() == manifId && k.getIdKupca() == userId && k.getStatus() == StatusKarte.REZERVISANA) {
					return true;
				}
			}

			return false;
		});

		put("/rest/manifestations/rateManifestation/:manifId/:userId/:grade", (req, res) -> {
			int manifId = Integer.parseInt(req.params("manifId"));
			int userId = Integer.parseInt(req.params("userId"));
			int grade = Integer.parseInt(req.params("grade"));
			String comment_body = req.body().toString();

			ArrayList<Kupac> customers = customersHandler.getkupci();
			ArrayList<Manifestacija> manifestations = manifestationHandler.getManifestacije();
			
			Manifestacija temp_manif = null;
			for (Manifestacija m : manifestations) {
				if (m.getId() == manifId) {
					temp_manif = m;
					break;
				}
			}

			Kupac temp_cust = null;
			for (Kupac k : customers) {
				if (k.getId() == userId) {
					temp_cust = k;
					break;
				}
			}

			if (temp_cust == null || temp_manif == null) {
				return null;
			}

			temp_manif.setBrojOcena(temp_manif.getBrojOcena() + 1);
			temp_manif.setSumaOcena(temp_manif.getSumaOcena() + grade);

			manifestationHandler.azurirajManifestaciju(temp_manif);
			
			commentHandler.dodajKomentar(new Komentar(commentHandler.nextId(), temp_cust.getId(), temp_manif.getId(), comment_body, grade));

			res.type("application/json");

			return "success";
		});
		
		post("/rest/manifestations/createManifestation", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			if(manifestationHandler.checkBadTimes(manifestation.getDatumVremePocetka(), manifestation.getDatumVremeKraja(), manifestation.getLokacija(), -1)) {
				return "Vec postoji manifestacija na datoj lokaciji za dato vreme";
			}
			
			//TODO mozda prekopirati sliku u neki nas folder
			
			manifestation.setId(manifestationHandler.nextId());
			manifestation.setBrSlobodnihMesta(manifestation.getBrMesta());
			manifestationHandler.dodajManifestaciju(manifestation);
			
			Korisnik u = (Korisnik) req.session().attribute("currentUser");
			
			Prodavac p = usersHandler.poIdProdavac(u.getId());
			p.addManifestacija(manifestation.getId());
			usersHandler.updateProdavac(p);
			
			for (Lokacija location : locationHandler.getLokacije()) {
				if(location.equals(manifestation.getLokacija())) {
					return "success";
				}
			}
			
			manifestation.getLokacija().setId(locationHandler.nextId());
			locationHandler.dodajLokaciju(manifestation.getLokacija());
			return "success";
		});
		
		post("/rest/manifestations/editManifestation", (req, res) -> {
			Manifestacija manifestation = gson.fromJson(req.body(), Manifestacija.class);
			
			res.type("application/json");

			if(manifestationHandler.checkBadTimes(manifestation.getDatumVremePocetka(), manifestation.getDatumVremeKraja(), manifestation.getLokacija(), manifestation.getId())) {
				return "Vec postoji manifestacija na datoj lokaciji za dato vreme";
			}
			
			//TODO mozda prekopirati sliku u neki nas folder
			
			if(manifestation.getPosterLink().equals("")) {
				manifestation.setPosterLink(manifestationHandler.poId(manifestation.getId()).getPosterLink());
			}
			
			manifestationHandler.azurirajManifestaciju(manifestation);
			
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
