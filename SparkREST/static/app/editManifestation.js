Vue.component("edit-manifestation", {
	data: function () {
		    return {
                currentPosition: { lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad" },
                manifestation: {naziv : "", brMesta: 0, cenaRegular: 0, tip: "KONCERT", status: "NEAKTIVNO", datumVremePocetka: "", datumVremeKraja: "", posterLink: "", lokacija: {}}
		    }
	},
	template: ` 

    <div>

    <br><br>

    <h2 align=center>Azuriranje manifestacije</h2>

    <br>
    
    <div class="card text-white bg-dark mb-3 w-75">

        <div class="card-body">
            
                <div class="form-group">
                    <label for="naziv">Naziv:</label>
                    <input type="text" name = "naziv" v-model="manifestation.naziv" id = "naziv" class="form-control form-control-sm" required>
                </div>
                
                <div class="form-group">
                    <label for="brMesta">Broj mesta:</label>
                    <input type="number" name = "brMesta" v-model="manifestation.brMesta" id = "brMesta" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="cena">Cena regularne karte:</label>
                    <input type="number" name = "cena" v-model="manifestation.cenaRegular" id = "cena" class="form-control form-control-sm" min="0" required>
                </div>
        
                <div class="form-group">
                    <label for="datum">Datum i vreme odrzavanja:</label>
                    <input type="datetime-local" name = "datum" v-model="manifestation.datumVremePocetka" id = "datum" class="form-control form-control-sm" required>
                </div>

                <div class="form-group">
                    <label for="datumKraja">Kraj odrzavanja:</label>
                    <input type="datetime-local" name = "datumKraja" v-model="manifestation.datumVremeKraja" id = "datumKraja" class="form-control form-control-sm" required>
                </div>
        
                <div class="form-group">
                    <label for="tip">Tip manifestacije:</label>
                    <select name="tip" id="tip" v-model="manifestation.tip" class="form-control form-control-sm">
                        <option value="KONCERT">Koncert</option>
                        <option value="FESTIVAL">Festival</option>
                        <option value="POZORISTE">Pozoriste</option>
                        <option value="DRUGO">Drugo</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="poster">Slika postera(ako ocete da menjate):</label>
                    <input type="file" name = "poster" v-on:change="posterChanged" id = "poster" class="form-control form-control-sm" accept="image/png, image/jpeg">
                </div>

                <img id="preview" src="#" alt="poster">
        
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
        
                <div>
                    <input type="button" name = "napravi" id = "napravi" value="Azurirajte manifestaciju" class="btn btn-primary" v-on:click="editManifestation()">
                </div>
            
        </div>

    </div>

    </div>
    
    `
	, 
	methods : {
        posterChanged : function(event) {
            const reader = new FileReader();

            reader.addEventListener("load", () => {
                this.manifestation.posterLink = reader.result;
                preview.src = reader.result;
            })

            reader.readAsDataURL(event.target.files[0]);
        },
        isInformationValid: function() {
            let nameValid = Boolean(this.manifestation.naziv);
            let seatsValid = Boolean(this.manifestation.brMesta);
            let startDateValid = Boolean(this.manifestation.datumVremePocetka);
            let endDateValid = Boolean(this.manifestation.datumVremeKraja);

            return nameValid && seatsValid && startDateValid && endDateValid;
        },
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
                    center: ol.proj.fromLonLat([self.manifestation.geoDuzina, self.manifestation.geoSirina]),
                    zoom: 15
                })
            });

            var marker;

            setMarker = function(position) {
                marker = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat(position)));
                vectorSource.addFeature(marker);
            }

            setMarker([self.manifestation.geoDuzina, self.manifestation.geoSirina]);

            map.on("click", function(event) {
                let position = ol.proj.toLonLat(event.coordinate);
                self.currentPosition.lat = parseFloat(position.toString().split(",")[1]).toFixed(6);
                self.currentPosition.lon = parseFloat(position.toString().split(",")[0]).toFixed(6);
                vectorSource.clear();
                setMarker(position);
                self.reverseGeolocation(position);
            });

        },
        editManifestation: function(){
            if (!this.isInformationValid()) {
                $.toast({text: "Popunite sva polja", icon: "error"});
                return;
            }

            let splited = this.currentPosition.adresa.split(", ");
            let grad = splited[1];
            let ulicaSplit = splited[0].split(" ");
            
            let number = ulicaSplit.pop();
            if(isNaN(number)){
                ulicaSplit.push(number);
                number = "";
            }
            let road = ulicaSplit.join(" ");

            this.manifestation.lokacija = {
                duzina: this.currentPosition.lon,
                sirina: this.currentPosition.lat,
                adresa: {
                    ulica : road,
                    broj : number,
                    mesto : grad
                }
            }

            let startDate = new Date(this.manifestation.datumVremePocetka);
            let endDate = new Date(this.manifestation.datumVremeKraja);
            if(startDate < endDate){
                axios
                .post("/rest/manifestations/editManifestation", this.manifestation)
                .then(response => {
                    if (response.data == "success") {
                        $.toast("Uspesno ste azurirali manifestaciju.");
                        this.$router.push({ name: "SellerManifestations" });
                    } else {
                        $.toast(response.data);
                    }
            });
            }else{
                $.toast("Kraj manifestacije mora da bude posle pocetka");
            }
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
        .get("/rest/manifestations/manifestation/" + this.$route.params.id)
        .then(response => {
            if (!response.data) {
                this.$router.push({ name: "SellerManifestations" });
            } else {
                this.manifestation = response.data;
                preview.src = this.manifestation.posterLink;
                this.manifestation.posterLink = "";
                this.currentPosition.lon = this.manifestation.geoDuzina;
                this.currentPosition.lat = this.manifestation.geoSirina;
                
                split = this.manifestation.adresa.split(", ")
                adresa = split[1] + ", " + split[0];
                this.currentPosition.adresa = adresa;

                this.showMap();
            }
    });
    }
});