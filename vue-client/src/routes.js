import Homepage from './components/Home.vue'
import Info from './components/Info.vue'
import Main from './components/sports/french/Main.vue'
import NewsList from './components/sports/french/NewsList.vue'
import terms from './components/terms'

export default [
    {path: '/', component: Homepage, name: 'home'},
    {path: '/main/:country', component: Main, name: 'main'},
    {path: '/news-list/:id', component: NewsList, name: 'news-list'},
    {path: '/info/', component: Info, name: 'info'},
    {path: '/terms/', component: terms, name: 'terms'},
]
