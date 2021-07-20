Vue.component("adminUsers", {
	data: function () {
		    return {
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Pregled Korisnika</h2>

    <div id="accordion">
    <div class="card text-white bg-dark mb-3 w-75">
        <div class="card-header" id="headingOne" >
            <h5 class="mb-0 ">
              <button class="btn btn-link " data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne" id="filtriranje">
                Filtriranje
              </button>
            </h5>
          </div>
    </div>
    </div>

    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">

    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="GET">
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="ime">Ime:</label>
                        <input type="text" name = "ime" id = "ime" class="form-control">
                    </div>
                    
                    <div class="form-group col-md-2">
                        <label for="prezime">Prezime:</label>
                        <input type="text" name = "prezime" id = "prezime" class="form-control">
                    </div>
            
                    <div class="form-group col-md-2">
                        <label for="kIme">Korisnicko ime:</label>
                        <input type="text" name = "kIme" id = "kIme" class="form-control">
                    </div>
            
                    <div class="form-group col-md-2">
                        <label for="uloga">Uloga:</label>
                        <select name="uloga" id="uloga" class="form-control">
                            <option value="SVE">Sve</option>
                            <option value="KUPAC">Kupac</option>
                            <option value="PRODAVAC">Prodavac</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
            
                    <div class="form-group col-md-2">
                        <label for="tip">Tip:</label>
                        <select name="tip" id="tip" class="form-control">
                            <option value="SVE">Sve</option>
                            <option value="BRONZANI">Bronzani</option>
                            <option value="SREBRNI">Srebrni</option>
                            <option value="ZLATNI">Zlatni</option>
                        </select>
                    </div>
    
                    <div class="form-group col-md-2">
                        <label for="sortiranjePo">Sortiraj po:</label>
                        <select name="sortiranjePo" id="sortiranjePo" class="form-control">
                            <option value="IME">Ime</option>
                            <option value="PREZIME">Prezime</option>
                            <option value="KIME">Korisnicko Ime</option>
                            <option value="BODOVI">Bodovi</option>
                        </select>
                    </div>
    
                    <div class="form-group col-md-2">
                        <label for="nacinSortiranja">Sortiraj:</label>
                        <select name="nacinSortiranja" id="nacinSortiranja" class="form-control">
                            <option value="RASTUCE">Rastuce</option>
                            <option value="OPADAJUCE">Opadajuce</option>
                        </select>
                    </div>

                    <div class="form-group col-md-2">
                        <input type="submit" name = "filtriraj" id = "filtriraj" value="Filtriraj" class="btn btn-primary">
                    </div>
                </div>
        
            </form>
        </div>
      </div>

    </div>

    
    <div class="card text-white bg-dark mb-3 w-75">

    <table class="table table-hover table-dark">
    <tr>
        <th>Korisnicko ime</th>
        <th>Lozinka</th>
        <th>Ime i Prezime</th>
		<th>Pol</th>
		<th>Datum rodjenja</th>
		<th>Uloga</th>
        <th>Sumnjiv</th>
        <th colspan=2></th>
    </tr>

    <tr>
        <td>markuza</td>
        <td>markuzaRage</td>
        <td>Petar Markovic</td>
        <td>Zensko</td>
        <td>1999.12.08</td>
        <td>Kupac</td>
        <td style="color:red;">Da</td>
        <td><input type="button" value="Obrisi" class="btn btn-danger"/></td>
		<td><input type="button" class="btn btn-primary" value="Odbanuj"/></td>
    </tr>

    <tr>
        <td>kupac</td>
        <td>kupac</td>
        <td>Markovic Markovic</td>
        <td>Musko</td>
        <td>1999.12.08</td>
        <td>Kupac</td>
        <td style="color:green;">Ne</td>
        <td><input type="button" value="Obrisi" class="btn btn-danger"/></td>
		<td><input type="button" class="btn btn-danger" value="Banuj"/></td>
    </tr>

    <tr>
        <td>prodavac</td>
        <td>markuzaProdavac</td>
        <td>Prodavac Markovic</td>
        <td>Zensko</td>
        <td>1999.12.08</td>
        <td>Prodavac</td>
        <td>-</td>
        <td><input type="button" value="Obrisi" class="btn btn-danger"/></td>
		<td><input type="button" class="btn btn-danger" value="Banuj"/></td>
    </tr>
</table>

    </div>
</div>
`
	, 
	methods : {
        
	},
	mounted () {
    }
});