<script setup lang="ts">

// -- CALLBACKS
import axios from "axios";
import {ref} from "vue";
import type {FamilyDtoRES} from "@/model/family/FamilyDtoRES.ts";
import {YaourtComputeDtoRES} from "@/model/yaourt/YaourtComputeDtoRES.ts";
import {YaourtComputeDtoREQ} from "@/model/yaourt/YaourtComputeDtoREQ.ts";

// -- VARS
const familyId = 1;
const yaourtDtoRES = ref<YaourtComputeDtoRES | null>(null);

// -- CALLBACKS HTTP -----------------------------------------------------------------

// -- Compute yaourts
const computeYaourt = async () => {

  // -- Safe
  try {

    // -- Call
    let request:YaourtComputeDtoREQ = new YaourtComputeDtoREQ("2025-01-06T09:30:00Z");
    let response:YaourtComputeDtoRES = (await axios.put(`http://localhost:8080/api/v1/yaourt/compute/${familyId}`, request)).data;

    // -- Set
    yaourtDtoRES.value = response;

  } catch (err) {window.alert("Une erreur est survenue lors du calcul des yaourts, avez-vous bien initialisé les données ? Pour ce faire, aller dans 'Settings' et cliquez sur 'Init App'");
  } finally {}
}

</script>

<template>

  <!-- COMPUTE -->
  <div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
       style="border-width: 1px!important;border-color: #dcdcdc!important;border-style: solid; border-radius: 6px;">

    <div class="d-flex flex-row justify-center align-items-center gap-2">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Yaourts total:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">{{yaourtDtoRES?.yaourtNumber??'0 ' + 'yaourt(s)'}}</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <button @click="computeYaourt()" type="button" class="btn btn-primary p-1 px-2 m-0"
                style="background: hsl(118,100%,37%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 600 ">Compute 🕹</button>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>