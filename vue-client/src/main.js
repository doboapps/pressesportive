import Vue from 'vue'
import App from './App.vue'
import store from './store'

import VueRouter from 'vue-router'
import routes from './routes';

import i18n from './lang'

import BootstrapVue from 'bootstrap-vue'
import Ads from 'vue-google-adsense'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


Vue.use(VueRouter)
Vue.use(BootstrapVue)
Vue.config.productionTip = false

// Adsense.
Vue.use(require('vue-script2'))
Vue.use(Ads.Adsense)
Vue.use(Ads.InArticleAdsense)
Vue.use(Ads.InFeedAdsense)

// Maybe remove this.
document.cookie = 'dobo-apps=true'

const router = new VueRouter({mode: 'history', routes})

new Vue({
  store,
  router,
  i18n,
  render: h => h(App)
}).$mount('#app')
