const Home = { template: '<home-page></home-page>' };
const ManifestationDetails = { template: '<manifestation-details></manifestation-details>' };
const Registration = { template: '<registration></registration>' };
const Login = { template: '<login></login>' };
const Profile = { template: '<profile></profile>'};
const RegisterSeller = { template: '<registerSeller></registerSeller>' };
const AdminManifestations = { template: '<adminManifestations></adminManifestations>' };
const AdminCards = { template: '<adminCards></adminCards>' };
const AdminUsers = { template: '<adminUsers></adminUsers>' };
const AdminComments = { template: '<adminComments></adminComments>' };
const CreateManifestation = { template: '<create-manifestation></create-manifestation>' };
const SellerManifestations = { template: '<sellerManifestations></sellerManifestations>' };
const SellerCards = { template: '<sellerCards></sellerCards>' };
const SellerComments = { template: '<sellerComments></sellerComments>' };
const BuyerCards = { template: '<buyerCards></buyerCards>' };

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home, name: "Home" },
        { path: '/manifestation/:id', component: ManifestationDetails, name: "ManifestationDetails" },
        { path: '/register', component: Registration },
        { path: '/login', component: Login, name: "Login" },
        { path: '/profile', component: Profile },
        { path: '/registerSeller', component: RegisterSeller },
        { path: '/adminManifestations', component: AdminManifestations },
        { path: '/adminCards', component: AdminCards },
        { path: '/adminUsers', component: AdminUsers },
        { path: '/adminComments', component: AdminComments },
        { path: '/createManifestation', component: CreateManifestation },
        { path: '/sellerManifestations', component: SellerManifestations },
        { path: '/sellerCards', component: SellerCards },
        { path: '/sellerComments', component: SellerComments },
        { path: '/buyerCards', component: BuyerCards },
    ]
});

var app = new Vue({
    router,
    el: '#webRezervacija',
    data: {
        korisnik: {uloga: "GOST", id: 0},
    },
    mounted () {
        let self = this;
        axios
        .get("/rest/users/getCurrentUser")
        .then(response => {
            if (response.data) {
                self.korisnik = response.data;
            }
        });
        this.$root.$on('sendingUser', (response) => {
            this.korisnik = response;
        });
    },
    methods: {
        logout: function() {
            let self = this;
            axios
                .get("/rest/users/logUserOut")
                .then(function(resp) {
                    if (resp.data == "success") {
                        self.korisnik = { zaposlenjeKorisnika: "GOST", id: 0 };
                        self.$router.push({ name: "Login" });
                        self.$root.$emit('loggingUserOut', self.korisnik);
                        self.$router.go();
                    }
                });
        },
    }
});