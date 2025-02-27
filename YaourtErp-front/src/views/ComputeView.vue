<script setup lang="ts">

// -- CALLBACKS
import axios from "axios";
import {computed, onMounted, ref} from "vue";
import {YaourtComputeDtoRES} from "@/model/yaourt/YaourtComputeDtoRES.ts";
import {YaourtComputeDtoREQ} from "@/model/yaourt/YaourtComputeDtoREQ.ts";
import {StockDtoRes} from "@/model/stock/StockDtoRes.ts";
import { LineChart } from "vue-chart-3";
import {Chart, registerables, TimeScale} from "chart.js";
import zoomPlugin from "chartjs-plugin-zoom";
import "chartjs-adapter-moment"; // 📌 Adaptateur obligatoire pour l'échelle de temps


// -- CONF --------------------------------------------------------------------------
Chart.register(...registerables, TimeScale, zoomPlugin); // 🔹 Enregistre les composants de Chart.js
// Configuration du graphique
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    x: {
      type: "time",
      time: {
        unit: "day",
        tooltipFormat: "YYYY-MM-DD"
      },
      title: {
        display: true,
        text: "Date"
      }
    },
    y: {
      title: {
        display: true,
        text: "Consommation"
      }
    }
  },
  plugins: {
    zoom: {
      pan: {
        enabled: true, // 📌 Permet de faire glisser le graphique horizontalement
        mode: "x"
      },
      zoom: {
        wheel: {
          enabled: true // 📌 Active le zoom avec la molette de la souris
        },
        pinch: {
          enabled: true // 📌 Active le zoom tactile sur mobile
        },
        mode: "x"
      }
    }
  }
});

// Liste des données (date + numberOfMoney)
const dataList = ref([
  { date: "2025-01-01", numberOfMoney: 500 },
  { date: "2025-01-02", numberOfMoney: 700 },
  { date: "2025-01-03", numberOfMoney: 400 },
  { date: "2025-01-04", numberOfMoney: 600 },
  { date: "2025-01-05", numberOfMoney: 800 }
]);

// 🔹 Extraction des données pour la courbe
const chartData = computed(() => ({
  labels: dataList.value.map(item => item.date), // Dates en labels
  datasets: [
    {
      label: "Yearly yaourt consumption", // Légende
      data: dataList.value.map(item => item.numberOfMoney),
      borderColor: "blue", // Couleur de la ligne
      backgroundColor: "rgba(0, 0, 255, 0.1)", // Couleur de remplissage
      tension: 0.4 // Lissage de la courbe
    }
  ]
}));



// -- LIFECYCLE ----------------------------------------------------------------------

onMounted(() => {

  // -- Get stock
  get_Stock();
  //setQuantityToBuy();

});


// -- CALLBACKS HTTP -----------------------------------------------------------------


// -- VARS
const familyId = 1;
const startDate = ref<string>('2025-01-06');
const isoDate = ref("");


const stockDtoRes = ref<StockDtoRes | null>(null);
const yaourtDtoRES = ref<YaourtComputeDtoRES | null>(null);
const quantityToBuy = ref<number>(0);
const quantityColisToBuy = ref<number>(0);

// 🔹 Extraction des données pour la courbe
const chartData2 = computed(() => ({
  labels: yaourtDtoRES?.value?.dailyConsumptionList.map(item => item.date)??[], // Dates en labels
  datasets: [
    {
      label: "Yearly yaourt consumption", // Légende
      data: yaourtDtoRES?.value?.dailyConsumptionList.map(item => item.consumption)??[],
      borderColor: "blue", // Couleur de la ligne
      backgroundColor: "rgba(0, 0, 255, 0.1)", // Couleur de remplissage
      tension: 0.4 // Lissage de la courbe
    }
  ]
}));


// -- Compute yaourts
const computeYaourt = async () => {

  // -- Safe
  try {

    // -- Call
    let request:YaourtComputeDtoREQ = new YaourtComputeDtoREQ(startDate.value);
    request.dateBegin = isoDate.value;
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

  if (!startDate.value) return;
  const localDate = new Date(startDate.value);
  isoDate.value = localDate.toISOString(); // ✅ Convertit en format UTC ISO 8601

  // -- Safe
  try {

    // -- Call
    await computeYaourt();

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

      <div class="d-flex flex-row gap-2 w-100 p-0">

        <div class="d-flex flex-row justify-center align-items-center" style="width: 150px">
          <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Start date:</h6>
        </div>

        <input name="startDate" min="0" v-model="startDate" type="date" class="p-0 px-1 pt-1 m-0 d-flex flex-column align-items-center justify-center flex-fill"
               style="border-radius:6px;border-width:0;background:rgba(110,110,110,0.11);min-height: 23px!important;
                 color:#393939;font-size:11px;font-weight:600"/>

      </div>

      <button @click="setQuantityToBuy()" type="button" class="btn btn-primary p-1 px-2 m-0"
              style="background: hsl(118,100%,37%); border-width: 0; max-height: 24px;font-size:11px; font-weight: 600 ">Compute</button>

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
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">Package:</h6>
      </div>

      <div class="d-flex flex-row justify-center align-items-center ">
        <h6 class="p-0 m-0 d-flex flex-column align-items-center justify-center">{{quantityColisToBuy}}</h6>
      </div>


    </div>

    <div class="d-flex flex-row justify-center align-items-center gap-2 w-100 mt-1" style=" background: #f4f4f4;" v-if="quantityToBuy != 0">
      <div class="d-flex flex-row justify-content-around align-items-center gap-2 w-50 " >
        <h6 class="p-0 m-0 d-flex flex-row align-items-center justify-center" >Date</h6>
      </div>
      <div class="d-flex flex-row justify-content-around align-items-center gap-2 w-50 ">
        <h6 class="p-0 m-0 d-flex flex-row align-items-center justify-center">Consumption</h6>
      </div>
    </div>
    <div class="d-flex flex-column justify-center align-items-center gap-2 w-100 table-container" v-if="quantityToBuy != 0">

      <table>
        <tbody>
        <tr v-for="(item, index) in yaourtDtoRES?.dailyConsumptionList" :key="index">
          <td class="w-50 text-center" >{{ item.date }}</td>
          <td class="w-50 text-center">{{ item.consumption }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Graphique -->
    <div class="d-flex flex-row justify-content-around align-items-center mb-1 mt-1" v-if="quantityToBuy != 0">
      <LineChart :chartData="chartData2" :options="chartOptions" />
    </div>
  </div>
</template>

<style scoped>
.table-container {
  width: 100%;
  max-width: 400px; /* Largeur maximale du tableau */
  max-height: 250px; /* Hauteur maximale avant scroll */
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