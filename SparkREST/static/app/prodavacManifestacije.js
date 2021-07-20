Vue.component("prodavacManifestacije", {
	data: function () {
		    return {
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

    <tr>
        <td>Primer Koncert Ramba</td>
        <td>Koncert</td>
        <td>2021.10.10</td>
        <td>1000</td>
        <td>300</td>
        <td>Telep</td>
        <td style="color:green;">Aktivno</td>
        <td><input type="button" class="btn btn-primary" value="Azuriraj"/></td>
    </tr>

    <tr>
        <td>Primer Dombos fest</td>
        <td>Festival</td>
        <td>2021.07.08</td>
        <td>10000</td>
        <td>10</td>
        <td>Mali idjos</td>
        <td style="color:red;">Neaktivno</td>
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
    }
});