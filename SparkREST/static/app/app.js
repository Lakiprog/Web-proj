const Registration = { template: '<registration></registration' };
const Login = { template: '<login></login>' };

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/register', component: Registration },
        { path: '/login', component: Login },
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