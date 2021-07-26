Vue.component("sellerManifestations", {
	data: function () {
		    return {
                manifestations: []
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Pregled Manifestacija</h2>
    
    <div class="card text-white bg-dark mb-3 w-75">

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
        <td>{{manifestation.datumVremePocetka}}-{{manifestation.datumVremeKraja}}</td>
        <td>{{manifestation.brMesta}}</td>
        <td>{{manifestation.cenaRegular}}</td>
        <td>{{manifestation.adresa}}</td>
        <td v-if="manifestation.status == 'AKTIVNO'" style="color:green;">Aktivno</td>
        <td v-else style="color:red;">Neaktivno</td>
        <td><input type="button" class="btn btn-primary" value="Azuriraj"/></td>
    </tr>
</table>

    </div>
</div>
`
	, 
	methods : {
        
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