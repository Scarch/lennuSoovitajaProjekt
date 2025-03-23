import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LennuSoovitajaView from '../views/LennuSoovitajaView.vue'
import LendView from '../views/LendView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/lennud',
    name: 'lennud',
    component: LennuSoovitajaView
  },
  {
    path: "/lennud/:id",
    name: "lend",
    component: LendView,
  },
  {
    path: "/lennud/:id/:piletidNr",
    name: "lend",
    component: LendView,
  },
  {
    path: "/:catchAll(.*)",
    name: "lennud",
    component: LennuSoovitajaView,
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
