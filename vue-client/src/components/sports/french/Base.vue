<template>
  <div>
    <Main></Main>
    <View-list></View-list>
  </div>
</template>


<script>
import Main from './Main.vue'
import ViewList from './ViewList.vue'
import { mapState } from 'vuex'

export default {
  name: 'Base',
    components: {
    Main,
    ViewList,
  },
}
</script>