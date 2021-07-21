Vue.component("profile", {
	data: function () {
		    return {
		    }
	},
	template: ` 
    <div class="dinamic">
    
    <br><br>

    <h2 align=center>Profil</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="kIme">Korisnicko ime:</label>
                    <input type="text" name = "kIme" id = "kIme" class="form-control  form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="ime">Ime:</label>
                    <input type="text" name = "ime" id = "ime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="prezime">Prezime:</label>
                    <input type="text" name = "prezime" id = "prezime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" id = "datumRodjenja" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="pol">Pol:</label>
                    <select name="pol" id="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka">Trenutna lozinka:</label>
                    <input type="text" name = "lozinka" id = "lozinka" class="form-control-plaintext" readonly>
                </div>
        
                <div>
                    <input type="submit" name = "azuriraj" id = "azuriraj" value="Azurirajte podatke" class="btn btn-primary">
                </div>
            </form>
        </div>

    </div>

    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="lozinkaNova">Nova lozinka:</label>
                    <input type="password" name = "lozinkaNova" id = "lozinkaNova" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinkaOpet">Nova lozinka ponovo (lozinke se moraju podudarati):</label>
                    <input type="password" name = "lozinkaOpet" id = "lozinkaOpet" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="submit" name = "azurirajLozinku" id = "azurirajLozinku" value="Azurirajte lozinku" class="btn btn-primary">
                </div>
            </form>
        </div>

    </div>

    </div>

    `
	, 
	methods : {
        
	},
	mounted() {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
            }
        });
    }
});