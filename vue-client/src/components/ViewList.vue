<template>
  <div class="container-view">
    <div class="background">
      <div class="spinner-container">
        <div class="lds-ring">
          <div></div><div></div><div></div><div></div>
        </div>
      </div>
      <p class="loading-txt">{{ $t('lang.viewList.loading') }}...</p>
    </div>
    <b-modal id="modal-error" size="sm" ok-only ok-variant="secondary" @hide="returnToMain" :ok-title="this.$t('lang.viewList.ok')">
      {{ $t('lang.viewList.out-service') }}
    </b-modal>
    <div class="header">
      <img @click="returnToMain()" src="@/assets/arrow-return.png" alt="Arrow return" class="arrow"/>
      <img src="@/assets/header.jpg" alt="title" class="bg"/>
      <h4>{{ title }}</h4>
    </div>
    <div v-for="(news, index) in newsList" :key="index + news.date" class="item">
      <a target="_blank" :href="news.link + '?dobotab=new'" class="new-tag-link"></a>
        <img src="@/assets/dark-field.jpg" alt="green" class="bg-item" />
        <b-row class="content">
          <b-col cols="3">
            <img src="@/assets/img-items.jpg" class="img-base" alt="stadium" />
            <img v-if="index < 12 && news.image_url" :src="news.image_url" />
            <img v-else src="@/assets/img-items.jpg" alt="stadium" />
          </b-col>
          <b-col cols="9" class="title">{{ news.title }}</b-col>
          <hr class="hr-1" />
          <b-col cols="12" class="description">{{ show300Character(news.description) }}...</b-col>
          <hr class="hr-2" />
          <b-col cols="12" class="time">{{ diffTime(news.date) }}</b-col>
        </b-row>
        <Banner-adsterra v-if="index === 4 && showMediumBanner" class="banner-medium-adsterra" bannerId="5c52d4821c92f24a380ec5e6aee43b46" width=300 height=250 :style="{'background-image': `url(${require('@/assets/dark-field.jpg')})`}"></Banner-adsterra>
        <Banner-adsterra v-if="index === 1" class="banner-small-adsterra" bannerId="64965c8a8ad05177166b1de1cf7c2e76" width=320 height=50></Banner-adsterra>
    </div>
    <Banner-native-adsterra class="banner-native-adsterra" bannerId="c86208c13af4c7247c322b0727add52f" :style="{'background-image': `url(${require('@/assets/dark-field.jpg')})`}"></Banner-native-adsterra>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import BannerAdsterra from './ads/BannerAdsterra'
import BannerNativeAdsterra from './ads/BannerNativeAdsterra'

export default {
  name: 'ViewList',
  computed: mapState(['newsList', 'title']),
  components: {
    BannerAdsterra,
    BannerNativeAdsterra
  },
  data() {
    return {
      currentDate: new Date().getTime(),
      idDigitalMedia: this.$route.params.id,
      showMediumBanner : false,
    }
  },
  methods: {
    showMediumBannerFunction: function() {
      setTimeout(() => {
        this.showMediumBanner = true
      }, 1500)

    },
    show300Character: description =>
      description.length < 300 ? description : description.substr(1, 300),
    diffTime: function(newsDate) {
      if (!isNaN(newsDate)) {
        return this.$t('lang.viewList.today')
      }
      let diff = (new Date(newsDate).getTime() - this.currentDate) / 1000
      let minutes = Math.abs(Math.round((diff /= 60)))
      let hours = (minutes / 60).toFixed(0)

      return minutes < 60 ? `${minutes} ${this.$t('lang.viewList.minutes')}` : `${hours} ${this.$t('lang.viewList.hours')}`
    },
    returnToMain: function() {
      this.$router.back()
    }
  },
  beforeMount() {
    document.title = window.location.search.replace('?dobotitle=','').replace('%20',' ').replace('%60',"'")

    setTimeout(() => {

      if (this.newsList.length === 0) {
        this.$bvModal.show('modal-error')
      }
      }, 30000)

    localStorage.setItem('digitalMediaName', this.idDigitalMedia)
    this.$store.dispatch('getNewFromAPI', `${this.idDigitalMedia}-fr`)
  },
  mounted () {
    this.showMediumBannerFunction()
  }
}
</script>

<style scoped lang="scss">
.container-view {
  background-color: #162027;
  margin-top: 40px;

  .header {
    z-index: 2;
    position: fixed;
    top: 0;
    border-bottom: 2px solid white;
    width: 100%;

    .arrow {
      position: absolute;
      height: 25px;
      top: 7px;
      left: 10px;
      z-index: 1;
    }
    h4 {
      text-align: center;
      position: absolute;
      width: 100%;
      top: 7px;
      padding: 0 60px;
      color: white;
    }
    .bg {
      width: 100%;
      height: 40px;
    }
  }
  .background {
    width: 100%;
    height: 100%;
    background: #162027;
    position: absolute;

    .spinner {
      position: absolute;
      width: 50%;
      margin-left: 25%;
      margin-top: 150px;
    }
    .loading-txt {
      text-align: center;
      color: #6b737b;
      position: absolute;
      width: 100%;
      margin-top: 60px;
    }
  }
  .item {
    position: relative;
    border-bottom: 2px solid white;
    font-size: 14px;

    .new-tag-link {
      width: 100%;
      height: 100%;
      position: absolute;
      z-index: 1;
    }
    .bg-item {
      position: absolute;
      width: 100%;
      height: 100%;
      z-index: 0;
    }
    .content {
      color: white;
      padding: 15px;
      display: flex;
      align-items: center;
      img {
        width: 100%;
        z-index: 1;
        position: relative;
      }
      .img-base {
        position: absolute;
        right: 0;
        padding: 0 20px;
        z-index: 0;
      }
      .title {
        padding-left: 0;
        line-height: 18px;
      }
      .time {
        margin-top: 10px;
      }
      hr {
        border: 1px solid;
        width: 100%;
        margin: 15px;
        z-index: 0;
        &.hr-1 {
          border-color: orange;
        }
        &.hr-2 {
          border-color: #52a055cc;
          margin-bottom: 0;
        }
      }
    }
    .banner-small-adsterra {
      padding: 15px 0;
      z-index: 1;
      text-align: center;
      width: 100%;
      margin-top: 15px;
      position: relative;
      border-top: 2px solid #03A9F4;
      z-index: 1;
    }
  }
  .banner-medium-adsterra {
    text-align: center;
    background-position: center center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    padding: 15px 0;
    position: relative;
    border-top: 2px solid white;
    z-index: 1;
  }
  .banner-native-adsterra {
    background-position: center center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }

  .spinner-container {
    text-align: center;
    padding-top: 100px;

    .lds-ring {
      display: inline-block;
      position: relative;
      width: 64px;
      height: 64px;
    }

    .lds-ring div {
      box-sizing: border-box;
      display: block;
      position: absolute;
      width: 51px;
      height: 51px;
      margin: 6px;
      border: 6px solid #fff;
      border-radius: 50%;
      animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
      border-color: #6a727a transparent transparent transparent;
    }
    .lds-ring div:nth-child(1) {
      animation-delay: -0.45s;
    }
    .lds-ring div:nth-child(2) {
      animation-delay: -0.3s;
    }
    .lds-ring div:nth-child(3) {
      animation-delay: -0.15s;
    }
    @keyframes lds-ring {
      0% {
        transform: rotate(0deg);
      }
      100% {
        transform: rotate(360deg);
      }
    }
  }
}
</style>