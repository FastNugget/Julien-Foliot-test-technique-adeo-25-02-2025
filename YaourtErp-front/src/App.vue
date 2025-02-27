<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import TitleView from "@/views/TitleView.vue";
</script>

<template>
  <div class="d-flex flex-column gap-2 w-100">

    <header class="d-flex flex-column gap-2 w-100 align-items-center">

      <img alt="Vue logo" class="logo m-0" src="@/assets/yogourt.png" width="200" height="200"/>

      <div class="wrapper flex-row align-items-center justify-center w-100">

        <TitleView msg="Yaourt ERP" />

        <nav class="d-flex flex-row justify-content-around align-items-center">
          <div class="d-flex flex-row justify-center align-items-center">
            <RouterLink to="/">Compute</RouterLink>
            <RouterLink to="/settings">Settings</RouterLink>
          </div>
        </nav>

      </div>

    </header>

    <RouterView/>

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
  margin-top: 1rem;
  margin-bottom: 2rem;
}

nav a.router-link-exact-active {
  color: red!important;
  text-decoration: none!important;
}

nav a.router-link-exact-active:hover {
  background-color: transparent!important;
}

nav a {
  color: #575757!important;
  text-decoration: none!important;
  background-color: transparent!important;
}

nav a:hover {
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
</style>