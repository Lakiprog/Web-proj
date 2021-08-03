Vue.component("buyerCards", {
	data: function () {
		    return {
                cards: [],
                criteria: {naziv : "", datumOd : "", datumDo: "", cenaMin: 0, cenaMax: 10000, tip: "SVE", status: "SVE", sortirajPo: "NAZIV", sortiraj: "RASTUCE"}
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Pregled Karata</h2>

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
                
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="naziv">Naziv:</label>
                            <input type="text" name = "naziv" v-model="criteria.naziv" id = "naziv" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="od">Od:</label>
                            <input type="date" name = "od" v-model="criteria.datumOd" id = "od" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="do">Do:</label>
                            <input type="date" name = "do" v-model="criteria.datumDo" id = "do" class="form-control">
                        </div>

                        <div class="form-group col-md-1">
                            <label for="tip">Tip:</label>
                            <select name="tip" v-model="criteria.tip" id="tip" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="REGULAR">Regular</option>
                                <option value="FANPIT">Fan Pit</option>
                                <option value="VIP">VIP</option>
                            </select>
                        </div>

                        <div class="form-group col-md-1">
                            <label for="status">Status:</label>
                            <select name="status" v-model="criteria.status" id="status" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="REZERVISANA">Rezervisana</option>
                                <option value="ODUSTANAK">Odustanak</option>
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
                            <label for="sortiranjePo">Sortiraj po:</label>
                            <select name="sortiranjePo" v-model="criteria.sortirajPo" id="sortiranjePo" class="form-control">
                                <option value="NAZIV">Naziv</option>
                                <option value="DATUM">Datum</option>
                                <option value="CENA">Cena</option>
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
    
    <div class="card text-white bg-dark mb-3 w-75">

    <table class="table table-hover table-dark">
    <tr>
        <th>Manifestacija</th>
        <th>Tip Karte</th>
		<th>Broj mesta</th>
		<th>Datum</th>
        <th>Status</th>
        <th></th>
    </tr>

    <tr v-for="c in this.cards">
        <td>{{c.manifestacija}}</td>
        <td>{{c.tip}}</td>
        <td>{{c.brMesta}}</td>
        <td>{{c.datumVreme}}</td>
        <td v-if="c.status == 'REZERVISANA'" style="color:green;">Rezervisana</td>
        <td v-else style="color:red;">Odustanak</td>
        <td><input v-bind:hidden="c.status != 'REZERVISANA'" type="button" class="btn btn-danger" value="Odustani" v-on:click="odustani(c)" /></td>
    </tr>

</table>

    </div>
</div>
`
	, 
	methods : {
        odustani: function(c){
            axios
            .post("/rest/cards/odustani", c)
            .then(response => {
                
            });
            c.status = "ODUSTANAK";
        },
        filter: function(){
            axios
            .post("/rest/cards/filterCardsKupac", this.criteria)
            .then(response => {
                this.cards = response.data;
            });
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
        .get("/rest/cards/getCardsKupac")
        .then(response => {
            this.cards = response.data;
        });
    }
});