Vue.component("home-page", {
	data: function () {
		    return {
                manifestations: [],
		    }
	},
	template: ` 
<div>
		
<br><br>

<h2 align=center>Manifestacije</h2>

<br>

    <div id="accordion">
        <div class="card text-white bg-dark mb-3 ">
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

        <div class="card text-white bg-dark mb-3">

            <div class="card-body">
                <form action="" method="GET">
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="naziv">Naziv:</label>
                            <input type="text" name = "naziv" id = "naziv" class="form-control">
                        </div>
                        
                        <div class="form-group col-md-2">
                            <label for="adresa">Adresa:</label>
                            <input type="text" name = "adresa" id = "adresa" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="od">Od:</label>
                            <input type="date" name = "od" id = "od" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="do">Do:</label>
                            <input type="date" name = "do" id = "do" class="form-control">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="minimum">Cena minimum:</label>
                            <input type="number" name = "minimum" id = "minimum" class="form-control" min="0" value="0">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="maximum">Cena maximum:</label>
                            <input type="number" name = "maximum" id = "maximum" class="form-control" min="0" value="1000000">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="tip">Tip:</label>
                            <select name="tip" id="tip" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="KONCERT">Koncert</option>
                                <option value="FESTIVAL">Festival</option>
                                <option value="POZORISTE">Pozoriste</option>
                                <option value="DRUGO">Drugo</option>
                            </select>
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="sortiranjePo">Sortiraj po:</label>
                            <select name="sortiranjePo" id="sortiranjePo" class="form-control">
                                <option value="NAZIV">Naziv</option>
                                <option value="DATUM">Datum</option>
                                <option value="CENA">Cena</option>
                                <option value="LOKACIJA">Lokacija</option>
                            </select>
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="nacinSortiranja">Sortiraj:</label>
                            <select name="nacinSortiranja" id="nacinSortiranja" class="form-control">
                                <option value="RASTUCE">Rastuce</option>
                                <option value="OPADAJUCE">Opadajuce</option>
                            </select>
                        </div>
                    </div>
            
                    <div>
                        <input type="submit" name = "filtriraj" id = "filtriraj" value="Filtriraj" class="btn btn-primary">
                    </div>
                </form>
            </div>
          </div>

        </div>
        
    
    <div class="card text-black bg-dark mb-3">

        <div class="card-body">
            
            <div class="row">

                <div v-for="manifestation in manifestations" class="card" style="width: 20rem; display: inline;">
                    <img class="card-img-top" v-bind:src="manifestation.posterLink" alt="Rambo car">
                    <div class="card-body">
                        <h5 class="card-title">{{manifestation.naziv}}</h5>
                        <p class="card-text">{{manifestation.datumVreme}}</p>
                        <p class="card-text">Loznica</p>
                        <p class="card-text">Cena karte vec od: {{manifestation.cenaRegular}}RSD</p>
                        <p class="card-text">Ocena: dodati polje</p>
                        <a href="#" class="btn btn-primary">Detalji</a>
                    </div>
                </div>

            </div>

            <div v-bind:hidden="manifestations.length < 6">
                <nav aria-label="Paginacija rezultata">
                <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Prethodni</a>
                </li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                <a class="page-link" href="#">Sledeci</a>
                </li>
                </ul>
                </nav>
            </div>

        </div>

    </div>
		
</div>		  
`
	, 
	methods : {
        
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
        .get("/rest/manifestations/getManifestations")
        .then(response => {
            this.manifestations = response.data;
        });
    }
});