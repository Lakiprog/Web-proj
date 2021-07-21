package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.util.Date;

import com.google.gson.Gson;

import domain.Korisnik;
import domain.Kupac;
import handlers.KorisnikHandler;
import handlers.KupciHandler;
import handlers.WsHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Session;

public class SparkAppMain {

	private static KorisnikHandler usersHandler = new KorisnikHandler();
	
	private static Gson gson = new Gson();
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		post("/rest/users/logUserIn", (req, res) -> {
			Korisnik user = gson.fromJson(req.body(), Korisnik.class);
			
			System.out.println(user.getkIme());
			System.out.println(user.getLozinka());

			if (usersHandler.getKupci().contains(user)) {
				res.type("application/json");
				req.session().attribute("currentUser", user);
				return "0k";
			}

			return "Username and password mismatch";
		});

		get("/rest/users/logUserOut", (req, res) -> {
			req.session().invalidate();
			return "0k";
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
