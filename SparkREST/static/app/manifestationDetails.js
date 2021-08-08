Vue.component("manifestation-details", {
	data: function () {
		    return {
                manifestation: {},
                comments: [],
                user: {},
                selected: false,
                type: "REGULAR",
                numberOfCards: "1",
                customerType: "BRONZANI",
                calculated: false,
                price: 0,
                grade: 0,
                rateable: false,
                comment: "",
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
                <p  v-if="manifestation.brojOcena > 0" class="card-text">Prosecna ocena: {{(manifestation.sumaOcena / manifestation.brojOcena).toFixed(2)}}</p>
                <p  v-else>Prosecna ocena: 0</p>

                <p>Broj Mesta: {{manifestation.brMesta}}</p>
                
                <p>Broj preostalih karata: {{manifestation.brSlobodnihMesta}}</p>

                <p>Cene:</p>
                <p class="utabovano">Regular - {{manifestation.cenaRegular}}RSD</p>
                <p class="utabovano">Fan Pit - {{manifestation.cenaRegular * 2}}RSD</p>
                <p class="utabovano">VIP - {{manifestation.cenaRegular * 4}}RSD</p>

                <p>Datum i vreme odrzavanja: {{manifestation.datumVremePocetka}}  -  {{manifestation.datumVremeKraja}}</p>

                <p>Tip manifestacije: {{manifestation.tip}}</p>

                <div id="map" class="map" style="width:400px;"></div>

                <br><br>

                <p v-if="manifestation.status == 'AKTIVNO'">Status manifestacije: <span style="color:green;">{{manifestation.status}}</span></p>
                <p v-else>Status manifestacije: <span style="color:red;">{{manifestation.status}}</span></p>
        
                <div>
                    <input type="button" name="rezervisi1" id="rezervisi1" value="Rezervisite kartu" class="btn btn-primary" v-bind:hidden="user.uloga != 'KUPAC' || selected || manifestation.status == 'NEAKTIVNO' || (new Date(manifestation.datumVremePocetka) < new Date())" v-on:click="reserve()"/>
                </div>

                <br>

                <div v-bind:hidden="!selected">
                    <label for="types">Odaberite tip karte: </label>

                    <select name="types" id="types" v-model="type">
                        <option value="REGULAR">Regular</option>
                        <option value="FANPIT">Fan Pit</option>
                        <option value="VIP">VIP</option>
                    </select>

                    <br>

                    <label>Broj karata: </label>
                    <input type="number" min="1" v-model="numberOfCards"/>

                    <br>

                    <label v-bind:hidden="!calculated">Vasa cena: {{price}}</label>

                    <div>
                        <input type="button" name="izracunaj" id="izracunaj" value="Izracunajte cenu" class="btn btn-primary" v-bind:hidden="!selected" v-on:click="calculatePrice()"/>
                        <input type="button" name="rezervisi2" id="rezervisi2" value="Zavrsite rezervaciju" class="btn btn-primary" v-bind:hidden="!calculated" v-on:click="reserve()"/>
                    </div>
                </div>
            </div>

            <img v-bind:src="manifestation.posterLink" alt="slika nemoze da se ucita" id="slikaManifestacije">
                
        </div>

        <div v-bind:hidden="user.uloga != 'KUPAC' || !rateable">
            <h2>Moja ocena(unesite komentar pa zatim ocenite klikom na zvezdicu): </h2>
	        <div class="star-rating" v-on:click="clickStar()">
                <span class="fa fa-star-o" data-rating="1" ></span>
				<span class="fa fa-star-o" data-rating="2"></span>
				<span class="fa fa-star-o" data-rating="3"></span>
				<span class="fa fa-star-o" data-rating="4"></span>
				<span class="fa fa-star-o" data-rating="5"></span>
				<input type="hidden" name="whatever1" class="rating-value" v-model="grade">
            </div>
            <textarea rows="6" style="width: 50%" name="text" v-model="comment"></textarea>
        </div>

        <br>

        <div v-bind:hidden="comments.length == 0">
        <label class="list-group-item text-white bg-dark border-primary">Komentari: </label>
        <ul class="list-group list-group-flush" v-for="comm in comments">
            <li v-bind:hidden="(user.uloga == 'KUPAC' || user.uloga == 'GOST') && !comm.odobren"class="list-group-item text-white bg-dark border-light">Ocena: {{comm.ocena}}<br/>{{comm.kIme}}: {{comm.komentar}}</li>
        </ul>
        </div>

    </div>

    </div>
    
    `
	, 
	methods : {
        SetRatingStar: function(){
    		let self = this;
    		var $star_rating = $('.star-rating .fa');
    		return $star_rating.each(function() {
    			$star_rating.siblings('input.rating-value').val(self.grade);
			    if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
			      return $(this).removeClass('fa-star-o').addClass('fa-star');
			    } else {
			      return $(this).removeClass('fa-star').addClass('fa-star-o');
			    }
			});
    	},
    	clickStar: function() {
    	  	axios
		        .put("/rest/manifestations/rateManifestation/" + this.manifestation.id + "/" + "/" + this.user.id + "/" + this.grade, this.comment)
		        .then(response => {
                    if (response.data == "success") {
                        toast("Uspesno ste ostavili svoju reviziju.");
                    } else {
                        toast("Doslo je do greske.");
                    }
		        });
            axios
            .get("/rest/comments/getManifestationComments/" + this.manifestation.id)
            .then(response => {
                this.comments = response.data;
            });
		},
        calculatePrice: function() {
            this.calculated = true;

            this.price = this.manifestation.cenaRegular;

            if (this.type != "REGULAR") {
                if (this.type == "FANPIT")
                    this.price *= 2;
                else if (this.type == "VIP")
                    this.price *= 4;
            }

            if (this.customerType != "BRONZANI") {
                if (this.customerType == "SREBRNI")
                    this.price = (this.price / 100) * 97;
                else if (this.customerType == "ZLATNI")
                    this.price = (this.price / 100) * 95;
            }

            this.price *= this.numberOfCards;
        },
        reserve: function() {
            if (!this.selected) {
                this.selected = true;
                
                axios
                .get("/rest/users/getCustomerType/" + this.user.id)
                .then(response => {
                    this.customerType = response.data;
                });

                return;
            }

            axios
            .post("/rest/cards/reserveCard/" + this.type + "/" + this.manifestation.id + "/" + this.numberOfCards)
            .then(response => {
                if (response.data == "success") {
                    toast("Karta uspesno rezervisana.");
                } else {
                    toast("Doslo je do greske.");
                }
            });
            this.selected = false;
        },
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
        let self = this;
    	var $star_rating = $('.star-rating .fa');
    	$star_rating.on('click', function() {
    	  self.grade = $(this).data('rating');
		  $star_rating.siblings('input.rating-value').val($(this).data('rating'));
		  return self.SetRatingStar();
		});
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                this.korisnik = response.data;
                this.user = response.data;
            }
            axios
            .get("/rest/manifestations/manifestation/" + this.$route.params.id)
            .then(response => {
                if (!response.data) {
                    this.$router.push({ name: "Home" });
                } else {
                    this.manifestation = response.data;
                    this.manifestation.datumVremePocetka = this.manifestation.datumVremePocetka.replace('T', ' ');
                    this.manifestation.datumVremeKraja = this.manifestation.datumVremeKraja.replace('T', ' ');
                    this.showMap();
                }
                if (this.user.uloga == "KUPAC") {
                    axios
                    .get("/rest/manifestations/rateable/" + this.manifestation.id + "/" + this.user.id)
                    .then(response => {
                        if (response.data) {
                            var manif_date = new Date(this.manifestation.datumVremeKraja);
                            var current_date = new Date();
                            if (manif_date < current_date) {
                                this.rateable = true;
                            }
                        }
                    }); 
                }
                axios
                .get("/rest/comments/getManifestationComments/" + this.manifestation.id)
                .then(response => {
                    this.comments = response.data;
                });
            });
            this.SetRatingStar();
        });
        
    }
});