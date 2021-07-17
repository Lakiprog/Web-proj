Vue.component("registration", {
	data: function () {
		    return {
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Registracija</h2>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="kIme">Unesite korisnicko ime:</label>
                    <input type="text" name = "kIme" id = "kIme" class="form-control  form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="ime">Unesite ime:</label>
                    <input type="text" name = "ime" id = "ime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="prezime">Unesite prezime:</label>
                    <input type="text" name = "prezime" id = "prezime" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="datumRodjenja">Unesite datum rodjenja:</label>
                    <input type="date" name = "datumRodjenja" id = "datumRodjenja" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="pol">Birajte pol:</label>
                    <select name="pol" id="pol" class="form-control  form-control-sm">
                        <option value="MUSKO">Musko</option>
                        <option value="ZENSKO">Zensko</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lozinka1">Unesite lozinku:</label>
                    <input type="password" name = "lozinka1" id = "lozinka1" class="form-control  form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="lozinka2">Unesite lozinku opet:</label>
                    <input type="password" name = "lozinka2" id = "lozinka2" class="form-control  form-control-sm" required>
                </div>
        
                <div>
                    <input type="submit" name = "registruj" id = "registruj" value="Registrujte se" class="btn btn-primary">
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