Vue.component("create-manifestation", {
	data: function () {
		    return {
		    }
	},
	template: ` 

    <div>

    <br><br>

    <h2 align=center>Koncert Ramba Primer</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3">

        <div class="card-body">
            
            <div id="informacije">
                <p>Prosecna ocena: 5</p>

                <p>Broj Mesta: 1000</p>
                
                <p>Broj preostalih karata: 1000</p>

                <p>Cene:</p>
                <p class="utabovano">Regular - 300 RSD</p>
                <p class="utabovano">Fan Pit - 600 RSD</p>
                <p class="utabovano">VIP - 1200 RSD</p>

                <p>Datum i vreme odrzavanja: 2021.10.10 18:00</p>

                <p>Tip manifestacije: Koncert</p>

                <p>Lokacija (mapa): telep</p>

                <p>Status manifestacije: <span id="aktivno">Aktivno</span></p>
        
                <div>
                    <input type="submit" name = "rezervisi" id = "rezervisi" value="Rezervisi kartu" class="btn btn-primary" v-bind:hidden="korisnik.uloga != 'KUPAC'">
                </div>
            </div>

            <img src="./css/rambo.jpeg" alt="Rambo Koncert Primer" id="slikaManifestacije">
                
        </div>

        <ul class="list-group list-group-flush ">
            <li class="list-group-item text-white bg-dark border-primary">Komentari</li>
            <li class="list-group-item text-white bg-dark border-light">ocena: 5 <br/> markuza: bruh</li>
            <li class="list-group-item text-white bg-dark border-light">ocena: 4 <br/> markuza: nije FAPovao</li>
        </ul>

    </div>

    </div>
    
    `
	, 
	methods : {
        
	},
	mounted () {
    }
});