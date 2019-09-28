import axios from 'axios'

const rssApi = {
  url: 'http://85.208.22.22:8000/rss-dobo/',
  token: localStorage.getItem('token') ? localStorage.getItem('token') : 'NO-TOKEN',

  /**
   * @param {string} newPapperName
   * @returns {Promise<boolean>}
   */
  getNews(digitalMediaName) {
    return axios.post(this.url, { digitalMediaName })
  }
}

export default rssApi
