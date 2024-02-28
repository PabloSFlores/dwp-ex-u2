import Vue from 'vue'
import VueRouter from 'vue-router'
import ListBooks from '../components/Views/ListBooksBooks'

Vue.use(VueRouter)
 
const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/listBooks',
      name: 'listBooks',
      component: ListBooks
    }
  ]
})

export default router