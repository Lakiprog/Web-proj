Vue.component("adminUsers", {
	data: function () {
		    return {
                users: [],
                criteria: {ime : "", prezime: "", kIme: "", uloga: "SVE", tip: "SVE", sortirajPo: "IME", sortiraj: "RASTUCE"}
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
            
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="ime">Ime:</label>
                        <input type="text" name = "ime" v-model="criteria.ime" id = "ime" class="form-control">
                    </div>
                    
                    <div class="form-group col-md-2">
                        <label for="prezime">Prezime:</label>
                        <input type="text" name = "prezime" v-model="criteria.prezime" id = "prezime" class="form-control">
                    </div>
            
                    <div class="form-group col-md-2">
                        <label for="kIme">Korisnicko ime:</label>
                        <input type="text" name = "kIme" v-model="criteria.kIme" id = "kIme" class="form-control">
                    </div>
            
                    <div class="form-group col-md-2">
                        <label for="uloga">Uloga:</label>
                        <select name="uloga" v-model="criteria.uloga" id="uloga" class="form-control">
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
                        <select name="tip" v-model="criteria.tip" id="tip" class="form-control">
                            <option value="SVE">Sve</option>
                            <option value="NIKAKVA">Nikakav</option>
                            <option value="BRONZANI">Bronzani</option>
                            <option value="SREBRNI">Srebrni</option>
                            <option value="ZLATNI">Zlatni</option>
                        </select>
                    </div>
    
                    <div class="form-group col-md-2">
                        <label for="sortiranjePo">Sortiraj po:</label>
                        <select name="sortiranjePo" v-model="criteria.sortirajPo" id="sortiranjePo" class="form-control">
                            <option value="IME">Ime</option>
                            <option value="PREZIME">Prezime</option>
                            <option value="KIME">Korisnicko Ime</option>
                            <option value="BODOVI">Bodovi</option>
                        </select>
                    </div>
    
                    <div class="form-group col-md-2">
                        <label for="nacinSortiranja">Sortiraj:</label>
                        <select name="nacinSortiranja" v-model="criteria.sortiraj" id="nacinSortiranja" class="form-control">
                            <option value="RASTUCE">Rastuce</option>
                            <option value="OPADAJUCE">Opadajuce</option>
                        </select>
                    </div>

                    <div class="form-group col-md-2">
                        <input type="button" name = "filtriraj" id = "filtriraj" value="Filtriraj" class="btn btn-primary" v-on:click="filter()">
                    </div>
                </div>
        
            
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

    <tr v-for="u in this.users">
        <td>{{u.kIme}}</td>
        <td>{{u.lozinka}}</td>
        <td>{{u.ime}} {{u.prezime}}</td>
        <td>{{u.pol}}</td>
        <td>{{u.datumRodjenja}}</td>
        <td>{{u.uloga}}</td>
        <td v-if="u.uloga == 'KUPAC' && u.brOtkazivanja > 5" style="color:red;">Da</td>
        <td v-if="u.uloga == 'KUPAC' && u.brOtkazivanja <= 5" style="color:green;">Ne</td>
        <td v-else >-</td>
        <td v-bind:hidden="u.uloga == 'ADMIN'"><input type="button" value="Obrisi" class="btn btn-danger"/></td>
		<td v-if="u.blokiran" v-bind:hidden="u.uloga == 'ADMIN'"><input type="button" class="btn btn-primary" value="Odbanuj"/></td>
        <td v-else v-bind:hidden="u.uloga == 'ADMIN'" ><input type="button" class="btn btn-danger" value="Banuj"/></td>
    </tr>
</table>

    </div>
</div>
`
	, 
	methods : {
        filter: function(){
            axios
            .post("/rest/users/getUsersSorted", this.criteria)
            .then(response => {
                this.users = response.data;
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
        .get("/rest/users/getUsers")
        .then(response => {
            this.users = response.data;
        });
    }
});