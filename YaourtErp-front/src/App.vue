<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import TitleView from "@/views/TitleView.vue";
</script>

<template>
  <div style="display: flex; flex-direction: column; width: 100%">


    <header style="display: flex; flex-direction: column; gap:30px; width: 100%">
      <img alt="Vue logo" class="logo" src="@/assets/yogourt.png" width="200" height="200" style="margin:0;"/>

      <div class="wrapper" style="display:flex;flex-direction:row;align-items:center;width:100%">
        <TitleView msg="Yaourt ERP" />

        <nav style="display:flex; justify-content: center; align-items: center">
          <RouterLink to="/">Compute</RouterLink>
          <RouterLink to="/settings">Settings</RouterLink>
        </nav>
      </div>

    </header>


    <RouterView />


  </div>

</template>

<style scoped>
header {
  line-height: 1.5;

}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
