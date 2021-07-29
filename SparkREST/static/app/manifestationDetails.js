Vue.component("manifestation-details", {
	data: function () {
		    return {
                manifestation: {},
                user: {},
		    }
	},
	template: ` 

    <div>

    <br><br>

    <h2 align=center>{{manifestation.naziv}}</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3">

        <div class="card-body">
            
            <div id="informacije">
                <p>Prosecna ocena: {{(manifestation.sumaOcena / manifestation.brojOcena).toFixed(2)}}</p>

                <p>Broj Mesta: {{manifestation.brMesta}}</p>
                
                <p>Broj preostalih karata: 1000</p>

                <p>Cene:</p>
                <p class="utabovano">Regular - {{manifestation.cenaRegular}}RSD</p>
                <p class="utabovano">Fan Pit - {{manifestation.cenaRegular * 2}}RSD</p>
                <p class="utabovano">VIP - {{manifestation.cenaRegular * 4}}RSD</p>

                <p>Datum i vreme odrzavanja: {{manifestation.datumVremePocetka}}  -  {{manifestation.datumVremeKraja}}</p>

                <p>Tip manifestacije: {{manifestation.tip}}</p>

                <div id="map" class="map"></div>

                <p>Status manifestacije: <span id="aktivno">{{manifestation.status}}</span></p>
        
                <div>
                    <input type="submit" name = "rezervisi" id = "rezervisi" value="Rezervisi kartu" class="btn btn-primary"v-bind:hidden="user.uloga != 'KUPAC'">
                </div>
            </div>

            <img v-bind:src="manifestation.posterLink" v-bind:alt="manifestation.naziv" id="slikaManifestacije">
                
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
        showMap: function(){
            let self = this;
            var map = new ol.Map({
                target: 'map',
                layers: [
                  new ol.layer.Tile({
                    source: new ol.source.OSM()
                  })
                ],
                view: new ol.View({
                  center: ol.proj.fromLonLat([self.manifestation.geoDuzina, self.manifestation.geoSirina]),
                  zoom: 16
                })
              });
             var layer = new ol.layer.Vector({
                 source: new ol.source.Vector({
                     features: [
                         new ol.Feature({
                             geometry: new ol.geom.Point(ol.proj.fromLonLat([self.manifestation.geoDuzina, self.manifestation.geoSirina]))
                         })
                     ]
                 })
             });
             map.addLayer(layer);
        },
	},
	mounted () {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
                this.user = response.data;
            }
        });
        axios
        .get("rest/manifestations/manifestation/" + this.$route.params.id)
        .then(response => {
            if (!response.data) {
                this.$router.push({ name: "Home" });
            } else {
                this.manifestation = response.data;
                this.manifestation.datumVremePocetka = this.manifestation.datumVremePocetka.replace('T', ' ');
                this.manifestation.datumVremeKraja = this.manifestation.datumVremeKraja.replace('T', ' ');
                this.showMap();
            }
        });
    }
});