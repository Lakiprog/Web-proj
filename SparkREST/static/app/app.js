const Registration = { template: '<registration></registration>' };
const Login = { template: '<login></login>' };
const Pocetna = { template: '<pocetna-strana></pocetna-strana>'};
const Profil = { template: '<profile></profile>'};
const RegistracijaProdavca = { template: '<registrujProdavca></registrujProdavca>' };
const AdminManifestacije = { template: '<adminManifestacije></adminManifestacije>' };
const AdminKarte = { template: '<adminKarte></adminKarte>' };
const AdminKorisnici = { template: '<adminKorisnici></adminKorisnici>' };
const AdminKomentari = { template: '<adminKomentari></adminKomentari>' };
const DodajManifestaciju = { template: '<dodajManifestaciju></dodajManifestaciju>' };
const ProdavacManifestacije = { template: '<prodavacManifestacije></prodavacManifestacije>' };
const ProdavacKarte = { template: '<prodavacKarte></prodavacKarte>' };
const ProdavacKomentari = { template: '<prodavacKomentari></prodavacKomentari>' };
const KupacKarte = { template: '<kupacKarte></kupacKarte>' };

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/register', component: Registration },
        { path: '/login', component: Login },
        { path: '/pocetna', component: Pocetna },
        { path: '/profil', component: Profil },
        { path: '/registrujProdavca', component: RegistracijaProdavca },
        { path: '/adminManifestacije', component: AdminManifestacije },
        { path: '/adminKarte', component: AdminKarte },
        { path: '/adminKorisnici', component: AdminKorisnici },
        { path: '/adminKomentari', component: AdminKomentari },
        { path: '/dodajManifestaciju', component: DodajManifestaciju },
        { path: '/prodavacManifestacije', component: ProdavacManifestacije },
        { path: '/prodavacKarte', component: ProdavacKarte },
        { path: '/prodavacKomentari', component: ProdavacKomentari },
        { path: '/kupacKarte', component: KupacKarte },
    ]
});

var app = new Vue({
    router,
    el: '#webRezervacija',
    data: {
        korisnik: {uloga: "GOST"},
    },
    mounted () {

    },
    methods: {

    }
});