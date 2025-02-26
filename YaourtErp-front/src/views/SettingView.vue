<script setup lang="ts">

// Fonction pour effectuer la requête GET
import axios from "axios";

const initApp = async () => {

  try {
    const response = await axios.get('http://localhost:8080/api/v1/init'); // URL de test

  } catch (err) {
    window.alert("Une erreur est survenue lors de l'initialisation des données");
    console.log(JSON.stringify(err))
  } finally {

  }
};


</script>

<template>


  <!-- INIT DATA -->
  <div class="d-flex flex-row justify-center align-items-center w-100 gap-1 p-1"
       style="border-width: 1px!important;border-color: #dcdcdc!important;border-style: solid; border-radius: 6px;">


      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Init data: </h6>
      </div>

      <div class="d-flex flex-row justify-content-around align-items-center flex-fill">

        <button @click="initApp" type="button" class="btn btn-primary p-1 m-0 px-2 align-self-center"
                style="background: hsl(125,99%,38%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 500 ">Click me 🚀</button>

      </div>


  </div>

  <!-- DELIVERY -->
  <div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
       style="border-width: 1px!important;border-color: #dcdcdc!important;border-style: solid; border-radius: 6px;">

    <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Delivery time 🚚: </h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center">
        <p class="p-0 m-0 d-flex flex-column align-items-center justify-center" style="font-size: 15px!important;">12 jours</p>
      </div>
    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Change:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <input type="number" class="p-0 m-0 d-flex flex-column align-items-center justify-center"
               style="border-radius: 6px; border-width: 0; background: rgba(110,110,110,0.11);min-height: 23px!important;
          color:#393939; font-size: 12px; font-weight: 600"/>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <button type="button" class="btn btn-primary p-1 m-0"
                style="background: hsl(0, 100%, 37%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 600 ">Change</button>
      </div>
    </div>

  </div>

<!-- FAMILY -->
<div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
  style="border-width: 1px!important;border-color: #dcdcdc!important;border-style: solid; border-radius: 6px;">

  <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Delivery time 🚚: </h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center">
        <p class="p-0 m-0 d-flex flex-column align-items-center justify-center" style="font-size: 15px!important;">12 jours</p>
      </div>
  </div>

  <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Change:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <input type="number" class="p-0 m-0 d-flex flex-column align-items-center justify-center"
          style="border-radius: 6px; border-width: 0; background: rgba(110,110,110,0.11);min-height: 23px!important;
          color:#393939; font-size: 12px; font-weight: 600"/>
      </div>

    <div class="d-flex flex-row justify-center align-items-center ">
      <button type="button" class="btn btn-primary p-1 m-0"
              style="background: hsl(0, 100%, 37%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 600 ">Change</button>
    </div>
  </div>

</div>


</template>

<style scoped>

</style>