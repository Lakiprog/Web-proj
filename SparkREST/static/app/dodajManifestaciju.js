<<<<<<< HEAD:SparkREST/static/app/createManifestation.js
Vue.component("create-manifestation", {
=======
Vue.component("dodajManifestaciju", {
>>>>>>> b3896e3fb17f7076d4277f69fd71d58f5512fba6:SparkREST/static/app/dodajManifestaciju.js
	data: function () {
		    return {
		    }
	},
	template: ` 
<<<<<<< HEAD:SparkREST/static/app/createManifestation.js

    <div>

    <br><br>

    <h2 align=center>Kreiranje manifestacije</h2>

    <br>
=======
    <div class="dinamic">
    <h2 align=center>Dodavanje Manifestacije</h2>
>>>>>>> b3896e3fb17f7076d4277f69fd71d58f5512fba6:SparkREST/static/app/dodajManifestaciju.js
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="naziv">Naziv:</label>
                    <input type="text" name = "naziv" id = "naziv" class="form-control form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="brMesta">Broj mesta:</label>
                    <input type="number" name = "brMesta" id = "brMesta" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="cena">Cena regularne karte:</label>
                    <input type="number" name = "cena" id = "cena" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="datum">Datum i vreme odrzavanja:</label>
                    <input type="datetime-local" name = "datum" id = "datum" class="form-control form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="tip">Tip manifestacije:</label>
                    <select name="tip" id="tip" class="form-control form-control-sm">
                        <option value="KONCERT">Koncert</option>
                        <option value="FESTIVAL">Festival</option>
                        <option value="POZORISTE">Pozoriste</option>
                        <option value="DRUGO">Drugo</option>
                    </select>
                </div>
        
                <div class="form-group">
                    <label for="lokacija">Lokacija (ovde cemo staviti onu mapu bacane, ja msm barem) :</label>
                    <input type="text" name = "lokacija" id = "lokacija" class="form-control form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="poster">Slika postera:</label>
                    <input type="file" name = "poster" id = "poster" class="form-control form-control-sm" accept="image/png, image/jpeg" required>
                </div>
        
                <div>
                    <input type="submit" name = "napravi" id = "napravi" value="Napravijte manifestaciju" class="btn btn-primary">
                </div>
            </form>
        </div>

    </div>
<<<<<<< HEAD:SparkREST/static/app/createManifestation.js

    </div>
    
    `
=======
</div>
`
>>>>>>> b3896e3fb17f7076d4277f69fd71d58f5512fba6:SparkREST/static/app/dodajManifestaciju.js
	, 
	methods : {
        
	},
	mounted () {
    }
});