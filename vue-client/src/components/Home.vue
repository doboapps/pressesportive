<template>
  <div class="info-content">
    <div class="dobo-app">
      <img src="@/assets/dobo.png" alt="Dobo logo" />
    </div>
    <div class="french-app">
      <router-link :to="{name: 'main', params: {country: 'france-sports'}}">
        <img src="@/assets/french/cover-page.png" alt="French App" />
      </router-link>
    </div>
    <div class="google-app">
      <a href="https://play.google.com/store/apps/developer?id=Dobo+Apps" target="_blank">
        <img src="@/assets/google-play.jpg" alt="Google Play" />
      </a>
    </div>
    <a href="/terms/?lang=es" rel="noopener noreferrer" class="privacy">Privacy Policy</a>
  </div>
</template>

<script>
</script>


<style scoped lang="scss">
.info-content {
  text-align: center;
  background-color: white;
  position: absolute;
  height: 100%;
  width: 100%;
  border: 5px solid;
  z-index: 1;

  img {
    max-width: 90%;
  }

  .dobo-app,
  .french-app,
  .google-app {
    width: 100%;
    text-align: center;
  }

  .dobo-app {
    padding: 60px 0;
    img {
      width: 250px;
    }
  }

  .french-app {
    padding-bottom: 60px;
    img {
      width: 200px;
    }
  }

  .google-app {
    img {
      width: 150px;
    }
    margin-bottom: 30px;
  }
}
</style>