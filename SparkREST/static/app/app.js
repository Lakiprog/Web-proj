const Registration = { template: '<registration></registration' };
const Login = { template: '<login></login>' };
const Pocetna = { template: '<pocetna-strana></pocetna-strana>'};

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/register', component: Registration },
        { path: '/login', component: Login },
        { path: '/pocetna', component: Pocetna },
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