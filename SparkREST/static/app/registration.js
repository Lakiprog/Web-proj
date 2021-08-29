Vue.component("registration", {
	data: function () {
		    return {
                user: {kIme: "", lozinka: "", ime: "", prezime: "", datumRodjenja: "", pol : "MUSKO", uloga : "KUPAC"},
                lozinkaOpet: ""
		    }
	},
	template: ` 
    <div class="dinamic">

    <br><br>

    <h2 align=center>Registracija</h2>
    
    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            
                <div class="form-group">
                    <label for="kIme">Korisnicko ime:</label>
                    <input type="text" name = "kIme" v-model="user.kIme" ref = "kIme" class="form-control  form-control-sm">
                </div>
                
                <div class="form-group">
                    <label for="ime">Ime:</label>
                    <input type="text" name = "ime" v-model="user.ime" ref = "ime" class="form-control  form-control-sm">
                </div>
        
                <div class="form-group">
                    <label for="prezime">Prezime:</label>
                    <input type="text" name = "prezime" v-model="user.prezime" ref = "prezime" class="form-control  form-control-sm">
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" v-model="user.datumRodjenja" ref = "datumRodjenja" class="form-control  form-control-sm">
                </div>
        
                <div class="form-group">
                    <label for="pol">Pol:</label>
                    <select name="pol" v-model="user.pol" ref="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka1">Lozinka:</label>
                    <input type="password" name = "lozinka1" v-model="user.lozinka" ref = "lozinka1" class="form-control  form-control-sm">
                </div>
        
                <div class="form-group">
                    <label for="lozinka2">Lozinka ponovo:</label>
                    <input type="password" name = "lozinka2" v-model="lozinkaOpet" ref = "lozinka2" class="form-control  form-control-sm">
                </div>
        
                <div>
                    <input type="button" name = "registruj" ref = "registruj" value="Registrujte se" class="btn btn-primary" v-on:click="registerBuyer()">
                </div>
        
        </div>

    </div>
</div>
`
	, 
	methods : {
        isInformationValid: function() {
            let usernameValid = Boolean(this.user.kIme);
            let fNameValid = Boolean(this.user.ime);
            let lNameValid = Boolean(this.user.prezime);
            let bdayValid = Boolean(this.user.datumRodjenja);
            let passwd1Valid = Boolean(this.user.lozinka);
            let passwd2Valid = Boolean(this.lozinkaOpet);

            return usernameValid && fNameValid && lNameValid && bdayValid && passwd1Valid && passwd2Valid;
        },
        registerBuyer: function() {
            if (!this.isInformationValid()) {
                $.toast({text: "Popunite sva polja", icon: "error"});
                return;
            }

            if (this.user.lozinka == this.lozinkaOpet){
                axios
                .post("/rest/users/registerBuyer", this.user)
                .then(response => {
                    if (response.data == "success") {
                        $.toast("Uspesno ste se registrovali.");
                        this.$router.push({ name: "Home" });
                    } else {
                        $.toast(response.data);
                    }
                });
            }else{
                $.toast("Lozinke nisu iste");
            }
        }
	},
	mounted () {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
            }
        });
    }
});