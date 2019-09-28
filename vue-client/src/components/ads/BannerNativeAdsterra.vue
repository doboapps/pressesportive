<template>
  <div>
    <Script async="async" data-cfasync="false" :src="adsSrcScript"></Script>
    <div :id="divId"></div>
  </div>
</template>

<script>
export default {
  name: 'BannerNativeAdsterra',
  props: {
    bannerId: String,
  },
  data() {
    return {
      divId: `container-${this.bannerId}`,
      adsSrcScript : `//pl15181307.pvclouds.com/${this.bannerId}/invoke.js`
    }
  }
}
</script>