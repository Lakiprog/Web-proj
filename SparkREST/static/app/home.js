Vue.component("home-page", {
	data: function () {
		    return {
                manifestations: [],
                currentPageManifestations: [],
                criteria: {naziv : "", adresa: "", datumOd : "", datumDo: "", cenaMin: 0, cenaMax: 10000, tip: "SVE", sortirajPo: "NAZIV", sortiraj: "RASTUCE", rasprodate: "SVE"},
                pagenum: 1,
                numberOfPages: 0,
                pages: [],
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
                
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="naziv">Naziv:</label>
                            <input type="text" name = "naziv" v-model="criteria.naziv" id = "naziv" class="form-control">
                        </div>
                        
                        <div class="form-group col-md-2">
                            <label for="adresa">Adresa:</label>
                            <input type="text" name = "adresa" v-model="criteria.adresa" id = "adresa" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="od">Od:</label>
                            <input type="date" name = "od" v-model="criteria.datumOd" id = "od" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="do">Do:</label>
                            <input type="date" name = "do" v-model="criteria.datumDo" id = "do" class="form-control">
                        </div>

                        <div class="form-group col-md-2">
                            <label for="rasprodate">Karte su/nisu rasprodate:</label>
                            <select name="rasprodate" v-model="criteria.rasprodate" id="rasprodate" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="NERASPRODATE">Nerasprodate</option>
                                <option value="RASPRODATE">Rasprodate</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="minimum">Cena minimum:</label>
                            <input type="number" name = "minimum" v-model="criteria.cenaMin" id = "minimum" class="form-control" min="0" value="0">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="maximum">Cena maximum:</label>
                            <input type="number" name = "maximum" v-model="criteria.cenaMax" id = "maximum" class="form-control" min="0" value="1000000">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="tip">Tip:</label>
                            <select name="tip" v-model="criteria.tip" id="tip" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="KONCERT">Koncert</option>
                                <option value="FESTIVAL">Festival</option>
                                <option value="POZORISTE">Pozoriste</option>
                                <option value="DRUGO">Drugo</option>
                            </select>
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="sortiranjePo">Sortiraj po:</label>
                            <select name="sortiranjePo" v-model="criteria.sortirajPo" id="sortirajPo" class="form-control">
                                <option value="NAZIV">Naziv</option>
                                <option value="DATUM">Datum</option>
                                <option value="CENA">Cena</option>
                                <option value="LOKACIJA">Lokacija</option>
                            </select>
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="nacinSortiranja">Sortiraj:</label>
                            <select name="nacinSortiranja" v-model="criteria.sortiraj" id="nacinSortiranja" class="form-control">
                                <option value="RASTUCE">Rastuce</option>
                                <option value="OPADAJUCE">Opadajuce</option>
                            </select>
                        </div>
                    </div>
            
                    <div>
                        <input type="button" name = "filtriraj" id = "filtriraj" value="Filtriraj" class="btn btn-primary" v-on:click="filter()">
                    </div>
                
            </div>
          </div>

        </div>
        
    
    <div class="card text-black bg-dark mb-3">

        <div class="card-body">
            
            <div class="row">

                <div v-for="manifestation of currentPageManifestations" class="card" style="width: 20rem; display: inline;">
                    <img class="card-img-top" v-bind:src="manifestation.posterLink" alt="Rambo car">
                    <div class="card-body">
                        <h5 class="card-title">{{manifestation.naziv}}</h5>
                        <p class="card-text">{{manifestation.datumVreme}}</p>
                        <p class="card-text">{{manifestation.adresa}}</p>
                        <p class="card-text">Cena karte vec od: {{manifestation.cenaRegular}}RSD</p>
                        <p class="card-text">{{(manifestation.sumaOcena / manifestation.brojOcena).toFixed(2)}}</p>
                        <input type="button" class="btn btn-primary" value="Detalji" v-on:click="viewManifestation(manifestation)"/>
                    </div>
                </div>
            </div>

            <br><br>

            <div v-bind:hidden="pages == 1">
                <nav aria-label="Paginacija rezultata">
                <ul v-for="num in pages" class="pagination justify-content-center" style="display:inline-block;margin:3px;">
                <li class="page-item"><input type="button" class="page-link" v-bind:value="num" v-on:click="loadPage(num)"/></li>
                </ul>
                </nav>
            </div>


        </div>

    </div>
		
</div>		  
`
	, 
	methods : {
        loadPage: function(pageNumber) {
            this.pagenum = pageNumber;

            var startIndex = (this.pagenum - 1) * 5;
            var endIndex = ((startIndex + 5) > (this.manifestations.length - 1)) ? this.manifestations.length : startIndex + 5;

            this.currentPageManifestations = this.manifestations.slice(startIndex, endIndex);
        },
        filter: function(){
            axios
            .post("/rest/manifestations/getManifestationsSorted", this.criteria)
            .then(response => {
                this.manifestations = response.data.sort((a, b)=>{
                    first = Date.parse(a.datumVremePocetka);
                    if(first < Date.now()){
                        first += 100*(Date.now() - first) + Date.now();
                    }
    
                    second = Date.parse(b.datumVremePocetka);
                    if(second < Date.now()){
                        second += 100*(Date.now() - second) + Date.now();
                    }
    
                    return  first - second; 
                });

                this.numberOfPages = parseInt(this.manifestations.length / 5);
                this.numberOfPages += (this.manifestations.length % 5 == 0) ? 0 : 1;

                this.pages = [...Array(this.numberOfPages + 1).keys()];
                this.pages.shift();

                if (this.pages < 6) {
                    this.currentPageManifestations = this.manifestations;
                } else {
                    this.currentPageManifestations = this.manifestations.slice(0, 5);
                }
            });
        },
        viewManifestation(m) {
            this.$router.push({ name: "ManifestationDetails", params: {id: m.id}});
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
        axios
        .get("/rest/manifestations/getManifestations")
        .then(response => {
            this.manifestations = response.data.sort((a, b)=>{
                first = Date.parse(a.datumVremePocetka);
                if(first < Date.now()){
                    first += 100*(Date.now() - first) + Date.now();
                }

                second = Date.parse(b.datumVremePocetka);
                if(second < Date.now()){
                    second += 100*(Date.now() - second) + Date.now();
                }

                return  first - second; 
            });
            //this.manifestations.shift();

            this.numberOfPages = parseInt(this.manifestations.length / 5);
            this.numberOfPages += (this.manifestations.length % 5 == 0) ? 0 : 1;

            this.pages = [...Array(this.numberOfPages + 1).keys()];
            this.pages.shift();

            if (this.pages < 6) {
                this.currentPageManifestations = this.manifestations;
            } else {
                this.currentPageManifestations = this.manifestations.slice(0, 5);
            }
        });
    }
});