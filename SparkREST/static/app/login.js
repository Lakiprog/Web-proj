Vue.component("login", {
	data: function () {
		    return {
                user: {kIme: "", lozinka: ""},
		    }
	},
	template: ` 
    <div class="dinamic">

    <br><br>

    <h2 align=center>Login</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
                <div class="form-group">
                    <label for="kIme">Korisnicko ime:</label>
                    <input type="text" v-model="user.kIme" name="kIme" id="kIme" class="form-control" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinka">Lozinka:</label>
                    <input type="password" v-model="user.lozinka" name="lozinka" id="lozinka" class="form-control" required>
                </div>

                <div>
                    <input type="button" name="logUserIn" id="uloguj" value="Ulogujte se" class="btn btn-primary" v-on:click="logUserIn()"/>
                </div>
        </div>

    </div>
    </div>
    `
	, 
	methods : {
        logUserIn: function() {
            let self = this;
            axios
            .post("/rest/users/logUserIn", this.user)
            .then(response => {
                if (response.data == "success") {
                    self.$root.$emit('sendingUser', response.data);
                    axios
                    .get("/rest/users/getCurrentUser")
                    .then(response => {
                        if (response.data) {
                            this.korisnik = response.data;
                        }
                    });
                    $.toast("Uspesno ste se prijavili.");
                    this.$router.push({ name: "Home" });
                    this.$router.go();
                    update();
                } else {
                   $. toast({text : response.data, position : "mid-center", icon: "error"});
                }
            });
        },
	},
	mounted() {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
            }
        });

        this.$root.$on('sendingUser', (data) => {
            this.user = data;
        });
    }
});