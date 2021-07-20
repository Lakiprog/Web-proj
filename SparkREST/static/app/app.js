const Home = { template: '<home-page></home-page>'};
const Registration = { template: '<registration></registration>' };
const Login = { template: '<login></login>' };
const Profile = { template: '<profile></profile>' };
const CreateManifestation = { template: '<create-manifestation></create-manifestation>' };

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home },
        { path: '/register', component: Registration },
        { path: '/login', component: Login },
        { path: '/profile', component: Profile },
        { path: '/createManifestation', component: CreateManifestation },
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