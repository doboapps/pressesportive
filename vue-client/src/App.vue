
<template>
  <div id="app">
    <router-view :title="title" />
    <!-- <Adsense
      data-ad-client="ca-pub-1050566555014396">
    </Adsense>-->
  </div>
</template>

<script>
import { mapState } from "vuex"

export default {
  computed: mapState(["title"])
}
</script>

<style scoped lang="scss">
$assets: "~@/assets/";

#app {
  overflow: hidden;
  background-color: #162027;
  .header {
    background: url($assets+"/header.jpg");
    height: 45px;
    position: fixed;
    width: 100%;
    z-index: 2;
    border-bottom: 2px solid white;
    ul {
      margin: 0;
      li {
        list-style: none;
        img.arrow-return {
          position: absolute;
          left: 15px;
          top: 10px;
          height: 20px;
        }
        a {
          color: white;
        }
        &.title {
          margin-top: 5px;
          text-align: center;
          color: white;
          font-size: 20px;
          font-weight: 800;
          text-shadow: 2px 2px black;
          position: absolute;
          width: 100%;
          right: 0;
          z-index: -1;
        }
      }
    }
  }
}
</style>
