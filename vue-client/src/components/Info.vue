<template>
  <div class="info-content">
    <div class="header"></div>
    <img src="@/assets/dobo.png" alt="Dobo title" class="dobo-img" />
    <h2>Dobo Apps</h2>
    <a href="mailto:name@email.com" class="email">doboapps@gmail.com</a>
    <div class="google-play-container">
      <a href="https://play.google.com/store/apps/developer?id=Dobo+Apps">
        <img src="@/assets/google-play.jpg" alt="Google Play" class="google-img" />
      </a>
    </div>
    <a :href="'/terms/?lang=' + this.$i18n.locale" rel="noopener noreferrer" class="privacy">Privacy Policy</a>
  </div>
</template>

<style scoped lang="scss">
.header {
  height: 45px;
  position: fixed;
  width: 100%;
  z-index: 3;
  background: white;
  right: 0;
  top: 0;
}
.info-content {
  background: white;
  padding: 100px 30px 30px 30px;
  text-align: center;
  position: absolute;
  width: 100%;
  height: 100%;

  a,
  img {
    width: 100%;
  }

  .dobo-img {
    width: 200px;
    margin-bottom: 40px;
  }

  h2 {
    margin-bottom: 15px;
  }

  a {
    font-weight: 500;
  }

  .google-play-container {
    margin: 50px 0;

    .google-img {
      width: 200px;
    }
  }
}
</style>
