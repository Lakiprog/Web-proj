Vue.component("registerSeller", {
	data: function () {
        return {
            user: {kIme: "", lozinka: "", ime: "", prezime: "", datumRodjenja: "", pol : "MUSKO", uloga : "PRODAVAC"},
            lozinkaOpet : ""
        }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Registracija Prodavca</h2>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            
                <div class="form-group">
                    <label for="kIme">Unesite korisnicko ime:</label>
                    <input type="text" name = "kIme" v-model="user.kIme" id = "kIme" class="form-control  form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="ime">Unesite ime:</label>
                    <input type="text" name = "ime" v-model="user.ime" id = "ime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="prezime">Unesite prezime:</label>
                    <input type="text" name = "prezime" v-model="user.prezime" id = "prezime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Unesite datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" v-model="user.datumRodjenja" id = "datumRodjenja" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="pol">Birajte pol:</label>
                    <select name="pol" v-model="user.pol" id="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka1">Unesite lozinku:</label>
                    <input type="password" name = "lozinka1" v-model="user.lozinka" id = "lozinka1" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinka2">Unesite lozinku opet:</label>
                    <input type="password" name = "lozinka2" v-model="lozinkaOpet" id = "lozinka2" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="button" name = "registruj" id = "registruj" value="Registrujte se" class="btn btn-primary" v-on:click="registerSeller()">
                </div>
            
        </div>

    </div>
</div>
`
	, 
	methods : {
        registerSeller: function(){
            if(this.user.lozinka == this.lozinkaOpet){
                axios
                .post("/rest/users/registerSeller", this.user)
                .then(response => {
                    if (response.data == "success") {
                        $.toast({text : "Uspesno ste registrovali prodavca.", position : "mid-center", icon : "success"});
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
    }
});