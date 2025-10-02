<template>
  <div class="virus-wrapper">
    <h2 class="filter-title">Filtres :</h2>
    <table class="filter-table">
      <tr>
        <td>
          <label for="filterpriceactive">Par prix</label>
          <input type="checkbox" v-model="filterPriceActive" id="filterpriceactive" />
          <div v-if="filterPriceActive" class="filter-group">
            <label for="filterprice">Prix inférieur à :</label>
            <input v-model="priceFilter" id="filterprice" type="number" />
          </div>
        </td>
        <td>
          <label for="filternameactive">Par nom</label>
          <input type="checkbox" v-model="filterNameActive" id="filternameactive" />
          <div v-if="filterNameActive" class="filter-group">
            <label for="filtername">Nom contient :</label>
            <input v-model="nameFilter" id="filtername" type="text" />
          </div>
        </td>
        <td>
          <label for="filterstockactive">Par stock</label>
          <input type="checkbox" v-model="filterStockActive" id="filterstockactive" />
          <div v-if="filterStockActive" class="filter-group">
            <label for="filterstock">En stock</label>
            <input type="checkbox" v-model="stockFilter" id="filterstock" />
          </div>
        </td>
      </tr>
    </table>
    <hr />
    <table class="virus-table">
      <thead>
        <tr>
          <th>Nom</th>
          <th>Prix</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(virus, index) in filterViruses" :key="index">
          <td>{{ virus.name }}</td>
          <td>{{ virus.price }}</td>
        </tr>
      </tbody>
    </table>
    <h1 class="virus-title">Viruses</h1>
    <CheckedList
      @list-button-clicked="flashItems"
      @item-button-clicked="flashItem($event)"
      @checked-change="changeSelected($event)"
      :data="filterViruses"
      :fields="fields"
      :itemCheck="itemCheck"
      :checked="checked"
      :itemButton="itemButton"
      :listButton="listButton"
    />
  </div>
</template>

<script>

import { mapState } from 'vuex';
import CheckedList from '../components/CheckedList.vue';

export default {
  name: 'VirusesView',
  components: {
    CheckedList
  },
  data: () => ({
    priceFilter: 0,
    nameFilter: '',
    stockFilter: true,
    filterPriceActive: false,
    filterNameActive: false,
    filterStockActive: false,
    fields: [
      "name",
      "description"
    ],
    itemCheck: true,
    selected: [],
    listButton: {
      show: true,
      text: "Get info",
      color: "white"
    }
  }),
  computed: {
    ...mapState('shop', ['viruses']),
    itemButton() {
      let itemButton = []
      for (let i = 0; i < this.viruses.length; i++) {
        itemButton.push({
          show: true,
          text: "details",
          color: "blue"
        })
      }
      return itemButton
    },
    filterVirusesByPrice() {
      if (!this.filterPriceActive)
        return this.viruses
      let price = parseInt(this.priceFilter)
      if (isNaN(price)) 
        return []
      if (price > 0) 
        return this.viruses.filter(v => v.price < price)
      return this.viruses
    },
    filterVirusesByName() {
      // no active filter => get whole list
      if (!this.filterNameActive) return this.viruses
      if (this.nameFilter) return this.viruses.filter(v => v.name.includes(this.nameFilter))
      return this.viruses
    },
    filterVirusesByStock() {
      // no active filter => get whole list
      if (!this.filterStockActive) return this.viruses
      if (this.stockFilter) return this.viruses.filter(v => v.stock > 0)
      return this.viruses
    },
    filterViruses() {
      console.log(this.viruses)
      let list = this.viruses
      if (this.filterPriceActive) {
        let price = parseInt(this.priceFilter)
        if ((!isNaN(price)) && (price > 0)) {
          list = list.filter(v => v.price < price)
        }
      }
      if (this.filterNameActive) {
        if (this.nameFilter) list = list.filter(v => v.name.includes(this.nameFilter))
      }
      if (this.filterStockActive) {
        if (this.stockFilter) list = list.filter(v => v.stock > 0)
      }
      return list
    },
    checked() {
      console.log(this.selected)
      let checked = []
      for (let i = 0; i < this.viruses.length; i++) {
        if (this.filterViruses.includes(this.viruses[i])) {
          checked.push(this.selected.includes(i))
        }
      }
      return checked
    }
  },
  methods: {
    flashItems() {
      let s = ""
      for (let i = 0; i < this.viruses.length; i++) {
        if (this.checked[i]) {
          s += this.filterViruses[i].name + ": " + this.filterViruses[i].stock + ", " + (this.filterViruses[i].sold ? "en vente" : "non disponible") + "\n"
        }
      }
      alert(s)
    },
    flashItem(index) {
      let v = this.filterViruses[index]
      alert("Virus : " + v.name + "\nStock : " + v.stock + "\nEtat : " + (v.sold ? "en vente" : "indisponible"))
    },
    changeSelected(index) {
      let v = this.filterViruses[index]
      if (this.filterViruses.includes(v)) {
        let idInList = this.viruses.indexOf(v)
        if (!this.selected.includes(idInList)) {
          this.selected.push(idInList)
        } else {
          this.selected.splice(this.selected.indexOf(idInList), 1)
        }
      }
    }
  }
}
</script>

<style scoped>
.virus-wrapper {
  padding: 20px;
  background-color: #f9f9f9;
}

.filter-title {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #333;
}

.filter-table {
  width: 100%;
  margin-bottom: 20px;
  border-collapse: collapse;
}

.filter-table td {
  padding: 10px;
  vertical-align: top;
}

.filter-group {
  margin-top: 5px;
}

.filter-group label {
  font-size: 0.9rem;
  color: #555;
}

input[type="text"],
input[type="number"] {
  width: 100%;
  padding: 5px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

input[type="checkbox"] {
  margin-left: 5px;
}

hr {
  margin: 20px 0;
  border: 0;
  border-top: 1px solid #ddd;
}

.virus-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.virus-table th,
.virus-table td {
  padding: 10px;
  text-align: left;
  border: 1px solid #ddd;
}

.virus-table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

.virus-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.virus-title {
  font-size: 1.8rem;
  margin-top: 20px;
  color: #333;
}
</style>