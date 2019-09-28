<template>
  <div>
    <Script>{{ adsIframeAttributes }}</Script>
    <Script :src="adsSrcScript"></Script>
  </div>
</template>

<script>
export default {
  name: 'BannerAdsterra',
  props: {
    bannerId: String,
    height: String,
    width: String
  },
  data() {
    return {
      adsIframeAttributes: `atOptions = {
        'key' : '${this.bannerId}',
        'format' : 'iframe',
        'height' : ${parseInt(this.height)},
        'width' : ${parseInt(this.width)},
        'params' : {}
      };`,
      adsSrcScript : 'http' + (location.protocol === 'https:' ? 's' : '') + '://www.bcloudhost.com/' + this.bannerId + '/invoke.js'
    }
  }
}
</script>