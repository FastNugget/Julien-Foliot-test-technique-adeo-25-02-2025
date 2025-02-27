<script setup lang="ts">

// -- CALLBACKS
import axios from "axios";
import {onMounted, ref} from "vue";
import {YaourtComputeDtoRES} from "@/model/yaourt/YaourtComputeDtoRES.ts";
import {YaourtComputeDtoREQ} from "@/model/yaourt/YaourtComputeDtoREQ.ts";
import {StockDtoRes} from "@/model/stock/StockDtoRes.ts";

// -- CONF --------------------------------------------------------------------------

// -- LIFECYCLE ----------------------------------------------------------------------

onMounted(() => {

  // -- Get stock
  get_Stock();
  setQuantityToBuy();

});


// -- CALLBACKS HTTP -----------------------------------------------------------------


// -- VARS
const familyId = 1;
const startDate = ref<string>('2025-01-06T09:30:00Z');

const stockDtoRes = ref<StockDtoRes | null>(null);
const yaourtDtoRES = ref<YaourtComputeDtoRES | null>(null);
const quantityToBuy = ref<number>(0);
const quantityColisToBuy = ref<number>(0);

// -- Compute yaourts
const computeYaourt = async () => {

  // -- Safe
  try {

    // -- Call
    let request:YaourtComputeDtoREQ = new YaourtComputeDtoREQ(startDate.value);
    let response:YaourtComputeDtoRES = (await axios.put(`http://localhost:8080/api/v1/yaourt/compute/${familyId}`, request)).data;

    // -- Set
    yaourtDtoRES.value = response;

  } catch (err) {window.alert("Une erreur est survenue lors du calcul des yaourts, avez-vous bien initialisé les données ? Pour ce faire, aller dans 'Settings' et cliquez sur 'Init App'");
  } finally {}
}

const get_Stock = async () => {

  // -- Safe
  try {

    // -- Call
    const stockResponse = await axios.get('http://localhost:8080/api/v1/stock/1');

    // -- Set
    stockDtoRes.value = stockResponse.data as StockDtoRes;

  } catch (err) {window.alert("Une erreur est survenue lors de l'initialisation des données");
  } finally {}
}


// -- UTILS -----------------------------------------------------------------

const setQuantityToBuy = async () => {

  // -- Safe
  try {

    // -- Call
    await computeYaourt();

    // -- Set yaourt
    // -- Check
    if (yaourtDtoRES.value == null || stockDtoRes.value == null) {
      return 0;
    }

    // -- Compute
    let res1: number = yaourtDtoRES.value.yaourtNumber;
    let res2: number = yaourtDtoRES.value.yaourtNumber - stockDtoRes.value.quantity;

    // -- Commit
    quantityToBuy.value = res2;

    // -- Set colis

    // -- Compute
    let res4: number = (yaourtDtoRES.value.yaourtNumber ?? 0) % (stockDtoRes.value.quantityMultiple ?? 0);
    let res5: number = Math.floor((yaourtDtoRES.value.yaourtNumber ?? 0) / (stockDtoRes.value.quantityMultiple ?? 0));
    let res6: number = res4 + res5;

    // -- Commit
    quantityColisToBuy.value = res6;

  } catch (err) {
    return 0;
  } finally {
  }

}

// -- Compute yaourts
const getNumberColis = () => {

  // -- Safe
  try {

    // -- Check
    if(yaourtDtoRES.value == null || stockDtoRes.value == null) {return 0;}

    // -- Compute
    let res1:number = (yaourtDtoRES.value.yaourtNumber??0) % (stockDtoRes.value.quantityMultiple??0);
    let res2:number = Math.floor((yaourtDtoRES.value.yaourtNumber??0) / (stockDtoRes.value.quantityMultiple??0));
    let res3:number = res2 + res1;

    // -- Commit
    return res3;

  } catch (err) {return 0;
  } finally {}

}


</script>

<template>

  <!-- COMPUTE -->
  <div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
       style="border-width: 1px!important;border-color: #dcdcdc!important;border-style: solid; border-radius: 6px;">

    <div class="d-flex flex-row justify-center align-items-center ">
      <div class="d-flex flex-row px-2 gap-2 w-100">
        <label for="startDate">Date de départ:</label>

        <input name="startDate" min="0" v-model="startDate" type="date" class="p-0 px-1 pt-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
               style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:11px;font-weight:600"/>

      </div>

      <button @click="setQuantityToBuy()" type="button" class="btn btn-primary p-1 px-2 m-0"
              style="background: hsl(118,100%,37%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 600 ">Compute 🕹</button>
    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-2">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Yaourts:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">{{quantityToBuy}}</h6>
      </div>


    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-2">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Colis:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">{{quantityColisToBuy}}</h6>
      </div>


    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-2 w-100 mt-1" style=" background: #f4f4f4;">
      <div class="d-flex flex-row justify-content-around align-items-center gap-2 w-50 " >
        <h6 class="p-0 m-0 d-flex flex-row align-items-center justify-center" >Date</h6>
      </div>
      <div class="d-flex flex-row justify-content-around align-items-center gap-2 w-50 ">
        <h6 class="p-0 m-0 d-flex flex-row align-items-center justify-center">Consommation</h6>
      </div>
    </div>
    <div class="d-flex flex-column justify-center align-items-center gap-2 w-100 table-container" >

      <table>
        <tbody>
        <tr v-for="(item, index) in yaourtDtoRES?.dailyConsummationList" :key="index">
          <td class="w-50 text-center" >{{ item.date }}</td>
          <td class="w-50 text-center">{{ item.consummation }}</td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<style scoped>
.table-container {
  width: 100%;
  max-width: 400px; /* Largeur maximale du tableau */
  max-height: 300px; /* Hauteur maximale avant scroll */
  overflow-y: auto; /* Activation du scroll vertical */
  border: 1px solid #ccc; /* Bordure pour visibilité */
}


table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f4f4f4;
  position: sticky;
  top: 0;
}
</style>