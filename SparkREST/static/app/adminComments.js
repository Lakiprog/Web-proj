Vue.component("adminComments", {
	data: function () {
		    return {
		    }
	},
	template: ` 
    <div class="dinamic">
    <h2 align=center>Pregled Komentara</h2>
    
    <div class="card text-white bg-dark mb-3 w-75">

    <table class="table table-hover table-dark">
    <tr>
        <th>K.Ime Kupca</th>
        <th>Ime i prezime</th>
		<th>Manifestacija</th>
		<th>Komentar</th>
        <th>Ocena</th>
        <th></th>
    </tr>

    <tr>
        <td>markuza</td>
        <td>Petar Markovic</td>
        <td>Primer Koncert Ramba</td>
        <td>bruh</td>
        <td>5</td>
        <td><input type="button" class="btn btn-danger" value="Obrisi" /></td>
    </tr>

    <tr>
        <td>markuza</td>
        <td>Petar Markovic</td>
        <td>Primer Koncert Ramba</td>
        <td>premalo FAPa</td>
        <td>4</td>
        <td><input type="button" class="btn btn-danger" value="Obrisi" /></td>
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