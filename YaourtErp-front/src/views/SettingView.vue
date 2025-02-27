<script setup lang="ts">
import axios from "axios";
import {StockDtoRes} from "../model/stock/StockDtoRes.ts";
import {onMounted, ref} from "vue";
import {StockDtoReq} from "../model/stock/StockDtoReq.ts";
import {FamilyDtoREQ} from "@/model/family/FamilyDtoREQ.ts";

// -- VARS UI
const deliveryDelayInput = ref();
const familyDtoREQ = ref<FamilyDtoREQ>(new FamilyDtoREQ(0,0,0,0,0,0,0));

// -- VARS
const stockDtoRes = ref<StockDtoRes | null>(null);

// -- LIFECYCLE ----------------------------------------------------------------------

onMounted(() => {

  // -- Get stock
  get_InitApp();

});

// -- CALLBACKS HTTP -----------------------------------------------------------------

const get_InitApp = async () => {

  // -- Safe
  try {

    // -- Init data
    const response = await axios.get('http://localhost:8080/api/v1/init');

    // -- Get stock
    get_Stock();
    get_Family();

  } catch (err) {window.alert("Une erreur est survenue lors de l'initialisation des données");
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

const patch_Delivery = async () => {

  // -- Check stock value
  if(stockDtoRes.value == null) {
    window.alert("Veuillez initialiser les données avant de modifier le délai de livraison");
    return;
  }

  // -- Check field input value
  if (!deliveryDelayInput.value) {
    window.alert("Veuillez entré un nombre valide");
    return;
  }

  // -- Safe
  try {

    // -- Init payload
    const stockReq:StockDtoReq = new StockDtoReq(stockDtoRes.value?.productName??'', stockDtoRes.value?.quantity??-1, stockDtoRes.value?.quantityMultiple??0, deliveryDelayInput.value);

    // -- Call
    const stockResponse = await axios.patch(`http://localhost:8080/api/v1/stock/${stockDtoRes.value?.id??-1}/delivery/delay`, stockReq);

    // -- Set
    stockDtoRes.value = stockResponse.data as StockDtoRes;

    // -- Refresh
    get_Stock();

  } catch (err) {window.alert("Une erreur est survenue lors de la modification du délai de livraison");
  } finally {}

}

const get_Family = async () => {

  // -- Safe
  try {

    // -- Call
    const familyDtoRESAxios = await axios.get('http://localhost:8080/api/v1/family/1');

    // -- Set
    familyDtoREQ.value = familyDtoRESAxios.data as FamilyDtoREQ;

  } catch (err) {window.alert("Une erreur est survenue lors de la récupération des données de la famille");
  } finally {}
}


const patch_FamilyConsummation = async () => {

  console.log("wa")
  // -- Check stock value
  if(stockDtoRes.value == null) {
    window.alert("Veuillez initialiser l'application");
    return;
  }

  // -- Safe
  try {
    // -- Call
    await axios.patch('http://localhost:8080/api/v1/family/1', familyDtoREQ.value);

  } catch (err) {window.alert("Une erreur est survenue lors de la modification du délai de livraison");
  } finally {}

}
</script>

<template>

  <!-- DELIVERY -->
  <div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
       style="border-width: 1px;border-color:#dcdcdc;border-style: solid;border-radius:6px;">

    <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Delivery time 🚚: </h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center">
        <p class="p-0 m-0 d-flex flex-column align-items-center justify-center" style="font-size:15px!important;">{{stockDtoRes?.deliveryDelay??0}} jours</p>
      </div>
    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-1">
      <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Change:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <input v-model="deliveryDelayInput" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center"
               style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
               color:#393939;font-size:12px;font-weight:600"/>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <button @click="patch_Delivery()" type="button" class="btn btn-primary p-1 m-0"
                style="background:hsl(0, 100%, 37%);border-width:0;max-height:24px;font-size:11px;font-weight:600">Change</button>
      </div>
    </div>

</div>

  <!-- FAMILY -->
  <div class="d-flex flex-column justify-center align-items-start w-100 gap-1 p-1"
       style="border-width: 1px;border-color:#dcdcdc;border-style: solid;border-radius:6px;">

    <div class="d-flex flex-row justify-content-around align-items-center gap-1 w-100">
      <div class="d-flex flex-row justify-a align-items-center">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Family consummation historic 🧑👶👩</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <button @click="patch_FamilyConsummation()" type="button" class="btn btn-primary p-1 m-0" style="background:hsl(0, 100%, 37%);border-width:0;max-height:24px;font-size:11px;font-weight:600">Change</button>
      </div>
    </div>


    <div class="d-flex flex-row justify-center align-items-center gap-1 w-100 pt-2">

        <div class="d-flex flex-column justify-center align-items-center gap-1 w-100">

          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="monday">Monday:</label>

            <input name="monday" min="0" v-model="familyDtoREQ.consummationHistoricMonday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>


          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="tuesday">Tuesday:</label>

            <input name="tuesday" min="0" v-model="familyDtoREQ.consummationHistoricTuesday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>

          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="wednesday">Wednesday:</label>

            <input name="wednesday" min="0" v-model="familyDtoREQ.consummationHistoricWednesday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>

          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="thursday">Thursday:</label>

            <input name="thursday" min="0" v-model="familyDtoREQ.consummationHistoricThursday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>

          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="friday">Friday:</label>

            <input name="friday" min="0" v-model="familyDtoREQ.consummationHistoricFriday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>


          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="saturday">Saturday:</label>

            <input name="saturday" min="0" v-model="familyDtoREQ.consummationHistoricSaturday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>


          <div class="d-flex flex-row px-2 gap-2 w-100">
            <label for="sunday">Sunday:</label>

            <input name="sunday" min="0" v-model="familyDtoREQ.consummationHistoricSunday" type="number" class="p-0 px-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
                   style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:12px;font-weight:600"/>

          </div>


        </div>


    </div>

  </div>

</template>

<style scoped>

</style>