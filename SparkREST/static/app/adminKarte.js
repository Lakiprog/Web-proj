Vue.component("adminKarte", {
	data: function () {
		    return {
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
                <form action="" method="GET">
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="naziv">Naziv:</label>
                            <input type="text" name = "naziv" id = "naziv" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="od">Od:</label>
                            <input type="date" name = "od" id = "od" class="form-control">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="do">Do:</label>
                            <input type="date" name = "do" id = "do" class="form-control">
                        </div>

                        <div class="form-group col-md-1">
                            <label for="tip">Tip:</label>
                            <select name="tip" id="tip" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="REGULAR">Regular</option>
                                <option value="FAN_PIT">Fan Pit</option>
                                <option value="VIP">VIP</option>
                            </select>
                        </div>

                        <div class="form-group col-md-1">
                            <label for="status">Status:</label>
                            <select name="status" id="status" class="form-control">
                                <option value="SVE">Sve</option>
                                <option value="REZERVISANO">Rezervisana</option>
                                <option value="ODUSTANO">Odustano</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="minimum">Cena minimum:</label>
                            <input type="number" name = "minimum" id = "minimum" class="form-control" min="0" value="0">
                        </div>
                
                        <div class="form-group col-md-2">
                            <label for="maximum">Cena maximum:</label>
                            <input type="number" name = "maximum" id = "maximum" class="form-control" min="0" value="1000000">
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="sortiranjePo">Sortiraj po:</label>
                            <select name="sortiranjePo" id="sortiranjePo" class="form-control">
                                <option value="NAZIV">Naziv</option>
                                <option value="DATUM">Datum</option>
                                <option value="CENA">Cena</option>
                            </select>
                        </div>
        
                        <div class="form-group col-md-2">
                            <label for="nacinSortiranja">Sortiraj:</label>
                            <select name="nacinSortiranja" id="nacinSortiranja" class="form-control">
                                <option value="RASTUCE">Rastuce</option>
                                <option value="OPADAJUCE">Opadajuce</option>
                            </select>
                        </div>
                    </div>
            
                    <div>
                        <input type="submit" name = "filtriraj" id = "filtriraj" value="Filtriraj" class="btn btn-primary">
                    </div>
                </form>
            </div>
          </div>

        </div>
    
    <div class="card text-white bg-dark mb-3 w-75">

    <table class="table table-hover table-dark">
    <tr>
        <th>K.Ime Kupca</th>
        <th>Ime i prezime</th>
        <th>Tip</th>
		<th>Broj mesta</th>
		<th>Manifestacija</th>
		<th>Datum</th>
        <th>Status</th>
    </tr>

    <tr>
        <td>markuza</td>
        <td>Petar Markovic</td>
        <td>Koncert</td>
        <td>1000</td>
        <td>Primer Koncert Ramba</td>
        <td>2021.10.10</td>
        <td style="color:green;">Rezervisano</td>
    </tr>

    <tr>
        <td>markuza</td>
        <td>Petar Markovic</td>
        <td>Festival</td>
        <td>10000</td>
        <td>Dombos Fest</td>
        <td>2021.08.08</td>
        <td style="color:red;">Odustano</td>
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