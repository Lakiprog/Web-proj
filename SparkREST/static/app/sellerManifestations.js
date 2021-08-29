Vue.component("sellerManifestations", {
	data: function () {
		    return {
                manifestations: [],
                criteria: {naziv : "", adresa: "", datumOd : "", datumDo: "", cenaMin: 0, cenaMax: 10000, tip: "SVE", sortirajPo: "NAZIV", sortiraj: "RASTUCE", rasprodate: "SVE"}
		    }
	},
	template: ` 
    <div class="dinamic">

    <br>
    <h2 align=center>Pregled Manifestacija</h2>
    <br>


    <div id="accordion">
        <div class="card text-white bg-dark mb-3">
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
    
    <div class="card text-white bg-dark mb-3">

    <table class="table table-hover table-dark">
    <tr>
        <th>Naziv</th>
        <th>Tip manifestacije</th>
        <th>Datum odrzavanja</th>
        <th>Broj mesta</th>
        <th>Cena karte</th>
        <th>Adresa odrzavanja</th>
        <th>Status</th>
        <th></th>
    </tr>

    <tr v-for="manifestation in manifestations" >
        <td>{{manifestation.naziv}}</td>
        <td>{{manifestation.tip}}</td>
        <td>{{manifestation.datumVremePocetka.replace('T', ' ')}} - {{manifestation.datumVremeKraja.replace('T', ' ')}}</td>
        <td>{{manifestation.brSlobodnihMesta}} / {{manifestation.brMesta}}</td>
        <td>{{manifestation.cenaRegular}}</td>
        <td>{{manifestation.adresa}}</td>
        <td v-if="manifestation.status == 'AKTIVNO'" style="color:green;">Aktivno</td>
        <td v-else style="color:red;">Neaktivno</td>
        <td><input type="button" class="btn btn-primary" value="Azuriraj" v-on:click="edit(manifestation)"/></td>
    </tr>
</table>

    </div>
</div>
`
	, 
	methods : {
        filter: function(){
            axios
            .post("/rest/manifestations/filterManifestationsProdavac", this.criteria)
            .then(response => {
                this.manifestations = response.data;
            });
        },
        edit: function(m){
            this.$router.push({ name: "EditManifestation", params: {id: m.id}});
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
        axios
        .get("/rest/manifestations/getManifestationsProdavac")
        .then(response => {
            this.manifestations = response.data;
        });
    }
});