import { createRouter, createWebHistory } from 'vue-router'
import ComputeView from "@/views/ComputeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'compute',
      component: ComputeView,
    },
    {
      path: '/settings',
      name: 'settings',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/SettingView.vue'),
    },
  ],
})

export default router
