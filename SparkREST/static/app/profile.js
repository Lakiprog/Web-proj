Vue.component("profile", {
	data: function () {
		    return {
                user: {},
                novaLozinka : "",
                novaLozinkaOpet : ""
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
                    <input type="text" name = "kIme" v-model="user.kIme" id = "kIme" class="form-control  form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="ime">Ime:</label>
                    <input type="text" name = "ime" v-model="user.ime" id = "ime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="prezime">Prezime:</label>
                    <input type="text" name = "prezime" v-model="user.prezime" id = "prezime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" v-model="user.datumRodjenja" id = "datumRodjenja" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="pol">Pol:</label>
                    <select name="pol" v-model="user.pol" id="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka">Trenutna lozinka:</label>
                    <input  type="text" name = "lozinka" v-model="user.lozinka" id = "lozinkaProfil" class="form-control-plaintext text-light" readonly>
                </div>
        
                <div>
                    <input type="button" name = "azuriraj" id = "azuriraj" value="Azurirajte podatke" class="btn btn-primary" v-on:click="update()">
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
                    <label for="lozinkaOpet">Nova lozinka ponovo:</label>
                    <input type="password" name = "lozinkaOpet" v-model="novaLozinkaOpet" id = "lozinkaOpet" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="button" name = "azurirajLozinku" id = "azurirajLozinku" value="Azurirajte lozinku" class="btn btn-primary" v-on:click="updatePassword()">
                </div>

        </div>

    </div>

    </div>

    `
	, 
	methods : {
        update: function(){
            let uloga = "";
            if(this.user.uloga == "KUPAC"){
                uloga = "Kupac"
            }else if(this.user.uloga == "PRODAVAC"){
                uloga = "Prodavac";
            }else{
                uloga = "Admin";
            }

            axios
            .post("/rest/users/update"+uloga, this.user)
            .then(response => {
                if(response.data == "success"){
                    toast("Uspesno ste izmenili profil")
                }else{
                    toast(response.data);
                }
            });
        },
        updatePassword: function(){
            if(this.novaLozinka != this.novaLozinkaOpet){
                toast("Lozinke moraju biti iste")
            }else{
                axios
                .get("/rest/users/getCurrentUserInfo")
                .then(response => {
                    if (response.data) {
                        response.data.lozinka = this.novaLozinka;

                        let uloga = "";
                        if(this.user.uloga == "KUPAC"){
                            uloga = "Kupac"
                        }else if(this.user.uloga == "PRODAVAC"){
                            uloga = "Prodavac";
                        }else{
                            uloga = "Admin";
                        }

                        axios
                        .post("/rest/users/update"+uloga, response.data)
                        .then(response2 => {
                            if(response2.data == "success"){
                                toast("Uspesno ste izmenili lozinku")
                                this.user.lozinka = response.data.lozinka;
                                this.novaLozinka = "";
                                this.novaLozinkaOpet = "";
                            }else{
                                toast(response2.data);
                            }
                        });
                    }
                });
            }
        }
	},
	mounted() {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
            }
        });
        axios
        .get("/rest/users/getCurrentUserInfo")
        .then(response => {
            if (response.data) {
                this.user = response.data;
            }
        });
    }
});