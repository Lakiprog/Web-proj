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
            axios
            .post("/rest/users/logUserIn", this.user)
            .then(response => {
                alert(response.data);
            });
        },
	},
	mounted () {
    }
});