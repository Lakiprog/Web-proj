Vue.component("sellerComments", {
	data: function () {
		    return {
                comments: []
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
        <th>Odobren</th>
        <th colspan=2 ></th>
    </tr>

    <tr v-for="c in this.comments">
        <td>{{c.kIme}}</td>
        <td>{{c.ime}} {{c.prezime}}</td>
        <td>{{c.manifestacija}}</td>
        <td>{{c.komentar}}</td>
        <td>{{c.ocena}}</td>
        <td v-if="c.odobren" style="color:green;">Odobren</td>
        <td v-else-if="!c.odbijen">-</td>
        <td v-else style="color:red;">Odbijen</td>
        <td><input v-bind:hidden="c.odobren || c.odbijen" type="button" class="btn btn-primary" value="Odobri" v-on:click="odobri(c)" /></td>
        <td><input v-bind:hidden="c.odobren || c.odbijen" type="button" class="btn btn-danger" value="Odbij" v-on:click="odbij(c)" /></td>
    </tr>

</table>

    </div>
</div>
`
	, 
	methods : {
        odobri: function(c){
            axios
            .post("/rest/comments/odobri", c)
            .then(response => {
                
            });
            c.odobren = true;
        },
        odbij: function(c){
            axios
            .post("/rest/comments/odbij", c)
            .then(response => {
                
            });
            c.odbijen = true;
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
        .get("/rest/comments/getCommentsProdavac")
        .then(response => {
            this.comments = response.data;
        });
    }
});