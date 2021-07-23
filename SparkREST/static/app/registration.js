Vue.component("registration", {
	data: function () {
		    return {
                user: {kIme: "", lozinka: "", ime: "", prezime: "", datumRodjenja: "", pol : "MUSKO", uloga : "KUPAC"},
                lozinkaOpet : ""
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
                    <label for="lozinka1">Lozinka:</label>
                    <input type="password" name = "lozinka1" v-model="user.lozinka" id = "lozinka1" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinka2">Lozinka ponovo:</label>
                    <input type="password" name = "lozinka2" v-model="lozinkaOpet" id = "lozinka2" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="button" name = "registruj" id = "registruj" value="Registrujte se" class="btn btn-primary" v-on:click="registerBuyer()">
                </div>
        
        </div>

    </div>
</div>
`
	, 
	methods : {
        registerBuyer: function(){
            if(this.user.lozinka == this.lozinkaOpet){
                axios
                .post("/rest/users/registerBuyer", this.user)
                .then(response => {
                    if (response.data == "success") {
                        $.toast({text : "Uspesno ste se registrovali.", position : "mid-center", icon : "success"});
                        this.$router.push({ name: "Home" });
                    } else {
                    $.toast({text : response.data, position : "mid-center", icon: "error"});
                    }
                });
            }else{
                $.toast({text : "Lozinke nisu iste", position : "mid-center", icon: "error"});
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