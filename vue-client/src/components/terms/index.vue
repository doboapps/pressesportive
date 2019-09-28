<template>
  <div class="language-container">
    <div class="language">
      <a href="/terms?lang=es">Español</a> /
      <a href="/terms?lang=it">Italiano</a> /
      <a href="/terms?lang=de">Deutsch</a> /
      <a href="/terms?lang=fr">Française</a>
    </div>
    <Term-es v-if="language === 'es'"></Term-es>
    <Term-it v-if="language === 'it'"></Term-it>
    <Term-de v-if="language === 'de'"></Term-de>
    <Term-fr v-if="language === 'fr'"></Term-fr>

  </div>
</template>

<script>
import TermEs from './es.vue'
import TermIt from './it.vue'
import TermDe from './de.vue'
import TermFr from './fr.vue'

export default {
  components: {
    TermEs,
    TermDe,
    TermIt,
    TermFr
  },
  data() {
    return {
      language: window.location.search.substr(1).replace('lang=', '')
    }
  }
}
</script>

<style scoped lang="scss">
  .language-container {
    color: #5d5e60;
    width: 80%;
    a {
      color: rgb(57, 99, 157);
    }
  }

  .language {
    margin: 15px;
    text-align: right;
    position: relative;
    width: 100%;
  }
</style>