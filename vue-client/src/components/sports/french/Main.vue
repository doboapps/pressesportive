<template>
  <b-container  v-if="DigitalMediaCountry()" fluid class="main">
    <div class="background">
      <img src="@/assets/main-bg.jpg" alt="stadium" />
    </div>
    <b-col cols=12 sm="6" class="ball-row">
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'beinsports'}}">
          <img @click="changeTitleText('beIN Sports')" src="@/assets/french/balls/beinsports.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'sports'}}">
          <img @click="changeTitleText('Sport.fr')" src="@/assets/french/balls/sports.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'france24'}}">
          <img @click="changeTitleText('France24')" src="@/assets/french/balls/france24.png" alt="football ball" />
        </router-link>
      </b-col>
    </b-col>
    <b-col cols=12 sm="6" class="ball-row">
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'eurosport'}}">
          <img @click="changeTitleText('Eurosport')" src="@/assets/french/balls/eurosport.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'lequipe'}}">
          <img @click="changeTitleText('L`EQUIPE')" src="@/assets/french/balls/lequipe.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: '20minutes'}}">
          <img @click="changeTitleText('20 minutes')" src="@/assets/french/balls/20minutes.png" alt="football ball" />
        </router-link>
      </b-col>
    </b-col>
    <b-col cols=12 sm="6" class="ball-row">
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'lemonde'}}">
          <img @click="changeTitleText('Le Monde')" src="@/assets/french/balls/lemonde.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'rmcsport'}}">
          <img @click="changeTitleText('BFM RCM Sport')" src="@/assets/french/balls/rmcsport.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'francetvinfo'}}">
          <img @click="changeTitleText('Franceinfo:')" src="@/assets/french/balls/francetvinfo.png" alt="football ball" />
        </router-link>
      </b-col>
    </b-col>
    <b-col cols=12 sm="6" class="ball-row">
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'europe1'}}">
          <img @click="changeTitleText('Europe1')" src="@/assets/french/balls/europe1.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'lefigaro'}}">
          <img @click="changeTitleText('Lefigaro.fr')" src="@/assets/french/balls/lefigaro.png" alt="football ball" />
        </router-link>
      </b-col>
      <b-col cols="4" class="ball">
        <router-link :to="{name: 'news-list', params: {id: 'maxifoot'}}">
          <img @click="changeTitleText('Maxifoot')" src="@/assets/french/balls/maxifoot.png" alt="football ball" />
        </router-link>
      </b-col>
    </b-col>
    <div class="info-container">
    <router-link :to="{name: 'info'}"></router-link>
    <!-- <a :href="{name: 'info'}" target="_blank"></a> -->
    <img @click="changeTitleText('Info')" src="@/assets/info.png" class=" info" alt="info icon" />
    </div>
  </b-container>
</template>

<script>
export default {
  name: 'Main',
  methods: {
    changeTitleText(title) {
      this.$store.commit('changeTitleText', title)
    },
    DigitalMediaCountry() {
      return this.$route.params.country === 'france-sports'
    }
  },
    beforeMount() {
    // Language.
    this.$i18n.locale  = 'fr'

  },
  created() {

  },
}
</script>

<style scoped lang="scss">
.main {
  position: absolute;
  height: 100%;
  padding: 0;
  overflow: hidden;
  z-index: 2;
  .background {
    position: absolute;
    height: 100%;
    img {
      height: 100%;
    }
  }
  .ball-row {
    height: 23%;
    display: flex;
    align-items: center;
    padding: 15px;
    .ball {
      img {
        width: 100%;
      }
    }
  }
  .info-container {
    a {
      position: absolute;
      width: 40px;
      height: 40px;
      z-index: 1;
      right: 10px;
    }
    .info {
      position: absolute;
      width: 30px;
      right: 15px;
      bottom: 15px;
    }
  }

  @media (orientation: landscape) {
    .background {
      width: 100%;
      img {
        width: 100%;
      }
    }
    .ball-row {
      float: left;
      margin-top: 10%;
      .ball {
        padding: 5px;
      }
    }
  }

  @media (min-width: 767px) {
    .background {
      width: 100%;
      img {
        width: 100%;
      }
    }
    .ball-row {
      float: left;
      margin-top: 10%;
      .ball {
        padding: 5px;
      }
    }
  }
}
</style>