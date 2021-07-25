Vue.component("profile", {
	data: function () {
		    return {
                updated : {kIme: this.korisnik.kIme, ime: this.korisnik.ime, prezime: this.korisnik.prezime, 
                    datumRodjenja: this.korisnik.datumRodjenja, pol : this.korisnik.pol, lozinka : this.korisnik.lozinka},
                novaLozinka : "",
                novaLozinkaPonovo : ""
		    }
	},
	template: ` 
    <div class="dinamic">
    
    <br><br>

    <h2 align=center>Profil</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            
                <div class="form-group">
                    <label for="kIme">Korisnicko ime:</label>
                    <input type="text" name = "kIme" v-model="updated.kIme" id = "kIme" class="form-control  form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="ime">Ime:</label>
                    <input type="text" name = "ime" v-model="updated.ime" id = "ime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="prezime">Prezime:</label>
                    <input type="text" name = "prezime" v-model="updated.prezime" id = "prezime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" v-model="updated.datumRodjenja" id = "datumRodjenja" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="pol">Pol:</label>
                    <select name="pol" v-model="updated.pol" id="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka">Trenutna lozinka:</label>
                    <input type="text" name = "lozinka" v-model="updated.lozinka" id = "lozinka" class="form-control-plaintext" readonly>
                </div>
        
                <div>
                    <input type="button" name = "azuriraj" id = "azuriraj" value="Azurirajte podatke" class="btn btn-primary">
                </div>
            
        </div>

    </div>

    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            
                <div class="form-group">
                    <label for="lozinkaNova">Nova lozinka:</label>
                    <input type="password" name = "lozinkaNova" v-model="novaLozinka" id = "lozinkaNova" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinkaOpet">Nova lozinka ponovo (lozinke se moraju podudarati):</label>
                    <input type="password" name = "lozinkaOpet" v-model="novaLozinkaOpet" id = "lozinkaOpet" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="button" name = "azurirajLozinku" id = "azurirajLozinku" value="Azurirajte lozinku" class="btn btn-primary">
                </div>

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