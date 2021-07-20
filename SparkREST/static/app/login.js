Vue.component("login", {
	data: function () {
		    return {
		    }
	},
	template: ` 
    <div class="dinamic">

    <br><br>

    <h2 align=center>Login</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="GET">
                <div class="form-group">
                    <label for="kIme">Korisnicko ime:</label>
                    <input type="text" name = "kIme" id = "kIme" class="form-control" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinka">Lozinka:</label>
                    <input type="password" name = "lozinka" id = "lozinka" class="form-control" required>
                </div>

                <div>
                    <input type="submit" name = "uloguj" id = "uloguj" value="Ulogujte se" class="btn btn-primary">
                </div>
            </form>
        </div>

    </div>
    </div>
    `
	, 
	methods : {
        
	},
	mounted () {
    }
});