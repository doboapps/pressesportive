import Vue from 'vue'
import Vuex from 'vuex'
import rssApi from '../api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    title: '',
    newsList: [],
  },
  mutations: {
    changeTitleText: (state, title) => state.title = title,
    AddNewsToList: (state, newsList) => state.newsList = newsList
  },
  actions: {
    getNewFromAPI({ commit }, newsPapperName) {
      commit('AddNewsToList', [])

      rssApi.getNews(newsPapperName).then(data => {
        commit('AddNewsToList', data.data)

      }).catch(err => {
        console.log('err: ', err)
        if (err.code === 'ECONNREFUSED') throw Error('could not reach server')
      })
    }
  }
})
