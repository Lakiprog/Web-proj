Vue.component("create-manifestation", {
	data: function () {
		    return {
                currentPosition: { lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad" },
		    }
	},
	template: ` 

    <div>

    <br><br>

    <h2 align=center>Kreiranje manifestacije</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="naziv">Naziv:</label>
                    <input type="text" name = "naziv" id = "naziv" class="form-control form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="brMesta">Broj mesta:</label>
                    <input type="number" name = "brMesta" id = "brMesta" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="cena">Cena regularne karte:</label>
                    <input type="number" name = "cena" id = "cena" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="datum">Datum i vreme odrzavanja:</label>
                    <input type="datetime-local" name = "datum" id = "datum" class="form-control form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="tip">Tip manifestacije:</label>
                    <select name="tip" id="tip" class="form-control form-control-sm">
                        <option value="KONCERT">Koncert</option>
                        <option value="FESTIVAL">Festival</option>
                        <option value="POZORISTE">Pozoriste</option>
                        <option value="DRUGO">Drugo</option>
                    </select>
                </div>
        
                <br>

                <div id="map" class="map"></div>

                <br><br>

                <div class="form-group">
                    <label for="geografska_sirina">Geografska sirina: </label>
                    <input type="number" name="sirina" v-model="currentPosition.lat" disabled/>

                    <br><br>

                    <label for="geografska_duzina">Geografska duzina: </label>
                    <input type="number" name="duzina" v-model="currentPosition.lon" disabled/>

                    <br><br>
                    
                    <label for="geografska_duzina">Adresa: </label>
                    <input type="text" name="adresa" v-model="currentPosition.adresa" disabled/>
                </div>
        
                <div class="form-group">
                    <label for="poster">Slika postera:</label>
                    <input type="file" name = "poster" id = "poster" class="form-control form-control-sm" accept="image/png, image/jpeg" required>
                </div>
        
                <div>
                    <input type="submit" name = "napravi" id = "napravi" value="Napravijte manifestaciju" class="btn btn-primary">
                </div>
            </form>
        </div>

    </div>

    </div>
    
    `
	, 
	methods : {
        reverseGeolocation: function(coords) {
            let self = this;
            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
                .then(function(response) {
                    return response.json();
                }).then(function(json) {
                    console.log(json);
                    if (json.address.house_number == undefined) {
                        if (json.address.building == undefined) {
                            self.currentPosition.adresa = json.address.road + ", " + json.address.city;
                        } else {
                            self.currentPosition.adresa = json.address.road + ", " + json.address.building + ", " + json.address.city;
                        }
                    } else {
                        self.currentPosition.adresa = json.address.road + " " + json.address.house_number + ", " + json.address.city;
                    }

                });
        },
        showMap: function() {
            let self = this;

            var vectorSource = new ol.source.Vector({});
            var vectorLayer = new ol.layer.Vector({ source: vectorSource });

            var map = new ol.Map({
                target: 'map',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    }), vectorLayer
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([19.8335, 45.2671]),
                    zoom: 11
                })
            });

            var marker;

            setMarker = function(position) {
                marker = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat(position)));
                vectorSource.addFeature(marker);
            }

            map.on("click", function(event) {
                let position = ol.proj.toLonLat(event.coordinate);
                self.currentPosition.lat = parseFloat(position.toString().split(",")[1]).toFixed(6);
                self.currentPosition.lon = parseFloat(position.toString().split(",")[0]).toFixed(6);
                vectorSource.clear();
                setMarker(position);
                self.reverseGeolocation(position);
            });

        },
	},
	mounted () {
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
            }
        });
        this.showMap();
    }
});