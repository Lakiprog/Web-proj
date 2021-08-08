Vue.component("sellerBuyers", {
	data: function () {
		    return {
                users: []
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Pregled Kupaca</h2>

    
    <div class="card text-white bg-dark mb-3 w-75">

    <table class="table table-hover table-dark">
    <tr>
        <th>Korisnicko ime</th>
        <th>Lozinka</th>
        <th>Ime i Prezime</th>
		<th>Pol</th>
		<th>Datum rodjenja</th>
        <th>Sumnjiv</th>
    </tr>

    <tr v-for="u in this.users">
        <td>{{u.kIme}}</td>
        <td>{{u.lozinka}}</td>
        <td>{{u.ime}} {{u.prezime}}</td>
        <td>{{u.pol}}</td>
        <td>{{u.datumRodjenja}}</td>
        <td v-if="u.uloga == 'KUPAC' && u.brOtkazivanja > 5" style="color:red;">Da</td>
        <td v-if="u.uloga == 'KUPAC' && u.brOtkazivanja <= 5" style="color:green;">Ne</td>
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
        .get("/rest/users/getBuyersProdavac")
        .then(response => {
            this.users = response.data;
        });
    }
});