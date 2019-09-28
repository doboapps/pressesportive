import Vue from 'vue'
import VueI18n from 'vue-i18n'
import en from './locals/en_UK'
import es from './locals/es_ES'
import fr from './locals/fr_FR'

Vue.use(VueI18n)

export default new VueI18n({
  locale: 'en',
  messages: {
    en: {
      lang: en
    },
    es: {
      lang: es
    },
    fr: {
      lang: fr
    }
  }
})